package me.wand555.challenges.settings.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.google.common.collect.Maps;

import me.wand555.challenges.api.events.settingschange.CallSettingsChangeEvents;
import me.wand555.challenges.api.events.settingschange.ChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.CustomHealthChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.ForceBlockChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.ItemCollectionLimitGlobalChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.MLGChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.PunishableChallengeStatusSwitchEvent;
import me.wand555.challenges.api.events.settingschange.RandomChallengeStatusSwitchEvent;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.CustomHealthChallenge;
import me.wand555.challenges.settings.challengeprofile.types.EndOnDeathChallenge;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NetherFortressSpawnChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockBreakingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockPlacingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoCraftingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoDamageChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoSneakingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.Punishable;
import me.wand555.challenges.settings.challengeprofile.types.RandomChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightChallenge;
import me.wand555.challenges.settings.challengeprofile.types.itemcollectionlimit.ItemCollectionLimitGlobalChallenge;
import me.wand555.challenges.settings.challengeprofile.types.itemcollectionlimit.ItemCollectionSameItemLimitChallenge;
import me.wand555.challenges.settings.challengeprofile.types.lavaground.LavaGroundChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGTimer;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockTimer;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.settings.gui.holders.PunishmentHolder;
import me.wand555.challenges.settings.gui.holders.SettingsHolder;
import me.wand555.challenges.start.Challenges;

public class GUIClickListener implements Listener, CallSettingsChangeEvents {

	private GUI gui;
	private SignMenuFactory signMenuFactory;
	private Challenges plugin;
	
	public GUIClickListener(Challenges plugin, GUI gui, SignMenuFactory signMenuFactory) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.gui = gui;
		this.signMenuFactory = signMenuFactory;
		this.plugin = plugin;
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public <T extends GenericChallenge & Punishable> void onGUIPunishableChallengeClickEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;	
		if(event.getCurrentItem() == null) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
			
