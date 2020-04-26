package me.wand555.Challenges.ChallengeProfile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoRegenerationChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionSameItemLimitChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.Positions.PositionManager;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.Challenges.Config.WorldUtil;
import me.wand555.Challenges.RestoreProfile.RestoreChallenge;
import me.wand555.Challenges.Timer.DateUtil;
import me.wand555.Challenges.Timer.SecondTimer;
import me.wand555.Challenges.Timer.TimerMessage;
import me.wand555.Challenges.Timer.TimerOrder;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;
import me.wand555.GUI.GUIType;
import me.wand555.GUI.SignMenuFactory;

public class ChallengeProfile extends Settings implements TimerOptions, ChallengeOptions {

	private static ChallengeProfile cProfile;
	private static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	private HashSet<UUID> participants = new HashSet<UUID>();
	private SecondTimer secondTimer;
	private RestoreChallenge restoreChallenge;
	private Backpack backpack;
	private PositionManager posManager;
	
	private InventoryManager invManager;
	
	//private MLG mlg;
	
	private ChallengeProfile() {}
	
	public HashSet<UUID> getParticipants() {
		return this.participants;
	}
	
	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(HashSet<UUID> participants) {
		this.participants = participants;
	}

	public void addToParticipants(UUID uuid) {
		participants.add(uuid);
	}
	
	public void removeFromParticipants(UUID uuid) {
		participants.remove(uuid);
	}
	
	public Set<Player> getParticipantsAsPlayers() {
		return participants.stream().map(Bukkit::getPlayer).collect(Collectors.toSet());
	}
	
	public static ChallengeProfile getInstance() {
		if(ChallengeProfile.cProfile == null) ChallengeProfile.cProfile = new ChallengeProfile();
		return cProfile;
	}
	
	public void sendMessageToAllParticipants(String msg) {
		participants.forEach(key -> Bukkit.getPlayer(key).sendMessage(msg));
	}
	
	public boolean isInChallenge(UUID uuid) {
		return participants.stream().anyMatch(key -> key.equals(uuid));
	}
	
	public void displayTimerDescendingEnterGUI(SignMenuFactory signMenuFactory, Player p) {	
		signMenuFactory
        .newMenu(new ArrayList<String>(LanguageMessages.timerStartDescSign))
        .reopenIfFail()
        .response((player, lines) -> {
        	if(canTakeEffect()) {
        		p.sendMessage(LanguageMessages.signNoEffect);
        		return true;
        	}
        	String enteredLine1 = lines[0]; 
        	long timeEntered = DateUtil.getSecondsFromFormattedDuration(enteredLine1);
        	if(timeEntered > 0) {
        		getSecondTimer().setTime(timeEntered);
        		ChallengeProfile.getInstance().startTimer(TimerOrder.DESC);
				ChallengeProfile.getInstance().closeOtherPlayerSettingsGUI();
        		return true;
        	}
        	else {
        		p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", enteredLine1));
        		return false;
        	}
        })
        .open(p);
	}

	@Override
	public void startTimer(TimerOrder order) {
		getSecondTimer().setOrder(order);
		setStarted();
		checkConditionsAndApply();
		//initialize first time things
		getParticipantsAsPlayers().forEach(p -> {
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
			p.setFoodLevel(25);
			p.getActivePotionEffects().clear();
			p.setLevel(0);
		});
		CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
		//if(cHealthChallenge.isActive()) {
		//	SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
		//	sHChallenge.setSharedHealth(cHealthChallenge.getCustomHP());
		//}	
	}

	@Override
	public void pauseTimer() {
		setPaused();
		secondTimer.setMessageType(TimerMessage.TIMER_PAUSED);
	}

	@Override
	public void resumeTimer() {
		setPaused();
		checkConditionsAndApply();
	}

