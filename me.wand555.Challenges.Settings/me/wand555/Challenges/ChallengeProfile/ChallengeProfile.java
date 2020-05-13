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
import java.util.stream.Stream;

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
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.wand555.Challenge.DeathRun.DeathRunHandler;
import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.API.Events.Violation.CallViolationEvent;
import me.wand555.Challenges.API.Events.Violation.ChallengeEnd.ChallengeEndEvent;
import me.wand555.Challenges.API.Events.Violation.ChallengeEnd.TimerHitZeroEvent;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoRegenerationChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoRegenerationHardChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge.HeightChallenge;
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

public class ChallengeProfile extends Settings implements TimerOptions, ChallengeOptions, CallViolationEvent {

	private static ChallengeProfile cProfile;
	private static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	private HashSet<Player> participants = new HashSet<Player>();
	private SecondTimer secondTimer;
	private RestoreChallenge restoreChallenge;
	private Backpack backpack;
	private PositionManager posManager;
	
	private InventoryManager invManager;
	
	private Scoreboard scoreBoard;
	
	private ChallengeProfile() {}
	
	public HashSet<Player> getParticipants() {
		return this.participants;
	}
	
	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(HashSet<UUID> participants) {
		this.participants = participants.stream().map(Bukkit::getPlayer).filter(p -> p != null).collect(Collectors.toCollection(HashSet::new));
	}

	public void addToParticipants(Player player) {
		participants.add(player);
	}
	
	public void removeFromParticipants(Player player) {
		participants.remove(player);
	}
	
	/*public Set<Player> getParticipantsAsPlayers() {
		return participants.stream().map(Bukkit::getPlayer).filter(p -> p != null).collect(Collectors.toSet());
	}*/
	
	public static ChallengeProfile getInstance() {
		if(ChallengeProfile.cProfile == null) ChallengeProfile.cProfile = new ChallengeProfile();
		return cProfile;
	}
	
	public void sendMessageToAllParticipants(String msg) {
		participants.forEach(key ->key.sendMessage(msg));
	}
	
	public boolean isInChallenge(Player player) {
		return participants.stream().anyMatch(key -> key.equals(player));
	}
	
	public void initializeScoreBoard() {
		scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreBoard.registerNewObjective("Health", Criterias.HEALTH, "health", RenderType.HEARTS);
		objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		//Score score = objective.getScore("abcTest").set;
		//score.set
	}
	
	public void addToScoreBoard(Player p) {
		p.setScoreboard(scoreBoard);
	}
	
