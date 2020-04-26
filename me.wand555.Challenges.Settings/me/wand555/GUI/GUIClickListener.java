package me.wand555.GUI;

import java.util.ArrayList;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.EndOnDeathChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NetherFortressSpawnChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockBreakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockPlacingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoCraftingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoDamageChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoSneakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.ChallengeProfile.Events.SettingsChange.ChallengeStatusSwitchEvent;
import me.wand555.Challenges.ChallengeProfile.Events.SettingsChange.CustomHealthChallengeStatusSwitchEvent;
import me.wand555.Challenges.ChallengeProfile.Events.SettingsChange.PunishableChallengeStatusSwitchEvent;
import me.wand555.Challenges.Config.LanguageMessages;

public class GUIClickListener implements Listener {

	private GUI gui;
	private SignMenuFactory signMenuFactory;
	private Challenges plugin;
	
	public GUIClickListener(Challenges plugin, GUI gui, SignMenuFactory signMenuFactory) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.gui = gui;
		this.signMenuFactory = signMenuFactory;
		this.plugin = plugin;
	}
	
	@EventHandler
	public <T> void onGUIClickEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() != null) {
			if(event.getCurrentItem() != null) {
				if(event.getWhoClicked() instanceof Player) {
					if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Settings")) {
						Player p = (Player) event.getWhoClicked();
						int slot = event.getRawSlot();
						if(slot <= 26) event.setCancelled(true);
						if(p.hasPermission("challenge.settings.modify")) {
							ChallengeProfile cProfile = ChallengeProfile.getInstance();
							//Slot 16 == Global Item Limit -> GUI darf sich öffnen
							if(slot != 16) {
								if(cProfile.canTakeEffect()) {
									p.sendMessage(LanguageMessages.noSettingsHasToBePaused);
									return;
								}
							}
							switch(slot) {
							case 0:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.END_ON_DEATH), p);
								break;
							}
							case 1:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NETHER_FORTRESS_SPAWN), p);
								break;
							}	
							case 2:
							{			
								NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
								if(!noDamageChallenge.isActive()) {
									gui.createGUI(p, GUIType.PUNISHMENT, noDamageChallenge.getPunishCause());
								}
								else {
									callPunishableEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING), noDamageChallenge.getPunishType(), p);
								}
								break;		
							}			
							case 3:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_REG), p);
								break;
							}	
							case 4:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_REG_HARD), p);
								break;
							}	
							case 5:
							{
								CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
								if(!cHealthChallenge.isActive()) {
									signMenuFactory
						            .newMenu(new ArrayList<String>(LanguageMessages.customHealthSign))
						            .reopenIfFail()
						            .response((player, lines) -> {
						            	if(ChallengeProfile.getInstance().canTakeEffect()) {
						            		p.sendMessage(LanguageMessages.signNoEffect);
						            		return true;
						            	}
						            	String enteredLine1 = lines[0];         	
						            	if(StringUtils.isNumericSpace(enteredLine1) && !enteredLine1.isEmpty()) {
						            		double amount = Double.valueOf(enteredLine1);
						            		if(amount > 0) {
						            			Bukkit.getServer().getScheduler().runTask(plugin, () -> 
						            				callCustomHealthEventAndActUpon(cHealthChallenge, (int)amount, p));
						            			p.sendMessage(LanguageMessages.signCorrect);
						            			return true;
						            		}
						            		else {
						            			p.sendMessage(LanguageMessages.signTooLowWrong);
						            		}
						            	}
						            	else {
						            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", enteredLine1));
						            	}
						                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
						            })
						            .open(p);
								}
								else {
									callNormalEventAndActUpon(cHealthChallenge, p);
								}
								break;
							}		
							case 6:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH), p);
								break;
							}				
							case 7:
							{
								NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
								if(!nBPChallenge.isActive()) {
									//nBPChallenge.setAround(); -> only set around when player has successfully clicked on the next gui
									gui.createGUI(p, GUIType.PUNISHMENT, nBPChallenge.getPunishCause());
								}
								else {
									callPunishableEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING), nBPChallenge.getPunishType(), p);
								}
								break;
							}		
							case 8:
							{
								NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
								if(!nBBChallenge.isActive()) {
									gui.createGUI(p, GUIType.PUNISHMENT, nBBChallenge.getPunishCause());
								}
								else {
									callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING), p);
								}
								break;
							}			
							case 9:
							{
								NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
								if(!nCChallenge.isActive()) {
									gui.createGUI(p, GUIType.PUNISHMENT, nCChallenge.getPunishCause());
								}
								else {
									callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING), p);
								}
								break;
							}			
							case 10:
							{
								NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
								if(!nSChallenge.isActive()) {
									gui.createGUI(p, GUIType.PUNISHMENT, nSChallenge.getPunishCause());
								}
								else {
									callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING), p);
								}
								break;
							}
							case 11:
							{
								callNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS), p);
								RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
								if(randomChallenge.isActive()) {
									randomChallenge.setAround();
									RandomChallenge.clearRandomizationIfCase();
								}
								else {
									randomChallenge.setAround();
									randomChallenge.randomizeItems();
								}
								
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								randomChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());	
								break;
							}
							case 12:
							{
								RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_MOB_DROPS);
								if(randomChallenge.isActive()) {
									randomChallenge.setAround();
									RandomChallenge.clearRandomizationIfCase();
								}
								else {
									randomChallenge.setAround();
									randomChallenge.randomizeItems();
								}
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								randomChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());	
								break;
							}
							case 13:
							{
								RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING);
								if(randomChallenge.isActive()) {
									randomChallenge.setAround();
									RandomChallenge.clearRandomizationIfCase();
								}
								else {
									randomChallenge.setAround();
									randomChallenge.randomizeItems();
								}
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								randomChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());	
								break;
							}
							case 14:
							{
								MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
								if(!mlgChallenge.isActive()) {
									signMenuFactory
						            .newMenu(new ArrayList<String>(LanguageMessages.mlgSign))
						            .reopenIfFail()
						            .response((player, lines) -> {
						            	if(ChallengeProfile.getInstance().canTakeEffect()) {
						            		p.sendMessage(LanguageMessages.signNoEffect);
						            		return true;
						            	}
						            	String[] entered = lines[0].split(" ");
						            	if(entered.length == 3) {
						            		String enteredLine2 = entered[0];
							            	String enteredLine3 = entered[1];
							            	String enteredLine4 = entered[2]; 
							            	if(StringUtils.isNumericSpace(enteredLine2) && !enteredLine2.isEmpty()
							            			&& StringUtils.isNumericSpace(enteredLine3) && !enteredLine3.isEmpty()
							            			&& StringUtils.isNumericSpace(enteredLine4)&& !enteredLine4.isEmpty()) {
							            		int earliest = (int) Math.round(Double.valueOf(enteredLine2));
							            		int latest = (int) Math.round(Double.valueOf(enteredLine3));
							            		int height = (int) Math.round(Double.valueOf(enteredLine4));
							            		
							            		if(earliest > 0 && latest > 0 && height > 0) {
							            			if(earliest <= latest) {
							            				mlgChallenge.setEarliest(earliest);
							            				mlgChallenge.setLatest(latest);
							            				mlgChallenge.setHeight(height);
							            				reloadOtherPlayerInvs(gui, p);
							            				p.sendMessage(LanguageMessages.signCorrect);
							            				//mlgChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
							            				Bukkit.getScheduler().runTaskLater(plugin, () -> {
							            					gui.createGUI(p, GUIType.PUNISHMENT, mlgChallenge.getPunishCause());
							            				}, 1L);
							            				return true;
							            			}
							            			else {
							            				p.sendMessage(LanguageMessages.signLatestLowerThanEarliestWrong);
							            			}
							            		}
							            		else {
							            			p.sendMessage(LanguageMessages.signTooLowWrong);
							            		}
							            	}
							            	else {
							            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", "Entered"));
							            	}
						            	}
						            	else {
						            		
						            	} 	
						                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
						            })
						            .open(p);
								}
								else {
									mlgChallenge.setAround();
									mlgChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
									if(mlgChallenge.getTimer() != null) {
										mlgChallenge.getTimer().cancel();
										mlgChallenge.setTimer(null);
									}
									gui.createGUI(p, GUIType.OVERVIEW);
								}
								
								
								reloadOtherPlayerInvs(gui, p);
								
								break;
							}	
							case 15:
							{			
								OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);														
								if(!onBlockChallenge.isActive()) {
									signMenuFactory
						            .newMenu(new ArrayList<String>(LanguageMessages.onBlockSign))
						            .reopenIfFail()
						            .response((player, lines) -> {
						            	if(ChallengeProfile.getInstance().canTakeEffect()) {
						            		p.sendMessage(LanguageMessages.signNoEffect);
						            		return true;
						            	}
						            	String[] entered = lines[0].split(" ");
						            	if(entered.length == 4) {
						            		String enteredLine2 = entered[0];
							            	String enteredLine3 = entered[1];
							            	String enteredLine4 = entered[2];
							            	String enteredLine5 = entered[3];
							            	if(StringUtils.isNumericSpace(enteredLine2) && !enteredLine2.isEmpty()
							            			&& StringUtils.isNumericSpace(enteredLine3) && !enteredLine3.isEmpty()
							            			&& StringUtils.isNumericSpace(enteredLine4) && !enteredLine4.isEmpty()
							            			&& StringUtils.isNumericSpace(enteredLine5) && !enteredLine5.isEmpty()) {
							            		int earliestToShown = (int) Math.round(Double.valueOf(enteredLine2));
							            		int latestToShown = (int) Math.round(Double.valueOf(enteredLine3));
							            		int earliestOnBlock = (int) Math.round(Double.valueOf(enteredLine4));
							            		int latestOnBlock = (int) Math.round(Double.valueOf(enteredLine5));
							            		
							            		if(earliestToShown > 0 && latestToShown > 0 
							            				&& earliestOnBlock > 0 && latestOnBlock > 0) {
							            			if(earliestToShown <= latestToShown || earliestOnBlock <= latestOnBlock) {
							            				
							            				//onBlockChallenge.setAround();
							            				onBlockChallenge.setEarliestToShow(earliestToShown);
							            				onBlockChallenge.setLatestToShow(latestToShown);
							            				onBlockChallenge.setEarliestOnBlock(earliestOnBlock);
							            				onBlockChallenge.setLatestOnBlock(latestOnBlock);
							            				reloadOtherPlayerInvs(gui, p);
							            				p.sendMessage(LanguageMessages.signCorrect);
							            				//onBlockChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
							            				Bukkit.getScheduler().runTaskLater(plugin, () -> {
							            					gui.createGUI(p, GUIType.PUNISHMENT, onBlockChallenge.getPunishCause());
							            				}, 1L);
							            				
							            				return true;
							            			}
							            			else {
							            				p.sendMessage(LanguageMessages.signLatestLowerThanEarliestWrong);
							            			}
							            		}
							            		else {
							            			p.sendMessage(LanguageMessages.signTooLowWrong);
							            		}
							            	}
							            	else {
							            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", "Entered"));
							            	}
						            	}
						            	else {
						            		
						            	} 	
						                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
						            })
						            .open(p);
								}
								else {
									onBlockChallenge.setAround();
									onBlockChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
									gui.createGUI(p, GUIType.OVERVIEW);
									if(onBlockChallenge.getTimer() != null) {
										onBlockChallenge.getTimer().cancel();
									}
									onBlockChallenge.setEarliestToShow(0);
									onBlockChallenge.setLatestToShow(0);
									onBlockChallenge.setEarliestOnBlock(0);
									onBlockChallenge.setLatestOnBlock(0);
									onBlockChallenge.setTimer(null);
									//onBlockChallenge.setBossBar(null);
								}
								
								
								reloadOtherPlayerInvs(gui, p);
								
								
								break;
							}
							case 16:
							{
								ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
								if(!iCLGChallenge.isActive()) {
									if(cProfile.canTakeEffect()) {
										p.sendMessage(LanguageMessages.noSettingsHasToBePaused);
										return;
									}
									signMenuFactory
						            .newMenu(new ArrayList<String>(LanguageMessages.itemCollectionLimitGlobalSign))
						            .reopenIfFail()
						            .response((player, lines) -> {
						            	if(ChallengeProfile.getInstance().canTakeEffect()) {
						            		p.sendMessage(LanguageMessages.signNoEffect);
						            		return true;
						            	}
						            	if(StringUtils.isNumeric(lines[0])) {
						            		int limit = (int) ((double) Double.valueOf(lines[0]));
						            		if(limit > 0) {
						            			iCLGChallenge.setAround();
						            			iCLGChallenge.setLimit(limit);
						            			p.sendMessage(LanguageMessages.signCorrect);
						            			iCLGChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
						            			Bukkit.getScheduler().runTaskLater(plugin, () -> {
					            					gui.createGUI(p, GUIType.OVERVIEW);
					            				}, 1L);
						            			return true;
						            		}
						            		else {
						            			p.sendMessage(LanguageMessages.signTooLowWrong);
						            		}
						            	}
						            	else {
						            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", lines[0]));
						            	}	
						                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
						            })
						            .open(p);
								}
								else {
									iCLGChallenge.getPageMap().put(p.getUniqueId(), 1);
									gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST);								
								}
								reloadOtherPlayerInvs(gui, p);
								break;
							}
							/*case 17:
							{
								ItemCollectionSameItemLimitChallenge iCSILChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
								if(!iCSILChallenge.isActive()) {
									gui.createGUI(p, GUIType.PUNISHMENT, iCSILChallenge.getPunishCause());
								}
								else {
									iCSILChallenge.setAround();
									gui.createGUI(p, GUIType.OVERVIEW);
									reloadOtherPlayerInvs(gui, p);
									iCSILChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
								}
								break;
							}*/
							case 26:
								ChallengeProfile.getInstance().getBackpack().setEnabled(!ChallengeProfile.getInstance().getBackpack().isEnabled());
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							default:
								return;
							}
						}
					}
					else if(event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Punishments")) {
						Player p = (Player) event.getWhoClicked();
						int slot = event.getRawSlot();
						if(slot <= 35) event.setCancelled(true);
						
						ChallengeType challengeType = gui.punishmentChallengeTypeOpenGUI.get(p.getUniqueId());
						@SuppressWarnings("unchecked")
						T rawType = (T) GenericChallenge.getChallenge(challengeType);
						GenericChallenge genericChallenge = (GenericChallenge) rawType;
						//gui.punishmentChallengeTypeOpenGUI.remove(p.getUniqueId());	
						
						switch(slot) {
						case 0:
							callPunishableEventAndActUpon(rawType, PunishType.NOTHING, p); 
							break;
						case 2:	
							signMenuFactory
							.newMenu(new ArrayList<String>(LanguageMessages.punishmentAmountSign))
				            .reopenIfFail()
				            .response((player, lines) -> {
				            	if(ChallengeProfile.getInstance().canTakeEffect()) {
				            		p.sendMessage(LanguageMessages.signNoEffect);
				            		return true;
				            	}
				            	String enteredLine1 = lines[0];   
				            	if(StringUtils.isNumeric(enteredLine1) && !enteredLine1.isEmpty()) {
				            		int number = Integer.valueOf(enteredLine1);
				            		switch(number) {
				            		case 1:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_1, p));
				            			break;
				            		case 2:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_2, p));
				            			break;
				            		case 3:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_3, p));
				            			break;
									case 4:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_4, p));		            			
										break;
									case 5:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_5, p));
										break;
									case 6:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_6, p));
										break;
									case 7:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_7, p));
										break;
									case 8:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_8, p));
										break;
									case 9:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_9, p));
										break;
									case 10:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_10, p));
										break;
									default:	
										p.sendMessage(LanguageMessages.signNoNumberInRange);
										return false;
				            		}				            		
				            		genericChallenge.setAround();
				            		genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
				            		return true;
				            	}
				            	else {
				            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", enteredLine1));
				            	}
				                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
				            })
				            .open(p);
							break;
						case 4:
							signMenuFactory
				            .newMenu(new ArrayList<String>(LanguageMessages.punishmentAmountSign))
				            .reopenIfFail()
				            .response((player, lines) -> {
				            	if(ChallengeProfile.getInstance().canTakeEffect()) {
				            		p.sendMessage(LanguageMessages.signNoEffect);
				            		return true;
				            	}
				            	String enteredLine1 = lines[0];        	
				            	if(StringUtils.isNumericSpace(enteredLine1) && !enteredLine1.isEmpty()) {
				            		int number = Integer.valueOf(enteredLine1);
				            		switch(number) {
				            		case 1:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_1, p));
				            			break;
				            		case 2:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_2, p));
				            			break;
				            		case 3:
				            			Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_3, p));
				            			break;
									case 4:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_4, p));			            			
										break;
									case 5:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_5, p));
										break;
									case 6:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_6, p));
										break;
									case 7:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_7, p));
										break;
									case 8:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_8, p));
										break;
									case 9:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_9, p));
										break;
									case 10:
										Bukkit.getScheduler().runTask(plugin, () -> callPunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_10, p));;
										break;
									default:	
										p.sendMessage(LanguageMessages.signNoNumberInRange);
										return false;
				            		}
				            		return true;
				            	}
				            	else {
				            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", enteredLine1));
				            	}
				                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
				            })
				            .open(p);
							break;
						case 6:
							callPunishableEventAndActUpon(rawType, PunishType.DEATH, p); 
							break;
						case 8:
							callPunishableEventAndActUpon(rawType, PunishType.DEATH_ALL, p);
							break;
						case 18:
							callPunishableEventAndActUpon(rawType, PunishType.ONE_ITEM, p);
							break;
						case 20:
							callPunishableEventAndActUpon(rawType, PunishType.ONE_ITEM_ALL, p);
							break;
						case 22:
							callPunishableEventAndActUpon(rawType, PunishType.ALL_ITEMS, p);
							break;
						case 24:
							callPunishableEventAndActUpon(rawType, PunishType.ALL_ITEMS_ALL, p);
							break;
						case 26:
							callPunishableEventAndActUpon(rawType, PunishType.CHALLENGE_OVER, p);
							break;
						case 31:
							gui.createGUI(p, GUIType.PUNISHMENT.getGoBack());
							break;
						default:
							break;
						}	
						reloadOtherPlayerInvs(gui, p);
					}
					else if(event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Collected Items")) {
						Player p = (Player) event.getWhoClicked();
						int slot = event.getRawSlot();
						event.setCancelled(true);
						ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
						if(slot == 45) {
							int page = iCLGChallenge.getPageCurrentlyOn(p.getUniqueId());
							if(page != 1) {
								iCLGChallenge.getPageMap().put(p.getUniqueId(), page-1);
								gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST);
							}
							else {
								p.sendMessage(LanguageMessages.noPreviousPage);
							}
						}
						else if(slot == 47) {
							if(ChallengeProfile.getInstance().canTakeEffect()) {
								p.sendMessage(LanguageMessages.noSettingsHasToBePaused);
								return;
							}
							//disable
							iCLGChallenge.setActive(false);
							iCLGChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
							gui.createGUI(p, GUIType.OVERVIEW);
							iCLGChallenge.setCurrentAmount(0);
							iCLGChallenge.setLimit(0);
							iCLGChallenge.getUniqueItems().clear();
						}
						else if(slot == 49) {
							gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST.getGoBack());
						}
						else if(slot == 53) {
							int page = iCLGChallenge.getPageCurrentlyOn(p.getUniqueId());
							if(iCLGChallenge.nextPageExists(page)) {
								iCLGChallenge.getPageMap().put(p.getUniqueId(), page+1);
								gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST);
							}
							else {
								p.sendMessage(LanguageMessages.noNextPage);
							}
						}
					}
				}
			}
 		}
	}
	
	private <T> void callNormalEventAndActUpon(T rawType, Player p) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		
		if(rawType.getClass().equals(CustomHealthChallenge.class)) {
			
		}
		else if(rawType instanceof RandomChallenge) {
			
		}
		else if(rawType.getClass().equals(MLGChallenge.class)) {
			
		}
		else if(rawType.getClass().equals(OnBlockChallenge.class)) {
			
		}
		else if(rawType.getClass().equals(ItemCollectionLimitGlobalChallenge.class)) {
			
		}
		else {
			ChallengeStatusSwitchEvent<T> switchEvent = new ChallengeStatusSwitchEvent<T>(rawType, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				genericChallenge.setAround();	
				genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());	
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
			}
			gui.createGUI(p, GUIType.OVERVIEW);
		}	
	}
	
	private void callCustomHealthEventAndActUpon(CustomHealthChallenge customHealthChallenge, int amount, Player p) {
		CustomHealthChallengeStatusSwitchEvent switchEvent = new CustomHealthChallengeStatusSwitchEvent(customHealthChallenge, amount, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			customHealthChallenge.setAround();
			customHealthChallenge.setAmount(switchEvent.getCustomHP());
			customHealthChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
		}
		gui.createGUI(p, GUIType.OVERVIEW);
	}
	
	private <T> void callPunishableEventAndActUpon(T rawType, PunishType type, Player p) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		
		if(rawType.getClass().equals(MLGChallenge.class)) {
			
		}
		else if(rawType.getClass().equals(OnBlockChallenge.class)) {
			RandomChallenge randomChallenge = (RandomChallenge) rawType;
			
		}
		else {
			PunishableChallengeStatusSwitchEvent<T> switchEvent = new PunishableChallengeStatusSwitchEvent<T>(rawType, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				Punishable punishable = (Punishable) rawType;	
				punishable.setPunishType(switchEvent.getPunishType());
				genericChallenge.setAround();
				genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());			
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		gui.createGUI(p, GUIType.PUNISHMENT.getGoBack());
	}
	
	private void reloadOtherPlayerInvs(GUI gui, Player changer) {
		ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
			.filter(p -> !p.getUniqueId().equals(changer.getUniqueId()))
			.filter(p -> p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Settings"))
			.forEach(p -> gui.createGUI(p, GUIType.OVERVIEW));
	}
}