	@Override
	public void endTimer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkConditionsAndApply() {
		NoRegenerationChallenge noRegChallenge = GenericChallenge.getChallenge(ChallengeType.NO_REG);
		WorldLinkManager.worlds.forEach(w -> {
				w.setGameRule(GameRule.NATURAL_REGENERATION, !noRegChallenge.isActive());
			});
		
		CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
		if(cHealthChallenge.isActive()) {
			SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
			sHChallenge.setSharedHealth(cHealthChallenge.getAmount());
			getParticipantsAsPlayers().forEach(p -> {
				//System.out.println("Custom HP in Settings: " + cHealthChallenge.getAmount());
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cHealthChallenge.getAmount());
				//System.out.println("reached");
				
				//p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				
				//System.out.println("Health: " + p.getHealth());
				//System.out.println("HealthScale before: " + p.getHealthScale());
				p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				//System.out.println("HealthScale after: " + p.getHealthScale());
				//p.setHealthScaled(true);
			});
		}
		else {
			//System.out.println("custom health not active");
			getParticipantsAsPlayers().forEach(p -> {
				AttributeInstance maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				//System.out.println("here1");
				maxHealth.setBaseValue(maxHealth.getDefaultValue());
				p.setHealthScale(p.getHealth());
				maxHealth.setBaseValue(maxHealth.getDefaultValue());
				p.setHealthScale(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				//p.damage(0.0001d);
				//p.setHealth(maxHealth.getDefaultValue());		
				//p.kickPlayer("Custom HP were changed. Please join back now.");
			});
		}
		
		//It doesnt have to be block drops, I just need any so I can access the static map
		RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
		if(!RandomChallenge.clearRandomizationIfCase()) {
			randomChallenge.randomizeItems();
		}
		
		MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
		if(mlgChallenge.isActive()) {
			//if timer is already active
			if(mlgChallenge.getTimer() == null 
					|| mlgChallenge.getTimer().getTimeToMLG() == 0 
					|| mlgChallenge.getTimer().getTotalTimeToMLG() == 0) {
				//wenn der Timer noch nie vorher gestartet wurde
				mlgChallenge.setTimer(new MLGTimer(PLUGIN, mlgChallenge));
			}
			else if(mlgChallenge.getTimer().isCancelled()) {
				mlgChallenge.setTimer(new MLGTimer(PLUGIN, mlgChallenge.getTimer().getTotalTimeToMLG(), mlgChallenge.getTimer().getTimeToMLG()));
			}
		}
		
		OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
		if(onBlockChallenge.isActive()) {
			if(onBlockChallenge.getTimer() == null
					|| onBlockChallenge.getTimer().getTimeTo() == 0
					|| onBlockChallenge.getTimer().getTotalTimeTo() == 0) {
				//noch nie gestartet
				onBlockChallenge.setTimer(new OnBlockTimer(PLUGIN, onBlockChallenge));
			}
			else if(onBlockChallenge.getTimer().isCancelled()) {
				onBlockChallenge.setTimer(new OnBlockTimer(PLUGIN, onBlockChallenge, onBlockChallenge.getTimer().getTotalTimeTo(), onBlockChallenge.getTimer().getTimeTo()));
			}
			
			getParticipantsAsPlayers().forEach(p -> onBlockChallenge.addPlayerToBossBar(p));
		}
	}
	