	public void removeFromScoreBoard(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
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
	
	public void displayTimerSetEnterGUI(SignMenuFactory signMenuFactory, Player p) {
		signMenuFactory
        .newMenu(new ArrayList<String>(LanguageMessages.timerStartDescSign))
        .reopenIfFail()
        .response((player, lines) -> {
        	String enteredLine1 = lines[0]; 
        	long timeEntered = DateUtil.getSecondsFromFormattedDuration(enteredLine1);
        	if(timeEntered > 0) {
        		getSecondTimer().setTime(timeEntered);
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
		getParticipants().forEach(p -> {
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
		NoRegenerationHardChallenge noRegHard = GenericChallenge.getChallenge(ChallengeType.NO_REG_HARD);
		WorldLinkManager.worlds.forEach(w -> w.setGameRule(GameRule.NATURAL_REGENERATION, !noRegHard.isActive()));
		
		CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
		if(cHealthChallenge.isActive()) {
			SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
			sHChallenge.setSharedHealth(cHealthChallenge.getAmount());
			getParticipants().forEach(p -> {
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
			getParticipants().forEach(p -> {
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
		/*RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
		if(!RandomChallenge.clearRandomizationIfCase()) {
			randomChallenge.randomizeItems();
		}*/
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
	public <T extends GenericChallenge> void endChallenge(T rawType, ChallengeEndReason reason, Object[] extraData, Player... causer) {		
		if(reason.isRestorable()) restoreChallenge = new RestoreChallenge(getParticipants(), getSecondTimer().getTime(), reason);	
		String reasonMessage;
		LinkedHashMap<UUID, Integer> sorted = new LinkedHashMap<UUID, Integer>();
		switch(reason) {
		case FINISHED:
			List<ChallengeType> activeChallenges = Stream.of(ChallengeType.values()).filter(GenericChallenge::isActive).collect(Collectors.toList());
			reasonMessage = LanguageMessages.endChallengeComplete.replace("[TIME]", DateUtil.formatDuration(secondTimer.getTime()));
			if(GenericChallenge.isActive(ChallengeType.ITEM_LIMIT_GLOBAL)) {
				sorted = ((ItemCollectionLimitGlobalChallenge)GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL)).displayReadyStats();
				callItemCollectionLimitGlobalChallengeBeatenEvent(activeChallenges, reasonMessage, sorted);
			}
			else {	
				callChallengeBeatenEvent(activeChallenges, reasonMessage);
			}
			
			return;
		case NATURAL_DEATH:
			reasonMessage = LanguageMessages.endChallengeNaturalDeath
				.replace("[PLAYER]", causer[0].getName())
				.replace("[REASON]", extraData[0].toString());
			break;
		case NO_DAMAGE:
			reasonMessage = LanguageMessages.endChallengeNoDamage
				.replace("[PLAYER]", causer[0].getName())
				.replace("[AMOUNT]", extraData[0].toString())
				.replace("[REASON]", extraData[1].toString());
			break;
		case NO_BLOCK_BREAK:
			reasonMessage = LanguageMessages.endChallengeNoBreak
				.replace("[PLAYER]", causer[0].getName())
				.replace("[BLOCK]", extraData[0].toString());
			break;
		case NO_BLOCK_PLACE:
			reasonMessage = LanguageMessages.endChallengeNoPlace
				.replace("[PLAYER]", causer[0].getName())
				.replace("[BLOCK]", extraData[0].toString());
			break;
		case NO_CRAFTING:
			reasonMessage = LanguageMessages.endChallengeNoCrafting
				.replace("[PLAYER]", causer[0].getName())
				.replace("[ITEM]", extraData[0].toString());
			break;	
		case NO_SNEAKING:
			reasonMessage = LanguageMessages.endChallengeNoSneaking.replace("[PLAYER]", causer[0].getName());
			break;
		case FAILED_MLG:
			reasonMessage = LanguageMessages.endChallengeFailedMLG.replace("[PLAYER]", causer[0].getName());
			break;
		case NOT_ON_BLOCK:
			reasonMessage = LanguageMessages.endChallengeNotOnBlock
				.replace("[PLAYER]", getMultipleCausers(causer))
				.replace("[BLOCK]", extraData[0].toString());
			break;
		case NO_TIME_LEFT:
			//rawType will be null here.
			reasonMessage = LanguageMessages.endChallengeNoTimeLeft;
			callEndChallengeNoTimeLeft(reasonMessage);
			return;
		case TOO_MANY_ITEMS_GLOBAL:
			reasonMessage = LanguageMessages.endChallengeTooManyItemsGlobal
				.replace("[PLAYER]", causer[0].getName())
				.replace("[ITEM]", extraData[0].toString());	
			ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
			sorted = iCLGChallenge.displayReadyStats();
			callEndChallengeItemCollectionLimitGlobalChallengeEventAndActUpon(iCLGChallenge, reason, reasonMessage, sorted, causer[0]);
			return;
		case SAME_ITEM_IN_INVENTORY:
			reasonMessage = LanguageMessages.endChallengeSameItemInInventory
				.replace("[PLAYER]", causer[0].getName())
				.replace("[ITEM]", extraData[0].toString())
				.replace("[PLAYERS]", extraData[1].toString());
				//.replace("[MATERIAL]", WordUtils.capitalize(((ItemCollectionSameItemLimitChallenge)GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM))
				//		.getLatestAdded().toString().replace('_', ' ').toLowerCase()));
			break;
		case NOT_ON_HEIGHT:
			reasonMessage = LanguageMessages.endChallengeNotOnHeight
				.replace("[PLAYER]", getMultipleCausers(causer))
				.replace("[HEIGHT_NORMAL]", extraData[0].toString())
			.replace("[HEIGHT_NETHER]", extraData[1].toString());
			break;
		default:
			reasonMessage = "Unknown";
			break;	
		}
		callEndChallengeEventAndActUpon(rawType, reason, reasonMessage, causer);			
	}

	@Override
	public void restoreChallenge() {
		getRestoreChallenge().restoreChallenge();
	}

	@Override
	public void resetChallenge() {
		isRestarted = true;
		
		OnBlockChallenge onBlockChallenge = (OnBlockChallenge) GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
		if(onBlockChallenge.getBossBar() != null) onBlockChallenge.getBossBar().removeAll();
		
		HeightChallenge heightChallenge = (HeightChallenge) GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT);
		if(heightChallenge.getNormalHeight().getBossbar() != null) heightChallenge.getNormalHeight().getBossbar().removeAll();
		if(heightChallenge.getNetherHeight().getBossbar() != null) heightChallenge.getNetherHeight().getBossbar().removeAll();
		
		System.out.println(getParticipants().size());
		getParticipants().forEach(WorldUtil::loadPlayerInformationBeforeChallengeAndApply);
		Bukkit.getScheduler().runTaskLater(PLUGIN, () -> {
			WorldUtil.deleteChallengeWorldsAndPlayerData();
			//WorldUtil.deletePortalData();
			//WorldUtil.deletePositionData();
			
			if(!PLUGIN.getConfig().getBoolean("autoReset")) participants.clear();
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
		ChallengeProfile.getInstance().getParticipants().stream()
		.filter(p -> p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Settings") 
				|| p.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "Punishments"))
		.forEach(Player::closeInventory);
	}
	
	public InventoryManager getInventoryManager() {
		if(invManager == null) invManager = new InventoryManager();
		return invManager;
	}
}
