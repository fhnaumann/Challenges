package me.wand555.challenges.settings.config;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import me.wand555.challenges.start.Challenges;

public class UserConfig extends ConfigUtil {

	public static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	public static void setUpDefaultConfig() {
		PLUGIN.getConfig().options().copyDefaults(true);
		PLUGIN.getConfig().addDefault("Language", "#Supported Languages:");
		PLUGIN.getConfig().addDefault("Language", "#English - " + Language.ENGLISH.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "#German - " + Language.GERMAN.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "de");
		PLUGIN.getConfig().addDefault("logDamage", true);
		PLUGIN.getConfig().addDefault("autoReset", false);
		/*
		final Map<Material, String> mapped = Stream.of(Material.values())
				.filter(Material::isBlock)
				.filter(Material::isItem)
				.collect(Collectors.toMap(Function.identity(), mat -> DisplayUtil.displayMaterial(mat)));
		
		List<String> list = mapped.entrySet().stream().map(entry -> entry.getKey().toString() + "," + entry.getValue().toString())
			.collect(Collectors.toList());
		
		PLUGIN.getConfig().addDefault("Translation", list.size()); */
		
		PLUGIN.saveConfig();
	}
	
	public static LanguageMessages setUpLanguage() {
		
		return null;
	}
	
	public static void placeLanguages() {
		placeLanguageEn();
		placeLanguageDe();
	}
	
