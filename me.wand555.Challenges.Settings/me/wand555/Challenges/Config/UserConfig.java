package me.wand555.Challenges.Config;

import java.io.File;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.wand555.Challenges.Challenges;

public class UserConfig extends ConfigUtil {

	public static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	public static void setUpDefaultConfig() {
		PLUGIN.getConfig().options().copyDefaults(true);
		PLUGIN.getConfig().addDefault("Language", "#Supported Languages:");
		PLUGIN.getConfig().addDefault("Language", "#English - " + Language.ENGLISH.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "#German - " + Language.GERMAN.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "de");
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
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSED &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinal Time: &2[TIME] &7 - /challenge reset or /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Amount> <Player>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		
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
		
		msgDefaults.put("guiCustomHealthAmount", "&7Custom HP: &a[AMOUNT]HP");
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
		
		msgDefaults.put("violationBlockPlacing", "&2[PLAYER] &7has placed a block (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockBreaking", "&2[PLAYER] &7has broken a block (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationCrafting", "&2[PLAYER] &7has crafted (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationSneaking", "&2[PLAYER] &7has sneaked (Punishment: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationOnBlock", "&2[PLAYER] &7failed to stand on the block (Punishment: &2[PUNISHMENT]&7!");
		
		msgDefaults.put("passedOnBlock", "All players stood on the correct block!");
		
		msgDefaults.put("titleChallengeChange", "&e[CHALLENGE] &7is [STATUS]");
		msgDefaults.put("titleWithAmountChallengeChange", "&e[CHALLENGE] &7(&a[AMOUNT]&7) is [STATUS]");
		msgDefaults.put("subtitleChallengeChange", "&2Punishment: &e[PUNISHMENT]");
		msgDefaults.put("subtitleWithAmountChallengeChange", "&2Punishment: &e[PUNISHMENT]&7(&a[AMOUNT]&7)");
		
		msgDefaults.put("customHealthSign", "__Write the amount_of hearts above!");
		msgDefaults.put("mlgSign", "__Order is_Early Late Height");
		msgDefaults.put("onBlockSign", "__Order is_Earliest Latest");
		msgDefaults.put("punishmentAmountSign", "_Choose a value_between 1 and 10!_(both inclusive)");
		
		msgDefaults.put("signCorrect", "&7Successfully changed settings.");
		msgDefaults.put("signTooLowWrong", "&7a number entered is too low.");
		msgDefaults.put("signLatestLowerThanEarliestWrong", "&7Latest cannot be smaller than earliest.");
		msgDefaults.put("signNoNumberInRange", "&7'&a[NUMBER]&7' has to be between 1 and 10 (inclusive).");
		
		msgDefaults.put("onBlockHidden", "&7Waiting for block...");
		msgDefaults.put("onBlockShown", "&7Stand on &e[BLOCK] &7in &2[TIME]&7!");
		
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
		
		msgDefaults.put("endChallengeReset", "&7Nutze &a/challenge reset &7um die Challenge zu beenden.");
		msgDefaults.put("endChallengeComplete", "&7Die Challenge wurde in &2&l[TIME] &7gemeistert!");
		msgDefaults.put("endChallengeNaturalDeath", "&2[PLAYER] &7starb! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoDamage", "&2[PLAYER] &7nahm Schaden! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoPlace", "&2[PLAYER] &7platzierte einen Block! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoBreak", "&2[PLAYER] &7baute einen Block ab! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoCrafting", "&2[PLAYER] &7craftete ein Item! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoSneaking", "&2[PLAYER] &7sneakte! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeFailedMLG", "&2[PLAYER] hat den MLG nicht geschafft! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNotOnBlock", "&2[PLAYER] &7stand nicht auf dem Block! Die Challenge ist vorbei!");
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSIERT &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinale Zeit: &2[TIME] &7 - /challenge reset oder /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Anzahl> <Spieler>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		
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
		
		msgDefaults.put("guiCustomHealthAmount", "&7Ausgewählte HP: &a[AMOUNT]HP");
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
		
		msgDefaults.put("violationBlockPlacing", "&2[PLAYER] &7hat einen Block platziert (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationBlockBreaking", "&2[PLAYER] &7hat einen Block abgebaut (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationCrafting", "&2[PLAYER] &7hat ein item gecraftet (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationSneaking", "&2[PLAYER] &7hat gesneakt (Bestrafung: &2[PUNISHMENT]&7)!");
		msgDefaults.put("violationOnBlock", "&2[PLAYER] &7stand nicht auf dem Block (Bestrafung: &2[PUNISHMENT]&7!");
		
		msgDefaults.put("passedOnBlock", "Alle Spieler standen auf dem korrekten Block!");
		
		msgDefaults.put("titleChallengeChange", "&e[CHALLENGE] &7ist [STATUS]");
		msgDefaults.put("titleWithAmountChallengeChange", "&e[CHALLENGE] &7(&a[AMOUNT]&7) ist [STATUS]");
		msgDefaults.put("subtitleChallengeChange", "&2Bestrafung: &e[PUNISHMENT]");
		msgDefaults.put("subtitleWithAmountChallengeChange", "&2Bestrafung: &e[PUNISHMENT]&7(&a[AMOUNT]&7)");
		msgDefaults.put("customHealthSign", "__Wähle die Anzahl_der Herzen oben!");
		msgDefaults.put("mlgSign", "__Anordnung ist_frueh spaet Hoehe");
		msgDefaults.put("onBlockSign", "__Anordnung ist_frueh spaet");
		msgDefaults.put("punishmentAmountSign", "_Wähle einen Wert_zwischen 1 und 10!_(beide inklusive)");
		
		msgDefaults.put("signCorrect", "&7Einstellungen erfolgreich aktualisiert.");
		msgDefaults.put("signTooLowWrong", "&7Eine der Zahlen ist zu niedrig.");
		msgDefaults.put("signLatestLowerThanEarliestWrong", "&7Maximal kann nicht kleiner als Minimal sein.");
		msgDefaults.put("signNoNumberInRange", "&7''&a[NUMBER]&7'' muss zwischen 1 und 10 sein (inklusive).");
		
		msgDefaults.put("onBlockHidden", "&7Waiting for block...");
		msgDefaults.put("onBlockShown", "&7Stand on &e[BLOCK] &7in &2[TIME]&7!");
		
		msgDefaults.keySet().stream()
		.filter(key -> !cfg.isSet(key))
		.forEachOrdered(key -> cfg.set(key, msgDefaults.get(key)));
		
		saveCustomYml(cfg, file);
		msgDefaults.clear();
	}
}
