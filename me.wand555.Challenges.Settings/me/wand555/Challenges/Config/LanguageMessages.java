package me.wand555.Challenges.Config;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Challenges;

public class LanguageMessages extends ConfigUtil {
	
	protected final static Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	protected final FileConfiguration CONFIG = PLUGIN.getConfig();
	
	/**
	 * Boolean options
	 */
	public static String enabled;
	public static String disabled;
	public static String status;
	
	public static String teleportMsg;
	public static String alreadyInChallenge;
	public static String notInChallenge;
	public static String noChallengeToJoin;
	public static String deletedChallengeWorlds;
	public static String resetWarning;
	public static String noChallengeToReset;
	public static String challengeRestored;
	public static String noChallengeToRestore;
	public static String timerAlreadyStarted;
	public static String noPauseBecauseNotRunning;
	public static String noSettingsHasToBePaused;
	public static String setHP;
	public static String setHPOutOfRange;
	public static String notANumber;
	public static String playerNotOnline;
	public static String targetNotInChallenge;
	public static String registeredPosition;
	public static String returnPosition;
	
	public static String endChallengeReset;
	public static String endChallengeComplete;
	public static String endChallengeNaturalDeath;
	public static String endChallengeNoDamage;
	public static String endChallengeNoPlace;
	public static String endChallengeNoBreak;
	public static String endChallengeNoCrafting;
	public static String endChallengeNoSneaking;
	public static String endChallengeFailedMLG;
	public static String endChallengeNotOnBlock;
	
	public static String timerMessageStart;
	public static String timerMessagePause;
	public static String timerMessageFinished;
	
	public static String challengeOptionSyntax;
	public static String timerOptionSyntax;
	public static String hpOptionSyntax;
	public static String positionSyntax;
	public static String bpSyntax;
	public static String settingSyntax;
	
	public static String guiDeathName;
	public static String guiFortressSpawnName;
	public static String guiNoDamageName;
	public static String guiNoRegName;
	public static String guiNoRegHardName;
	public static String guiCustomHealthName;
	public static String guiSharedHealthName;
	public static String guiNoPlacingName;
	public static String guiNoBreakingName;
	public static String guiNoCraftingName;
	public static String guiNoSneakingName;
	public static String guiRandomBlockDropsName;
	public static String guiRandomMobDropsName;
	public static String guiRandomCraftingName;
	public static String guiRandomMLGName;
	public static String guiOnBlockName;
	
	public static ArrayList<String> guiDeathLore;
	public static ArrayList<String> guiFortressSpawnLore;
	public static ArrayList<String> guiNoDamageLore;
	public static ArrayList<String> guiNoRegLore;
	public static ArrayList<String> guiNoRegHardLore;
	public static ArrayList<String> guiCustomHealthLore;
	public static ArrayList<String> guiSharedHealthLore;
	public static ArrayList<String> guiNoPlacingLore;
	public static ArrayList<String> guiNoBreakingLore;
	public static ArrayList<String> guiNoCraftingLore;
	public static ArrayList<String> guiNoSneakingLore;
	public static ArrayList<String> guiRandomBlockDropsLore;
	public static ArrayList<String> guiRandomMobDropsLore;
	public static ArrayList<String> guiRandomCraftingLore;
	public static ArrayList<String> guiMLGLore;
	public static ArrayList<String> guiOnBlockLore;
	
	public static String guiCustomHealthAmount;
	/**
	 * Holds String which is displayed in the GUI when a challenge is active. Contains the punishment ready to be set in the lore.
	 */
	public static String punishItemDisplay;
	/**
	 * Used in GUIType.PUNISHMENT for display in itemstacks.
	 */
	public static String punishNothing;
	public static String punishHealth;
	public static String punishHealthAll;
	public static String punishDeath;
	public static String punishDeathAll;
	public static String punishOneRandomItem;
	public static String punishOneRandomItemAll;
	public static String punishAllItems;
	public static String punishAllItemsAll;
	public static String punishChallengeOver;
	/**
	 * Message in chat when a player violates a challenge. Holds [PLAYER] and [PUNISHMENT] placeholders.
	 */
	public static String violationBlockPlacing;
	public static String violationBlockBreaking;
	public static String violationCrafting;
	public static String violationSneaking;
	public static String violationOnBlock;
	
	/**
	 * Message in chat when (mostly everyone) passed a certain challenge. Not all challenges notify in chat.
	 */
	public static String passedOnBlock;
	
	/**
	 * Message in title when settings are changed.
	 */
	public static String titleChallengeChange;
	public static String titleWithAmountChallengeChange;
	public static String subtitleChallengeChange;
	public static String subtitleWithAmountChallengeChange;
	
	/**
	 * SignGUI on sign display
	 */
	public static ArrayList<String> customHealthSign;
	public static ArrayList<String> mlgSign;
	public static ArrayList<String> onBlockSign;
	public static ArrayList<String> punishmentAmountSign;	
	