	private String getMultipleCausers(Player[] causers) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<causers.length; i++) {
			stringBuilder.append(causers[i].getName());
			if(i+1 >= causers.length) {
				//last one
			}
			else {
				stringBuilder.append(", ");
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public void endChallenge(ChallengeEndReason reason, Player... causer) {
		restoreChallenge = new RestoreChallenge(getParticipantsAsPlayers(), getSecondTimer().getTime(), reason);
		setPaused();
		setDone();
		getSecondTimer().setMessageType(TimerMessage.TIMER_FINISHED);
		String reasonMessage;
		LinkedHashMap<UUID, Integer> sorted = new LinkedHashMap<UUID, Integer>();
		switch(reason) {
		case FINISHED:
			reasonMessage = LanguageMessages.endChallengeComplete.replace("[TIME]", DateUtil.formatDuration(secondTimer.getTime()));
			if(GenericChallenge.isActive(ChallengeType.ITEM_LIMIT_GLOBAL)) {
				sorted = ((ItemCollectionLimitGlobalChallenge)GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL)).displayReadyStats();
			}
			break;
		case NATURAL_DEATH:
			reasonMessage = LanguageMessages.endChallengeNaturalDeath.replace("[PLAYER]", causer[0].getName());
			break;
		case NO_BLOCK_BREAK:
			reasonMessage = LanguageMessages.endChallengeNoBreak.replace("[PLAYER]", causer[0].getName());
			break;
		case NO_BLOCK_PLACE:
			reasonMessage = LanguageMessages.endChallengeNoPlace.replace("[PLAYER]", causer[0].getName());
			break;
		case NO_CRAFTING:
			reasonMessage = LanguageMessages.endChallengeNoCrafting.replace("[PLAYER]", causer[0].getName());
			break;
		case NO_DAMAGE:
			reasonMessage = LanguageMessages.endChallengeNoDamage.replace("[PLAYER]", causer[0].getName());
			break;
		case NO_SNEAKING:
			reasonMessage = LanguageMessages.endChallengeNoSneaking.replace("[PLAYER]", causer[0].getName());
			break;
		case FAILED_MLG:
			reasonMessage = LanguageMessages.endChallengeFailedMLG.replace("[PLAYER]", causer[0].getName());
			break;
		case NOT_ON_BLOCK:
			reasonMessage = LanguageMessages.endChallengeNotOnBlock.replace("[PLAYER]", getMultipleCausers(causer));
			break;
		case NO_TIME_LEFT:
			reasonMessage = LanguageMessages.endChallengeNoTimeLeft;
			break;
		case TOO_MANY_ITEMS_GLOBAL:
			reasonMessage = LanguageMessages.endChallengeTooManyItemsGlobal.replace("[PLAYER]", causer[0].getName());	
			sorted = ((ItemCollectionLimitGlobalChallenge)GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL)).displayReadyStats();
			break;
		case SAME_ITEM_IN_INVENTORY:
			reasonMessage = LanguageMessages.endChallengeSameItemInInventory
				.replace("[PLAYER]", causer[0].getName())
				.replace("[MATERIAL]", WordUtils.capitalize(((ItemCollectionSameItemLimitChallenge)GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM))
						.getLatestAdded().toString().replace('_', ' ').toLowerCase()));
			break;
		default:
			reasonMessage = "Unknown";
			break;	
		}
		
		for(Player p : getParticipantsAsPlayers()) {
			p.sendMessage(reasonMessage);
			p.setGameMode(GameMode.SPECTATOR);
			if(!sorted.isEmpty()) {
				int place = 0;
				for(Map.Entry<UUID, Integer> entry : sorted.entrySet()) {
					String toSend = ChatColor.GRAY + "" + (++place) + ": " + ChatColor.DARK_GREEN
							+ Bukkit.getOfflinePlayer(entry.getKey()).getName() + " " + ChatColor.YELLOW + entry.getValue() + ChatColor.GRAY + " Items!";
					p.sendMessage(toSend);
				}
			}
		}		
	}

	@Override
	public void restoreChallenge() {
		getRestoreChallenge().restoreChallenge();
	}

	@Override
	public void resetChallenge() {
		isRestarted = true;
		getParticipantsAsPlayers().forEach(WorldUtil::loadPlayerInformationBeforeChallengeAndApply);
		Bukkit.getScheduler().runTaskLater(PLUGIN, () -> {
			WorldUtil.deleteChallengeWorldsAndPlayerData();
			WorldUtil.deletePortalData();
			WorldUtil.deletePositionData();
			
			participants.clear();
			WorldLinkManager.worlds.clear();
			super.restoreDefault();
		}, 30L);
	}

	/**
	 * @return the secondTimer
	 */
	public SecondTimer getSecondTimer() {
		return secondTimer;
	}

	/**
	 * @param secondTimer the secondTimer to set
	 */
	public void setSecondTimer(SecondTimer secondTimer) {
		this.secondTimer = secondTimer;
	}

	/**
	 * @return the posManager
	 */
	public PositionManager getPosManager() {
		if(posManager == null) posManager = new PositionManager();
		return posManager;
	}

	/**
	 * @return the backpack
	 */
	public Backpack getBackpack() {
		if(backpack == null) backpack = new Backpack(PLUGIN);
		return backpack;
	}

	/**
	 * @return the restoreChallenge
	 */
	public RestoreChallenge getRestoreChallenge() {
		return restoreChallenge;
	}
	
	public void closeOtherPlayerSettingsGUI() {
		ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
		.filter(p -> p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Settings") 
				|| p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "Punishments"))
		.forEach(Player::closeInventory);
	}
	
	public InventoryManager getInventoryManager() {
		if(invManager == null) invManager = new InventoryManager();
		return invManager;
	}
}