	private static void placeLanguageEn() {
		checkLangOrdner(Language.ENGLISH.getAbbreviation());
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 Language.ENGLISH.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> msgDefaults = new LinkedHashMap<String, String>();
		msgDefaults.put("enabled", "&aenabled");
		msgDefaults.put("disabled", "&cdisabled");
		msgDefaults.put("status", "&7Status: [STATUS]");
		
		msgDefaults.put("teleportMsg", "&7Teleported.");
		msgDefaults.put("alreadyInChallenge", "&7You're already in the challenge.");
		msgDefaults.put("notInChallenge", "&7You're not in a challenge.");
		msgDefaults.put("noChallengeToJoin", "&7There is no challenge to join. Reset or restore existing challenge.");
		msgDefaults.put("deletedChallengeWorlds", "&7Deleted challenge worlds.");
		msgDefaults.put("resetWarning", "&7Reload/Restart the server and type /challenge join to join a new challenge.");
		msgDefaults.put("noChallengeToReset", "&7You have to be in the challenge to reset it.");
		msgDefaults.put("challengeRestored", "&7Restored challenge.");
		msgDefaults.put("noChallengeToRestore", "&7There is no challenge to restore.");
		msgDefaults.put("timerAlreadyStarted", "&7Timer is started. Use /timer pause to pause or resume the timer.");
		msgDefaults.put("noPauseBecauseNotRunning", "&7Cannot pause. Challenge is not running.");
		msgDefaults.put("noSettingsHasToBePaused", "&7Timer has to be paused. /timer pause");
		msgDefaults.put("setHP", "&7Set HP.");
		msgDefaults.put("setHPOutOfRange", "&7Amount has to be 0 <= amount < MAX_HEALTH.");
		msgDefaults.put("notANumber", "&7'&a[NUMBER]&7' is not a number.");
		msgDefaults.put("playerNotOnline", "&7The player '&a[PLAYER]&7' is not online.");
		msgDefaults.put("targetNotInChallenge", "&7The player '&a[PLAYER]&7' is not in a challenge.");
		msgDefaults.put("registeredPosition", "&7Registered position &a[POS]&7.");
		msgDefaults.put("returnPosition", "&7Position: &2&l[X]&7/&2&l[Y]&7/&2&l[Z] &7'&a[POSNAME]&7' in the world &2[WORLD]&7.");
		msgDefaults.put("backpackDisabled", "&7Backpack is disabled.");
		msgDefaults.put("noRestoreBecauseDesc", "&7Cannot restore a countdown challenge!");
		msgDefaults.put("posEmpty", "&7No positions exist.");
		msgDefaults.put("notRestorable", "&7This challenge is not restorable.");
		msgDefaults.put("timerNotSettable", "&7Challenge has be running.");
		
		msgDefaults.put("loadingWorlds", "&7Generating new worlds...");
		
		msgDefaults.put("endChallengeReset", "&7Type &a/challenge reset &7to reset the challenge.");
		msgDefaults.put("endChallengeComplete", "&7The challenge has been completed in &2&l[TIME]&7!");
		msgDefaults.put("endChallengeNaturalDeath", "&2[PLAYER] &7died! The challenge ended!");
		msgDefaults.put("endChallengeNoDamage", "&2[PLAYER] &7took damage! The challenge ended!");
		msgDefaults.put("endChallengeNoPlace", "&2[PLAYER] &7placed a block! The challenge ended!");
		msgDefaults.put("endChallengeNoBreak", "&2[PLAYER] &7broke a block! The challenge ended!");
		msgDefaults.put("endChallengeNoCrafting", "&2[PLAYER] &7crafted an item! The challenge ended!");
		msgDefaults.put("endChallengeNoSneaking", "&2[PLAYER] &7sneaked! The challenge ended!");
		msgDefaults.put("endChallengeFailedMLG", "&2[PLAYER] failed the MLG! The challenge ended!");
		msgDefaults.put("endChallengeNotOnBlock", "&2[PLAYER] &7failed to stand on the block! The challenge ended!");
		msgDefaults.put("endChallengeNoTimeLeft", "&7You ran out of time! The challenge ended");
		msgDefaults.put("endChallengeTooManyItemsGlobal", "&2[PLAYER] &7reached the item limit. The challenge ended!");
		msgDefaults.put("endChallengeSameItemInInventory", "&2[PLAYER] &7collected an item which was already in someone's inventory! The challenge ended!");
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSED &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinal Time: &2[TIME] &7 - /challenge reset or /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause:set:reset");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Amount> <Player>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		msgDefaults.put("timerStartSyntax", "&7Syntax: &a/timer start asc &7or &a/timer start desc");
		
		msgDefaults.put("guiBackpackName", "&7&lTeam Backpack");
		msgDefaults.put("guiDeathName", "&7&lDeath");
		msgDefaults.put("guiFortressSpawnName", "&7&lGuarantee Fortress Spawn");
		msgDefaults.put("guiNoDamageName", "&7&lNo Damage");
		msgDefaults.put("guiNoRegName", "&7&lNo Regeneration");
		msgDefaults.put("guiNoRegHardName", "&7&lNo Regeneration Hard");
		msgDefaults.put("guiCustomHealthName", "&7&lCustom Health");
		msgDefaults.put("guiSharedHealthName", "&7&lShared Health");
		msgDefaults.put("guiNoPlacingName", "&7&lNo Placing");
		msgDefaults.put("guiNoBreakingName", "&7&lNo Breaking");
		msgDefaults.put("guiNoCraftingName", "&7&lNo Crafting");
		msgDefaults.put("guiNoSneakingName", "&7&lNo Sneaking");
		msgDefaults.put("guiRandomBlockDropsName", "&7&lRandomized Blockdrops");
		msgDefaults.put("guiRandomMobDropsName", "&7&lRandomized Mobdrops");
		msgDefaults.put("guiRandomCraftingName", "&7&lRandomized Crafting");
		msgDefaults.put("guiRandomMLGName", "&7&lRandom MLG");
		msgDefaults.put("guiOnBlockName", "&7&lForceBlock");
		msgDefaults.put("guiItemCollectionLimitGlobalName", "&7&lGlobal Item Limit");
		msgDefaults.put("guiItemCollectionSameItemName", "&7&lNo Same Items");
		msgDefaults.put("guiItemFloorIsLavaName", "&7&lFloor is Lava");
		msgDefaults.put("guiToBeOnHeightName", "&7&lHeight Order");
		
		msgDefaults.put("noPreviousPage", "&7You're on the first page.");
		msgDefaults.put("noNextPage", "&7You're on the last page.");
		
		msgDefaults.put("guiPagePrevious", "&6&lPrevious Page");
		msgDefaults.put("guiPageNext", "&6&lNext Page");
		msgDefaults.put("guiBackTo", "&7Back to [TO]");
		msgDefaults.put("guiBackpackLore", "&7&A backpack all challenge participants share!");
		msgDefaults.put("guiDeathLore", "The challenge ends when a player dies!");
		msgDefaults.put("guiFortressSpawnLore", "You will always spawn near a nether fortress!");
		msgDefaults.put("guiNoDamageLore", "The challenge ends when a player takes damage!");
		msgDefaults.put("guiNoRegLore", "Natural regeneration is off!");
		msgDefaults.put("guiNoRegHardLore", "Any regeneration is off!");
		msgDefaults.put("guiCustomHealthLore", "Set with how much HP to play! (kicks all players after changing)");
		msgDefaults.put("guiSharedHealthLore", "All players share their health!");
		msgDefaults.put("guiNoPlacingLore", "Set your own punishment when a player places a block!");
		msgDefaults.put("guiNoBreakingLore", "Set your own punishment when a player breaks a block!");
		msgDefaults.put("guiNoCraftingLore", "Set your own punishment when a player crafts an item!");
		msgDefaults.put("guiNoSneakingLore", "Set your own punishment when a player sneaks!");
		msgDefaults.put("guiRandomBlockDropsLore", "All block drops will be randomized!");
		msgDefaults.put("guiRandomMobDropsLore", "All mob drops will be randomized!");
		msgDefaults.put("guiRandomCraftingLore", "All crafting recipes will be randomized!");
		msgDefaults.put("guiMLGLore", "Random MLG's in custom intervals!");
		msgDefaults.put("guiOnBlockLore", "All players have to stand on a certain block type in random intervals (configurable)!");
		msgDefaults.put("guiItemCollectionLimitGlobalLore", "The unique amount of items players can collect in the inventory in total!");
		msgDefaults.put("guiItemCollectionSameItemLore", "Players are not allowed to have the same item in their inventory at the same time!");
		msgDefaults.put("guiItemFloorIsLavaLore", "The floor is lava. The blocks you stand on transition from normal -> magma -> lava -> normal.");
		msgDefaults.put("guiToBeOnHeightLore", "You have to be on a certain height after some time!");
		
		msgDefaults.put("guiCustomHealthAmount", "&7Custom HP: &a[AMOUNT]HP");
		msgDefaults.put("guiItemCollectionLimit", "&7Limit: &a[AMOUNT] &7Unique items");
		
		msgDefaults.put("punishItemDisplay", "&7Punishment: &a[PUNISHMENT]");
		msgDefaults.put("punishNothing", "&7&lNothing");
		msgDefaults.put("punishHealth", "&a[AMOUNT]HP &7&lfor the causer");
		msgDefaults.put("punishHealthAll", "&a[AMOUNT]HP &7&lfor everyone");
		msgDefaults.put("punishDeath", "&7&lThe causer dies");
		msgDefaults.put("punishDeathAll", "&7&lAll player die");
		msgDefaults.put("punishOneRandomItem", "&7&lThe causer loses 1 random item");
		msgDefaults.put("punishOneRandomItemAll", "&7&lAll lose 1 random item");
		msgDefaults.put("punishAllItems", "&7&lThe causer loses all items");
		msgDefaults.put("punishAllItemsAll", "&7&lAll lose all items");
		msgDefaults.put("punishChallengeOver", "&7&lThe challenge ends");
		
		msgDefaults.put("violationNoDamage", "&2[PLAYER] &7took &e[AMOUNT] &7damage from &a[REASON] &7(Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockPlacing", "&2[PLAYER] &7has placed a block (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockBreaking", "&2[PLAYER] &7has broken a block (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationCrafting", "&2[PLAYER] &7has crafted (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationSneaking", "&2[PLAYER] &7has sneaked (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationMLG", "&2[PLAYER] &7failed the MLG (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationOnBlock", "&2[PLAYER] &7failed to stand on the block (Punishment: &2[PUNISHMENT]&7!");
		msgDefaults.put("violationNoSameItemInInventory", "&2[PLAYER] &7collected an item which is already in someone's inventory (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationToBeOnHeight", "&2[PLAYER] &7failed to be at &e[HEIGHT] &7(Punishment: &2[PUNISHMENT]&7!");
		
		msgDefaults.put("passedMLG", "&7All players beat the MLG!");
		msgDefaults.put("passedOnBlock", "&7All players stood on the correct block!");
		msgDefaults.put("passedHeight", "&7All players were at the correct height!");
		
		msgDefaults.put("logDamage", "&2[PLAYER] &7took &e[AMOUNT] &7damage (Reason: &a[REASON]&7)!");
		msgDefaults.put("logItemPickUp", "&2[PLAYER] &7collected &a[MATERIAL] &7(&e[AMOUNT]&7/&e[LIMIT]&7)!");
		
		msgDefaults.put("titleChallengeChange", "&e[CHALLENGE] &7is [STATUS]");
		msgDefaults.put("titleWithAmountChallengeChange", "&e[CHALLENGE] &7(&a[AMOUNT]&7) is [STATUS]");
		msgDefaults.put("subtitleChallengeChange", "&2Punishment: &e[PUNISHMENT]");
		msgDefaults.put("subtitleWithAmountChallengeChange", "&2Punishment: &e[PUNISHMENT]&7(&a[AMOUNT]&7)");
		
		msgDefaults.put("customHealthSign", "__Write the amount_of hearts above!");
		msgDefaults.put("mlgSign", "__Order is_Early Late Height");
		msgDefaults.put("onBlockSign", "__Order is_Earliest Latest");
		msgDefaults.put("punishmentAmountSign", "_Choose a value_between 1 and 10_(both inclusive)");
		msgDefaults.put("timerStartDescSign", "__Format is_HH:MM:SS");
		msgDefaults.put("itemCollectionLimitGlobalSign", "__Choose amount_of unique items");
		msgDefaults.put("floorIsLavaSign", "__Format is_Time true | false");
		
		msgDefaults.put("signCorrect", "&7Successfully changed settings.");
		msgDefaults.put("signTooLowWrong", "&7a number entered is too low.");
		msgDefaults.put("signLatestLowerThanEarliestWrong", "&7Latest cannot be smaller than earliest.");
		msgDefaults.put("signNoNumberInRange", "&7'&a[NUMBER]&7' has to be between 1 and 10 (inclusive).");
		msgDefaults.put("signNoEffect", "&Notice: Your action had no effect because another player started/resumed the timer.");
		msgDefaults.put("signNoBoolean", "&a[BOOL] &7has to equal either 'true' or 'false'.");
		
		msgDefaults.put("onBlockHidden", "&7Waiting for block...");
		msgDefaults.put("onBlockShown", "&7Stand on &e[BLOCK] &7in &2[TIME]&7!");
		msgDefaults.put("onHeightHidden", "&7Waiting for height...");
		msgDefaults.put("onHeightShown", "&7Be on &e[HEIGHT] &7in &2[TIME]&7!");
		
		msgDefaults.put("itemOnGroundAlreadyCollectedName", "&7Registered");
		
		msgDefaults.keySet().stream()
			.filter(key -> !cfg.isSet(key))
			.forEachOrdered(key -> cfg.set(key, msgDefaults.get(key)));
		
		saveCustomYml(cfg, file);
		msgDefaults.clear();
	}
	