		Player p = (Player) event.getWhoClicked();
		int slot = event.getRawSlot();
		if(slot <= 35) event.setCancelled(true);
		if(event.getClickedInventory().getHolder() instanceof PunishmentHolder) {
			
			T rawType1 = null;
			if(slot >= InventoryManager.getInventoryManager().getSlotToChallenge().length && slot != 31) return;
			else if(slot < InventoryManager.getInventoryManager().getSlotToChallenge().length)  
				rawType1 = (T) InventoryManager.getInventoryManager()
					.getSlotToChallenge()[gui.punishmentChallengeTypeOpenGUI.get(p.getUniqueId())];
			T rawType = rawType1;
			System.out.println("!!!! " + rawType);
			System.out.println(rawType instanceof Punishable);
			//if(rawType == null) return;
			
			
			
			switch(slot) {
			case 0:
				callSettingsChangePunishableEventAndActUpon((T) rawType, PunishType.NOTHING, p, gui, plugin); 
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
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_1, p, gui, plugin));
	            			break;
	            		case 2:
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_2, p, gui, plugin));
	            			break;
	            		case 3:
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_3, p, gui, plugin));
	            			break;
						case 4:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_4, p, gui, plugin));		            			
							break;
						case 5:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_5, p, gui, plugin));
							break;
						case 6:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_6, p, gui, plugin));
							break;
						case 7:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_7, p, gui, plugin));
							break;
						case 8:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_8, p, gui, plugin));
							break;
						case 9:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_9, p, gui, plugin));
							break;
						case 10:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_10, p, gui, plugin));
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
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_1, p, gui, plugin));
	            			break;
	            		case 2:
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_2, p, gui, plugin));
	            			break;
	            		case 3:
	            			Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_3, p, gui, plugin));
	            			break;
						case 4:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_4, p, gui, plugin));			            			
							break;
						case 5:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_5, p, gui, plugin));
							break;
						case 6:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_6, p, gui, plugin));
							break;
						case 7:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_7, p, gui, plugin));
							break;
						case 8:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_8, p, gui, plugin));
							break;
						case 9:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_9, p, gui, plugin));
							break;
						case 10:
							Bukkit.getScheduler().runTask(plugin, () -> callSettingsChangePunishableEventAndActUpon(rawType, PunishType.HEALTH_ALL_10, p, gui, plugin));
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
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.DEATH, p, gui, plugin); 
				break;
			case 8:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.DEATH_ALL, p, gui, plugin);
				break;
			case 18:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.ONE_ITEM, p, gui, plugin);
				break;
			case 20:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.ONE_ITEM_ALL, p, gui, plugin);
				break;
			case 22:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.ALL_ITEMS, p, gui, plugin);
				break;
			case 24:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.ALL_ITEMS_ALL, p, gui, plugin);
				break;
			case 26:
				callSettingsChangePunishableEventAndActUpon(rawType, PunishType.CHALLENGE_OVER, p, gui, plugin);
				break;
			case 31:
				gui.createGUI(p, GUIType.PUNISHMENT.getGoBack(), -1, null);
				break;
			default:
				break;
			}
		}
	}
	
	@EventHandler
	public <T extends GenericChallenge> void onGUIGenericChallengeClickEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;	
		if(event.getCurrentItem() == null) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
			
		Player p = (Player) event.getWhoClicked();
		int slot = event.getRawSlot();
		if(slot <= 26) event.setCancelled(true);
		if(event.getClickedInventory().getHolder() instanceof SettingsHolder) {	
			if(p.hasPermission("challenge.settings.modify")) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				//Slot 16 == Global Item Limit -> GUI darf sich öffnen
				if(slot != 16) {
					if(cProfile.canTakeEffect()) {
						p.sendMessage(LanguageMessages.noSettingsHasToBePaused);
						return;
					}
				}
				//Replace #getChallenge call with index from array
				switch(slot) {
				case 0:
				{
					callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.END_ON_DEATH), p, slot, gui);
					break;
				}
				case 1:
				{
					callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NETHER_FORTRESS_SPAWN), p, slot, gui);
					break;
				}	
				case 2:
				{			
					NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
					if(!noDamageChallenge.isActive()) {
						gui.createGUI(p, GUIType.PUNISHMENT, slot, noDamageChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE), p, slot, gui);
					}
					break;		
				}			
				case 3:
				{
					callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_REG), p, slot, gui);
					break;
				}	
				case 4:
				{
					callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_REG_HARD), p, slot, gui);
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
			            				callSettingsChangeCustomHealthEventAndActUpon(cHealthChallenge, (int)amount, p, slot, gui));
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
						callSettingsChangeNormalEventAndActUpon(cHealthChallenge, p, slot, gui);
					}
					break;
				}		
				case 6:
				{
					callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH), p, slot, gui);
					break;
				}				
				case 7:
				{
					NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
					if(!nBPChallenge.isActive()) {
						//nBPChallenge.setAround(); -> only set around when player has successfully clicked on the next gui
						gui.createGUI(p, GUIType.PUNISHMENT, slot, nBPChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING), p, slot, gui);
					}
					break;
				}		
				case 8:
				{
					NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
					if(!nBBChallenge.isActive()) {
						gui.createGUI(p, GUIType.PUNISHMENT, slot, nBBChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING), p, slot, gui);
					}
					break;
				}			
				case 9:
				{
					NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
					if(!nCChallenge.isActive()) {
						gui.createGUI(p, GUIType.PUNISHMENT, slot, nCChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(nCChallenge, p, slot, gui);
					}
					break;
				}			
				case 10:
				{
					NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
					if(!nSChallenge.isActive()) {
						gui.createGUI(p, GUIType.PUNISHMENT, slot, nSChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING), p, slot, gui);
					}
					break;
				}
				case 11:
				{			
					RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);			
					callSettingsChangeRandomChallengeEventAndActUpon(randomChallenge,
							randomChallenge.isRandomized() ? randomChallenge.getRandomizeMapped() : randomChallenge.randomizeItems(), p, slot, gui);	
					break;
				}
				case 12:
				{
					RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_MOB_DROPS);			
					callSettingsChangeRandomChallengeEventAndActUpon(randomChallenge,
							randomChallenge.isRandomized() ? randomChallenge.getRandomizeMapped() : randomChallenge.randomizeItems(), p, slot, gui);	
					break;
				}
				case 13:
				{
					RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING);			
					callSettingsChangeRandomChallengeEventAndActUpon(randomChallenge,
							randomChallenge.isRandomized() ? randomChallenge.getRandomizeMapped() : randomChallenge.randomizeItems(), p, slot, gui);	
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
				            				p.sendMessage(LanguageMessages.signCorrect);
				            				//mlgChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
				            				Bukkit.getScheduler().runTaskLater(plugin, () -> {
				            					gui.createGUI(p, GUIType.PUNISHMENT, slot, mlgChallenge);
				            				}, 0L);
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
						callSettingsChangeNormalEventAndActUpon(mlgChallenge, p, slot, gui);
					}
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
				            				onBlockChallenge.setEarliestToShow(earliestToShown);
				            				onBlockChallenge.setLatestToShow(latestToShown);
				            				onBlockChallenge.setEarliestOnBlock(earliestOnBlock);
				            				onBlockChallenge.setLatestOnBlock(latestOnBlock);
				            				p.sendMessage(LanguageMessages.signCorrect);
				            				Bukkit.getScheduler().runTaskLater(plugin, () -> {
				            					gui.createGUI(p, GUIType.PUNISHMENT, slot, onBlockChallenge);
				            				}, 0L);
				            				
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
						callSettingsChangeNormalEventAndActUpon(onBlockChallenge, p, slot, gui);
						//onBlockChallenge.setBossBar(null);
					}					
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
			            			p.sendMessage(LanguageMessages.signCorrect);
			            			
			            			Bukkit.getScheduler().runTaskLater(plugin, () -> {
			            				callSettingsChangeItemCollectionLimitGlobalChallengeEventAndActUpon(iCLGChallenge, limit, p, slot, gui);	
			            				gui.createGUI(p, GUIType.OVERVIEW, slot, iCLGChallenge);
			            			}, 0L);
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
						gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST, slot, iCLGChallenge);								
					}
					break;
				}
				case 17:
				{
					ItemCollectionSameItemLimitChallenge iCSILChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
					if(!iCSILChallenge.isActive()) {
						gui.createGUI(p, GUIType.PUNISHMENT, slot, iCSILChallenge);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(iCSILChallenge, p, slot, gui);
					}
					break;
				}
				case 18:
				{
					LavaGroundChallenge lavaGroundChallenge = GenericChallenge.getChallenge(ChallengeType.GROUND_IS_LAVA);
					if(!lavaGroundChallenge.isActive()) {
						signMenuFactory
			            .newMenu(new ArrayList<String>(LanguageMessages.floorIsLavaSign))
			            .reopenIfFail()
			            .response((player, lines) -> {
			            	if(ChallengeProfile.getInstance().canTakeEffect()) {
			            		p.sendMessage(LanguageMessages.signNoEffect);
			            		return true;
			            	}
			            	String[] entered = lines[0].split(" ");
			            	if(entered.length == 2) {
			            		String enteredLine1 = entered[0];
				            	String enteredLine2 = entered[1];
				            	if(StringUtils.isNumericSpace(enteredLine1) && !enteredLine1.isEmpty()) {
				            		long timeToChangeType = (int) Math.round(Double.valueOf(enteredLine1));
				            		if(timeToChangeType >= 20) {
				            			if(enteredLine2.equalsIgnoreCase("true") || enteredLine2.equalsIgnoreCase("false")) {
				            				lavaGroundChallenge.setTimeToTransition(timeToChangeType);
				            				lavaGroundChallenge.setLavaStay(Boolean.valueOf(enteredLine2));
				            				//CALL EVENT CORRECT EVENT (ADD LATER)
				            				Bukkit.getScheduler().runTaskLater(plugin, () -> callSettingsChangeNormalEventAndActUpon(lavaGroundChallenge, p, slot, gui), 0L);
				            				p.sendMessage(LanguageMessages.signCorrect);
				            				return true;
				            			}
				            			else {
				            				//second argument is not true or false
				            				p.sendMessage(LanguageMessages.signNoBoolean.replace("[BOOL]", enteredLine2));
				            			}
				            		}
				            		else {
				            			p.sendMessage(LanguageMessages.signTooLowWrong);
				            		}
				            	}
				            	else {
				            		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", enteredLine1));
				            	}
			            	}
			            	else {
			            		
			            	} 	
			                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
			            })
			            .open(p);
					}
					else {
						callSettingsChangeNormalEventAndActUpon(lavaGroundChallenge, p, slot, gui);
					}
					break;
				}
				case 19:
				{			
					HeightChallenge heightChallenge = GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT);														
					if(!heightChallenge.isActive()) {
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
				            				heightChallenge.setEarliestToShow(earliestToShown);
				            				heightChallenge.setLatestToShow(latestToShown);
				            				heightChallenge.setEarliestToBeOnHeight(earliestOnBlock);
				            				heightChallenge.setLatestToBeOnHeight(latestOnBlock);
				            				p.sendMessage(LanguageMessages.signCorrect);
				            				Bukkit.getScheduler().runTaskLater(plugin, () -> {
				            					gui.createGUI(p, GUIType.PUNISHMENT, slot, heightChallenge);
				            				}, 0L);
				            				
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
						callSettingsChangeNormalEventAndActUpon(heightChallenge, p, slot, gui);
						//onBlockChallenge.setBossBar(null);
					}					
					break;
				}
				case 26:
					ChallengeProfile.getInstance().getBackpack().setEnabled(!ChallengeProfile.getInstance().getBackpack().isEnabled());
					gui.createGUI(p, GUIType.OVERVIEW, slot, null);
					break;
				default:
					return;
				}
				
			}
		}
		
		
		
		
		if(event.getClickedInventory() != null) {
			if(event.getCurrentItem() != null) {
				if(event.getWhoClicked() instanceof Player) {
					
					if(event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Collected Items")) {
						event.setCancelled(true);
						ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
						if(slot == 45) {
							int page = iCLGChallenge.getPageCurrentlyOn(p.getUniqueId());
							if(page != 1) {
								iCLGChallenge.getPageMap().put(p.getUniqueId(), page-1);
								gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST, -1, null);
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
							callSettingsChangeNormalEventAndActUpon(iCLGChallenge, p, slot, gui);
						}
						else if(slot == 49) {
							gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST.getGoBack(), -1, null);
						}
						else if(slot == 53) {
							int page = iCLGChallenge.getPageCurrentlyOn(p.getUniqueId());
							if(iCLGChallenge.nextPageExists(page)) {
								iCLGChallenge.getPageMap().put(p.getUniqueId(), page+1);
								gui.createGUI(p, GUIType.COLLECTED_ITEMS_LIST, -1, null);
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
}