	/**
	 * When in SignGUI entered wrong. (right, first var here)
	 */
	public static String signCorrect;
	public static String signTooLowWrong;
	public static String signLatestLowerThanEarliestWrong;
	public static String signNoNumberInRange;
	/**
	 * The title on the bossbar depending on the status of the OnBlock/ForceBlock Challenge
	 */
	public static String onBlockHidden;
	public static String onBlockShown;
	
	public static String onlyForPlayers;
	
	public static void loadLang(Language lang) {	
		checkLangOrdner(lang.getAbbreviation());
		File file =  new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 lang.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		enabled = formatWithoutPrefix(cfg.getString("enabled"));
		disabled = formatWithoutPrefix(cfg.getString("disabled"));
		status = formatWithoutPrefix(cfg.getString("status"));
		
		teleportMsg = format(cfg.getString("teleportMsg"));
		alreadyInChallenge = format(cfg.getString("alreadyInChallenge"));
		notInChallenge = format(cfg.getString("notInChallenge"));
		noChallengeToJoin = format(cfg.getString("noChallengeToJoin"));
		deletedChallengeWorlds = format(cfg.getString("deletedChallengeWorlds"));
		resetWarning = format(cfg.getString("resetWarning"));
		noChallengeToReset = format(cfg.getString("noChallengeToReset"));
		challengeRestored = format(cfg.getString("challengeRestored"));
		noChallengeToRestore = format(cfg.getString("noChallengeToRestore"));
		timerAlreadyStarted = format(cfg.getString("timerAlreadyStarted"));
		noPauseBecauseNotRunning = format(cfg.getString("noPauseBecauseNotRunning"));
		noSettingsHasToBePaused = format(cfg.getString("noSettingsHasToBePaused"));
		setHP = format(cfg.getString("setHP"));
		setHPOutOfRange = format(cfg.getString("setHPOutOfRange"));
		notANumber = format(cfg.getString("notANumber"));
		playerNotOnline = format(cfg.getString("playerNotOnline"));
		targetNotInChallenge = format(cfg.getString("targetNotInChallenge"));
		registeredPosition = format(cfg.getString("registeredPosition"));
		returnPosition = format(cfg.getString("returnPosition"));
		
		endChallengeReset = format(cfg.getString("endChallengeReset"));
		endChallengeComplete = format(cfg.getString("endChallengeComplete"));
		endChallengeNaturalDeath = format(cfg.getString("endChallengeNaturalDeath"));
		endChallengeNoDamage = format(cfg.getString("endChallengeNoDamage"));
		endChallengeNoPlace = format(cfg.getString("endChallengeNoPlace"));
		endChallengeNoBreak = format(cfg.getString("endChallengeNoBreak"));
		endChallengeNoCrafting = format(cfg.getString("endChallengeNoCrafting"));
		endChallengeNoSneaking = format(cfg.getString("endChallengeNoSneaking"));
		endChallengeFailedMLG = format(cfg.getString("endChallengeFailedMLG"));
		endChallengeNotOnBlock = format(cfg.getString("endChallengeNotOnBlock"));
		
		timerMessageStart = formatWithoutPrefix(cfg.getString("timerMessageStart"));
		timerMessagePause = formatWithoutPrefix(cfg.getString("timerMessagePause"));
		timerMessageFinished = formatWithoutPrefix(cfg.getString("timerMessageFinished"));
		
		challengeOptionSyntax = format(cfg.getString("challengeOptionSyntax"));
		timerOptionSyntax = format(cfg.getString("timerOptionSyntax"));
		hpOptionSyntax = format(cfg.getString("hpOptionSyntax"));
		positionSyntax = format(cfg.getString("positionSyntax"));
		bpSyntax = format(cfg.getString("bpSyntax"));
		settingSyntax = format(cfg.getString("settingSyntax"));
		
		guiDeathName = formatWithoutPrefix(cfg.getString("guiDeathName"));
		guiFortressSpawnName = formatWithoutPrefix(cfg.getString("guiFortressSpawnName"));
		guiNoDamageName = formatWithoutPrefix(cfg.getString("guiNoDamageName"));
		guiNoRegName = formatWithoutPrefix(cfg.getString("guiNoRegName"));
		guiNoRegHardName = formatWithoutPrefix(cfg.getString("guiNoRegHardName"));
		guiCustomHealthName = formatWithoutPrefix(cfg.getString("guiCustomHealthName"));
		guiSharedHealthName = formatWithoutPrefix(cfg.getString("guiSharedHealthName"));
		guiNoPlacingName = formatWithoutPrefix(cfg.getString("guiNoPlacingName"));
		guiNoBreakingName = formatWithoutPrefix(cfg.getString("guiNoBreakingName"));
		guiNoCraftingName = formatWithoutPrefix(cfg.getString("guiNoCraftingName"));
		guiNoSneakingName = formatWithoutPrefix(cfg.getString("guiNoSneakingName"));
		guiRandomBlockDropsName = formatWithoutPrefix(cfg.getString("guiRandomBlockDropsName"));
		guiRandomMobDropsName = formatWithoutPrefix(cfg.getString("guiRandomMobDropsName"));
		guiRandomCraftingName = formatWithoutPrefix(cfg.getString("guiRandomCraftingName"));
		guiRandomMLGName = formatWithoutPrefix(cfg.getString("guiRandomMLGName"));
		guiOnBlockName = formatWithoutPrefix(cfg.getString("guiOnBlockName"));
		
		guiDeathLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiDeathLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiFortressSpawnLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiFortressSpawnLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoDamageLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoDamageLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoRegLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiDeathLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoRegHardLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoRegLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiCustomHealthLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiCustomHealthLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiSharedHealthLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiSharedHealthLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoPlacingLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoPlacingLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoBreakingLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoBreakingLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoCraftingLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoCraftingLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiNoSneakingLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiNoSneakingLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiRandomBlockDropsLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiRandomBlockDropsLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiRandomMobDropsLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiRandomMobDropsLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiRandomCraftingLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiRandomCraftingLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiMLGLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiMLGLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		guiOnBlockLore = Lists.newArrayList(WordUtils.wrap(formatWithoutPrefix(cfg.getString("guiOnBlockLore")), 20, "_", true).split("_"))
				.stream().map(string -> "&7"+string).map(LanguageMessages::formatWithoutPrefix).collect(Collectors.toCollection(ArrayList::new));
		
		guiCustomHealthAmount = formatWithoutPrefix(cfg.getString("guiCustomHealthAmount"));
		punishItemDisplay = formatWithoutPrefix(cfg.getString("punishItemDisplay"));
		punishNothing = formatWithoutPrefix(cfg.getString("punishNothing"));
		punishHealth = formatWithoutPrefix(cfg.getString("punishHealth"));
		punishHealthAll = formatWithoutPrefix(cfg.getString("punishHealthAll"));
		punishDeath = formatWithoutPrefix(cfg.getString("punishDeath"));
		punishDeathAll = formatWithoutPrefix(cfg.getString("punishDeathAll"));
		punishOneRandomItem = formatWithoutPrefix(cfg.getString("punishOneRandomItem"));
		punishOneRandomItemAll= formatWithoutPrefix(cfg.getString("punishOneRandomItemAll"));
		punishAllItems = formatWithoutPrefix(cfg.getString("punishAllItems"));
		punishAllItemsAll = formatWithoutPrefix(cfg.getString("punishAllItemsAll"));
		punishChallengeOver = formatWithoutPrefix(cfg.getString("punishChallengeOver"));
		
		violationBlockPlacing = format(cfg.getString("violationBlockPlacing"));
		violationBlockBreaking = format(cfg.getString("violationBlockBreaking")); 
		violationCrafting = format(cfg.getString("violationCrafting"));
		violationSneaking = format(cfg.getString("violationSneaking"));
		violationOnBlock = format(cfg.getString("violationOnBlock"));
		
		passedOnBlock = format(cfg.getString("passedOnBlock"));
		
		titleChallengeChange = formatWithoutPrefix(cfg.getString("titleChallengeChange"));
		titleWithAmountChallengeChange = formatWithoutPrefix(cfg.getString("titleWithAmountChallengeChange"));
		subtitleChallengeChange = formatWithoutPrefix(cfg.getString("subtitleChallengeChange"));
		subtitleWithAmountChallengeChange = formatWithoutPrefix(cfg.getString("subtitleWithAmountChallengeChange"));
		customHealthSign = Lists.newArrayList(cfg.getString("customHealthSign").split("_"))
				.stream().collect(Collectors.toCollection(ArrayList::new));
		mlgSign = Lists.newArrayList(cfg.getString("mlgSign").split("_"))
				.stream().collect(Collectors.toCollection(ArrayList::new));
		onBlockSign = Lists.newArrayList(cfg.getString("onBlockSign").split("_"))
				.stream().collect(Collectors.toCollection(ArrayList::new));
		punishmentAmountSign = Lists.newArrayList(cfg.getString("punishmentAmountSign").split("_"))
				.stream().collect(Collectors.toCollection(ArrayList::new));
	
		signCorrect = format(cfg.getString("signCorrect"));
		signTooLowWrong = format(cfg.getString("signTooLowWrong"));
		signLatestLowerThanEarliestWrong = format(cfg.getString("signLatestLowerThanEarliestWrong"));
		signNoNumberInRange = format(cfg.getString("signNoNumberInRange"));
		
		onBlockHidden = formatWithoutPrefix(cfg.getString("onBlockHidden"));
		onBlockShown = formatWithoutPrefix(cfg.getString("onBlockShown"));
	}
	
	public static String format(String msg) {
		return Challenges.PREFIX + ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static String formatWithoutPrefix(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static ArrayList<String> formatWithoutPrefixList(List<String> list) {
		return list.stream().map(s -> formatWithoutPrefix(s)).collect(Collectors.toCollection(ArrayList::new));
	}
}