	private static void placeLanguageDe() {
		checkLangOrdner(Language.GERMAN.getAbbreviation());
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 Language.GERMAN.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> msgDefaults = new LinkedHashMap<String, String>();
		
		msgDefaults.put("enabled", "&aaktiviert");
		msgDefaults.put("disabled", "&cdeaktiviert");
		msgDefaults.put("status", "&7Status: [STATUS]");
		
		msgDefaults.put("teleportMsg", "&7Teleportiert.");
		msgDefaults.put("alreadyInChallenge", "&7Du bist bereits in einer Challenge.");
		msgDefaults.put("notInChallenge", "&7Du bist in keiner Challenge.");
		msgDefaults.put("noChallengeToJoin", "&7Es existiert keine Challenge zum beitreten. Setzt zuerst existierende Challenge zurück.");
		msgDefaults.put("deletedChallengeWorlds", "&7Challenge Welten gelöscht.");
		msgDefaults.put("resetWarning", "&7Reloade/Restarte den Server und und joine erneut mit /challenge join für eine neue Challenge.");
		msgDefaults.put("noChallengeToReset", "&7Du musst dich in einer Challenge Welt befinden.");
		msgDefaults.put("challengeRestored", "&7Challenge wiederhergestellt.");
		msgDefaults.put("noChallengeToRestore", "&7Es gibt keine Challenge zum wiederherstellen.");
		msgDefaults.put("timerAlreadyStarted", "&7Timer läuft bereits. Pausiere/Starte den Timer mit /timer pause");
		msgDefaults.put("noPauseBecauseNotRunning", "&7Challenge läuft nicht.");
		msgDefaults.put("noSettingsHasToBePaused", "&7Timer muss pausiert sein. /timer pause");
		msgDefaults.put("setHP", "&7HP gesetzt.");
		msgDefaults.put("setHPOutOfRange", "&7HP müssen zwischen 0 und der maximalen Herzen liegen.");
		msgDefaults.put("notANumber", "&7'&a[NUMBER]&7' ist keine gültige Zahl.");
		msgDefaults.put("playerNotOnline", "&7Der Spieler '&a[PLAYER]&7' ist nicht online.");
		msgDefaults.put("targetNotInChallenge", "&7Der Spieler ''&a[PLAYER]&7'' ist in keiner Challenge.");
		msgDefaults.put("registeredPosition", "&a[POS]&7 hinzugefügt.");
		msgDefaults.put("returnPosition", "&7Position: &2&l[X]&7/&2&l[Y]&7/&2&l[Z] &7'&a[POSNAME]&7' in der Welt &2[WORLD]&7.");
		msgDefaults.put("backpackDisabled", "&7Rucksack ist deaktiviert.");
		msgDefaults.put("noRestoreBecauseDesc", "&7Kann keine Countdown Challenge wiederherstellen!");
		msgDefaults.put("posEmpty", "&7Es existieren keine Positions.");
		msgDefaults.put("notRestorable", "&7Diese Challenge ist nicht wiederherstellbar.");
		msgDefaults.put("timerNotSettable", "&7Challenge muss laufen.");
		
		msgDefaults.put("loadingWorlds", "&7Generiere neue Welten...");
		
		msgDefaults.put("endChallengeReset", "&7Nutze &a/challenge reset &7um die Challenge zu beenden.");
		msgDefaults.put("endChallengeComplete", "&7Die Challenge wurde in &2&l[TIME] &7gemeistert!");
		msgDefaults.put("endChallengeNaturalDeath", "&2[PLAYER] &7starb (Grund: &e[REASON]&7! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoDamage", "&2[PLAYER] &7nahm &a[AMOUNT] &7Schaden (&e[REASON]&7! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoPlace", "&2[PLAYER] &7platzierte &e[BLOCK]&7! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoBreak", "&2[PLAYER] &7baute &e[BLOCK] &7ab! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoCrafting", "&2[PLAYER] &7craftete &e[ITEM]&7! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoSneaking", "&2[PLAYER] &7sneakte! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeFailedMLG", "&2[PLAYER] hat den MLG nicht geschafft! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNotOnBlock", "&2[PLAYER] &7stand nicht auf &e[BLOCK]&7! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoTimeLeft", "&7Der Timer ist abgelaufen! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeTooManyItemsGlobal", "&2[PLAYER] &7hat mit &e[ITEM] &7Item-Limit überschritten! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeSameItemInInventory", "&2[PLAYER] &7hat &e[ITEM] &7aufgesammelt, was bereits [PLAYERS] im Inventar haben! Die Challenge ist vorbei!");	
		msgDefaults.put("endChallengeNotOnHeight", "&2[PLAYER] &7war nicht auf der vorgegebenen Höhe von &e[HEIGHT_NORMAL] &7(Nether: &e[HEIGHT_NETHER]&7)! Die Challenge ist vorbei!");
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSIERT &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinale Zeit: &2[TIME] &7 - /challenge reset oder /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause:set:reset");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Anzahl> <Spieler>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		msgDefaults.put("timerStartSyntax", "&7Syntax: &a/timer start asc &7oder &a/timer start desc");
		
		msgDefaults.put("noPreviousPage", "&7Du bist auf der 1. Seite.");
		msgDefaults.put("noNextPage", "&7Du bist auf der letzten Seite.");
		
		msgDefaults.put("guiPagePrevious", "&6&lVorherige Seite");
		msgDefaults.put("guiPageNext", "&6&lNächste Seite");
		msgDefaults.put("guiBackTo", "&7Zurück zu [TO]");
		msgDefaults.put("guiBackpackName", "&7&lTeam Rucksack");
		msgDefaults.put("guiDeathName", "&7&lTod");
		msgDefaults.put("guiFortressSpawnName", "&7&lGarantierter Netherfestungsspawn");
		msgDefaults.put("guiNoDamageName", "&7&lKein Schaden");
		msgDefaults.put("guiNoRegName", "&7&lKeine Regeneration");
		msgDefaults.put("guiNoRegHardName", "&7&lKeine Regeneration Schwer");
		msgDefaults.put("guiCustomHealthName", "&7&lCustom Herzen");
		msgDefaults.put("guiSharedHealthName", "&7&lGeteilte Herzen");
		msgDefaults.put("guiNoPlacingName", "&7&lKein Platzieren");
		msgDefaults.put("guiNoBreakingName", "&7&lKein Abbauen");
		msgDefaults.put("guiNoCraftingName", "&7&lKein Crafting");
		msgDefaults.put("guiNoSneakingName", "&7&lKein Sneaking");
		msgDefaults.put("guiRandomBlockDropsName", "&7&lZufällige Blockdrops");
		msgDefaults.put("guiRandomMobDropsName", "&7&lZufällige Mobdrops");
		msgDefaults.put("guiRandomCraftingName", "&7&lZufälliges Crafting");
		msgDefaults.put("guiRandomMLGName", "&7&lZufälliger MLG");
		msgDefaults.put("guiOnBlockName", "&7&lForceBlock");
		msgDefaults.put("guiItemCollectionLimitGlobalName", "&7&lGlobales Item Limit");
		msgDefaults.put("guiItemCollectionSameItemName", "&7&lKeine Gleichen Items");
		msgDefaults.put("guiItemFloorIsLavaName", "&7&lDer Boden ist Lava");
		msgDefaults.put("guiToBeOnHeightName", "&7&lHöhenanweisung");
		
		msgDefaults.put("guiBackpackLore", "&7Ein gemeinsamer Rucksack zum Teilen von Items!");
		msgDefaults.put("guiDeathLore", "Die Challenge ist vorbei sobald ein Spieler stirbt!");
		msgDefaults.put("guiFortressSpawnLore", "Du spawnst im Nether immer in der Nähe einer Festung!");
		msgDefaults.put("guiNoDamageLore", "Die Challenge ist vorbei sobald ein Spieler schaden bekommt!");
		msgDefaults.put("guiNoRegLore", "Natürliche Regeneration ist aus!");
		msgDefaults.put("guiNoRegHardLore", "Jegliche Regeneration ist aus!");
		msgDefaults.put("guiCustomHealthLore", "Wähle wie viele HP die Spieler haben! (Spieler nach Änderung kicken)");
		msgDefaults.put("guiSharedHealthLore", "Alle Spieler teilen ihre Herzen!");
		msgDefaults.put("guiNoPlacingLore", "Wähle was passiert, wenn ein Spieler einen Block platziert!");
		msgDefaults.put("guiNoBreakingLore", "Wähle was passiert, wenn ein Spieler einen Block abbaut!");
		msgDefaults.put("guiNoCraftingLore", "Wähle was passiert, wenn ein Spieler ein Item craftet!");
		msgDefaults.put("guiNoSneakingLore", "Wähle was passiert, wenn ein Spieler sneakt!");
		msgDefaults.put("guiRandomBlockDropsLore", "Alle Block-Drops sind zufällig!");
		msgDefaults.put("guiRandomMobDropsLore", "Alle Mob-Drops sind zufällig!");
		msgDefaults.put("guiRandomCraftingLore", "Alle Crafting Rezepte sind zufällig!");
		msgDefaults.put("guiMLGLore", "Zufällige MLG's in ausgewählten Intervallen!");
		msgDefaults.put("guiOnBlockLore", "Es werden in bestimmten Intervallen zufällig Blöcke ausgewählt, auf denen ihr nach einer konfigurienten Zeit draufstehen müsst!");
		msgDefaults.put("guiItemCollectionLimitGlobalLore", "Begrenzt die maximale Anzahl an verschiedenen Items, die genutzt werden!");
		msgDefaults.put("guiItemCollectionSameItemLore", "Spieler dürfen nicht zum gleichen Zeitpunkt dieselben Items im Inventar haben!");
		msgDefaults.put("guiItemFloorIsLavaLore", "Der Boden ist Lava. Die Blöcke unter dir verwandeln sich wie folgt: Normal -> Magma -> Lava -> Normal");
		msgDefaults.put("guiToBeOnHeightLore", "Ihr müsst in einer bestimmten Zeit auf einer bestimmten Höhe sein!");
		
		msgDefaults.put("guiCustomHealthAmount", "&7Ausgewählte HP: &a[AMOUNT]HP");
		msgDefaults.put("guiItemCollectionLimit", "&7Limit: &a[AMOUNT] &7verschiedene Items");
		
		msgDefaults.put("punishItemDisplay", "&7Bestrafung: &a[PUNISHMENT]");
		msgDefaults.put("punishNothing", "&7&lNichts");
		msgDefaults.put("punishHealth", "&a[AMOUNT]HP &7&lfür den Spieler");
		msgDefaults.put("punishHealthAll", "&a[AMOUNT]HP &7&lfür alle");
		msgDefaults.put("punishDeath", "&7&lDer Spieler stirbt");
		msgDefaults.put("punishDeathAll", "&7&lAlle Spieler sterben");
		msgDefaults.put("punishOneRandomItem", "&7&lDer Spieler verliert ein zufälliges Item");
		msgDefaults.put("punishOneRandomItemAll", "&7&lAlle Spieler verlieren ein zufälliges Item");
		msgDefaults.put("punishAllItems", "&7&lDer Spieler verliert sein Inventar");
		msgDefaults.put("punishAllItemsAll", "&7&lAlle Spieler verlieren ihr Inventar");
		msgDefaults.put("punishChallengeOver", "&7&lDie Challenge ist vorbei");
		
		msgDefaults.put("violationNoDamage", "&2[PLAYER] &7hat durch &a[REASON] &e[AMOUNT] &7Schaden genommen (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockPlacing", "&2[PLAYER] &7hat einen Block platziert (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockBreaking", "&2[PLAYER] &7hat einen Block abgebaut (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationCrafting", "&2[PLAYER] &7hat ein Item gecraftet (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationSneaking", "&2[PLAYER] &7hat gesneakt (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationMLG", "&2[PLAYER]&7 hat den MLG nicht geschafft (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationOnBlock", "&2[PLAYER] &7stand nicht auf dem Block (Bestrafung: &2[PUNISHMENT]&7!");
		msgDefaults.put("violationNoSameItemInInventory", "&2[PLAYER] &7hat ein Item aufgesammelt, was bereits jemand im Inventar hatte (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationToBeOnHeight", "&2[PLAYER] &7war nicht auf der Höhe &e[HEIGHT] &7(Bestrafung: &2[PUNISHMENT]&7!");
		
		msgDefaults.put("passedMLG", "&7Alle Spieler haben den MLG geschafft!");
		msgDefaults.put("passedOnBlock", "&7Alle Spieler standen auf dem korrekten Block!");
		msgDefaults.put("passedHeight", "&7Alle Spieler befanden sich auf der korrekten Höhe!");
		
		msgDefaults.put("logDamage", "&2[PLAYER] &7hat &e[AMOUNT] &7Schaden genommen (Grund: &a[REASON]&7)!");
		msgDefaults.put("logItemPickUp", "&2[PLAYER] &7hat &a[MATERIAL] &7aufgesammelt(&e[AMOUNT]&7/&e[LIMIT]&7)!");
		
		msgDefaults.put("titleChallengeChange", "&e[CHALLENGE] &7ist [STATUS]");
		msgDefaults.put("titleWithAmountChallengeChange", "&e[CHALLENGE] &7(&a[AMOUNT]&7) ist [STATUS]");
		msgDefaults.put("subtitleChallengeChange", "&2Bestrafung: &e[PUNISHMENT]");
		msgDefaults.put("subtitleWithAmountChallengeChange", "&2Bestrafung: &e[PUNISHMENT]&7(&a[AMOUNT]&7)");
		msgDefaults.put("customHealthSign", "__Wähle die Anzahl_der Herzen oben!");
		msgDefaults.put("mlgSign", "__Anordnung ist_Früh Spät Höhe");
		msgDefaults.put("onBlockSign", "__Anordnung ist_Früh Spät");
		msgDefaults.put("punishmentAmountSign", "_Wähle einen Wert_zw. 1 und 10_(beide inklusive)");
		msgDefaults.put("timerStartDescSign", "__Format ist_HH:MM:SS");
		msgDefaults.put("itemCollectionLimitGlobalSign", "__Wähle die max._Anzahl an Items");
		msgDefaults.put("floorIsLavaSign", "__Format ist_Zeit true | false");
		
		msgDefaults.put("signCorrect", "&7Einstellungen erfolgreich aktualisiert.");
		msgDefaults.put("signTooLowWrong", "&7Eine der Zahlen ist zu niedrig.");
		msgDefaults.put("signLatestLowerThanEarliestWrong", "&7Maximal kann nicht kleiner als Minimal sein.");
		msgDefaults.put("signNoNumberInRange", "&7''&a[NUMBER]&7'' muss zwischen 1 und 10 sein (inklusive).");
		msgDefaults.put("signNoEffect", "&7Hinweis: Deine Eingabe hatte keinen Effekt, da ein anderer Spieler den Timer bereits fortgesetzt hat.");
		msgDefaults.put("signNoBoolean", "&a[BOOL] &7muss entweder 'true' oder 'false' entsprechen.");
		
		msgDefaults.put("onBlockHidden", "&7Warten auf Block...");
		msgDefaults.put("onBlockShown", "&7Stehe auf &e[BLOCK] &7in &2[TIME]&7!");
		msgDefaults.put("onHeightHidden", "&7Warten auf Höhe...");
		msgDefaults.put("onHeightShown", "&7Sei auf &e[HEIGHT] &7in &2[TIME]&7!");
		
		msgDefaults.put("itemOnGroundAlreadyCollectedName", "&7Registriert");
		
		msgDefaults.keySet().stream()
		.filter(key -> !cfg.isSet(key))
		.forEachOrdered(key -> cfg.set(key, msgDefaults.get(key)));
		
		saveCustomYml(cfg, file);
		msgDefaults.clear();
	}
}
