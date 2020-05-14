package me.wand555.challenges.start;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockBreakingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockPlacingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.Punishable;
import me.wand555.challenges.settings.challengeprofile.types.ReasonNotifiable;
import me.wand555.challenges.settings.config.ConfigHandler;
import me.wand555.challenges.settings.config.Language;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.settings.config.UserConfig;
import me.wand555.challenges.settings.gui.GUI;
import me.wand555.challenges.settings.gui.GUIClickListener;
import me.wand555.challenges.settings.gui.SignMenuFactory;
import me.wand555.challenges.settings.listener.BackpackListener;
import me.wand555.challenges.settings.listener.EnderDragonDeathListener;
import me.wand555.challenges.settings.listener.HeightWorldChangeListener;
import me.wand555.challenges.settings.listener.ItemCollectionLimitGlobalListener;
import me.wand555.challenges.settings.listener.LavaGroundListener;
import me.wand555.challenges.settings.listener.MLGListener;
import me.wand555.challenges.settings.listener.NoBlockBreakingListener;
import me.wand555.challenges.settings.listener.NoBlockPlacingListener;
import me.wand555.challenges.settings.listener.NoCraftingListener;
import me.wand555.challenges.settings.listener.NoDamageListener;
import me.wand555.challenges.settings.listener.NoRegListener;
import me.wand555.challenges.settings.listener.NoSameItemListener;
import me.wand555.challenges.settings.listener.NoSneakingListener;
import me.wand555.challenges.settings.listener.PlayerDeathListener;
import me.wand555.challenges.settings.listener.RandomizeListener;
import me.wand555.challenges.settings.listener.SharedHealthPlayerChangeLifeListener;
import me.wand555.challenges.settings.listener.roleassign.PlayerJoinListener;
import me.wand555.challenges.settings.listener.roleassign.PlayerQuitListener;
import me.wand555.challenges.settings.timer.SecondTimer;
import me.wand555.challenges.settings.timer.TimerMessage;
import me.wand555.challenges.worldlinking.WorldLinkManager;
import me.wand555.challenges.worldlinking.nether.PortalListener;

public class Challenges extends JavaPlugin {

	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "Challenge" + ChatColor.GRAY + "] ";
	public static final Random random = new Random();
	
	private CE myCE;
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	
	public void onEnable() {
		UserConfig.setUpDefaultConfig();
		UserConfig.placeLanguages();
		String lang = this.getConfig().getString("Language");
		if(lang.equalsIgnoreCase("en")) LanguageMessages.loadLang(Language.ENGLISH);
		else if(lang.equalsIgnoreCase("de")) LanguageMessages.loadLang(Language.GERMAN);
		else LanguageMessages.loadLang(Language.ENGLISH);
		
		ChallengeProfile.getInstance().initializeScoreBoard();
		initializeWorlds();
		
		ConfigHandler.loadFromConfig();
		
		
		
		/**
		 * 
		 * ON LOAD EVERY CHALLENGE INSTANCE HAS TO BE CREATED ONCE SO IT IS NEVER NULL, DONT DO THAT HERE, DO IT
		 * WHEN LOADING FROM CONFIG WITH THE STORED VALUES (boolean)!!!!!!!!!!!
		 * 
		 * 
		 */
		
		gui = new GUI(this);
		signMenuFactory = new SignMenuFactory(this);
		
		myCE = new CE(this, gui, signMenuFactory);
		this.getCommand("challenge").setExecutor(myCE);
		this.getCommand("timer").setExecutor(myCE);
		this.getCommand("pos").setExecutor(myCE);
		this.getCommand("bp").setExecutor(myCE);
		this.getCommand("hp").setExecutor(myCE);
		this.getCommand("settings").setExecutor(myCE);
		this.getCommand("deathrun").setExecutor(myCE);
		
		registerListeners();	
	}
	
	public void onDisable() {
		ChallengeProfile.getInstance().pauseTimer();
		ConfigHandler.storeToConfig();
		Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
	}
	
	private void registerListeners() {
		new PlayerJoinListener(this);
		new PlayerQuitListener(this);
		new GUIClickListener(this, gui, signMenuFactory);
		new PortalListener(this);
		new EnderDragonDeathListener(this);
		new PlayerDeathListener(this);
		new NoDamageListener(this);
		new NoRegListener(this);
		new SharedHealthPlayerChangeLifeListener(this);
		new NoBlockPlacingListener(this);
		new NoBlockBreakingListener(this);
		new NoCraftingListener(this);
		new NoSneakingListener(this);
		new RandomizeListener(this);
		new MLGListener(this);
		new ItemCollectionLimitGlobalListener(this);
		new NoSameItemListener(this);
		new LavaGroundListener(this);
		new HeightWorldChangeListener(this);
		
		new BackpackListener(this);
	}
	
	public static boolean hasClickedTop(InventoryClickEvent event) {
        return event.getRawSlot() == event.getSlot();
    }
	
	public static void initializeWorlds() {
		Challenges plugin = Challenges.getPlugin(Challenges.class);
		
		Bukkit.broadcastMessage(Challenges.PREFIX + ChatColor.GRAY + "Generating worlds... Please don't move!");
		
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			World overWorld = Bukkit.createWorld(new WorldCreator("ChallengeOverworld").environment(Environment.NORMAL));
			overWorld.setDifficulty(Difficulty.HARD);
			WorldLinkManager.worlds.add(overWorld);
			Bukkit.broadcastMessage(Challenges.PREFIX + ChatColor.GRAY + "Overworld loaded/generated!");
			
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				World netherWorld = Bukkit.createWorld(new WorldCreator("ChallengeNether").environment(Environment.NETHER));
				netherWorld.setDifficulty(Difficulty.HARD);
				WorldLinkManager.worlds.add(netherWorld);
				Bukkit.broadcastMessage(Challenges.PREFIX + ChatColor.GRAY + "Nether loaded/generated!");
				
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					World endWorld = Bukkit.createWorld(new WorldCreator("ChallengeEnd").environment(Environment.THE_END));
					endWorld.setDifficulty(Difficulty.HARD);
					WorldLinkManager.worlds.add(endWorld);
					Bukkit.broadcastMessage(Challenges.PREFIX + ChatColor.GRAY + "End loaded/generated!");
					
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						World mlgWorld = new WorldCreator("MLGWorld")
								.environment(Environment.NORMAL)
								.type(WorldType.FLAT)
								.generateStructures(false)
								.createWorld();
						mlgWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
						mlgWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
						mlgWorld.setDifficulty(Difficulty.PEACEFUL);
						mlgWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
						for(int x=-50; x<=50; x+=16) {
							for(int z=-50; z<=50; z+=16) {
								mlgWorld.getChunkAt(x, z).setForceLoaded(true);
							}
						}
						WorldLinkManager.worlds.add(mlgWorld);
						Bukkit.broadcastMessage(Challenges.PREFIX + ChatColor.GRAY + "MLG World loaded/generated!");
					}, 20*3L);
				}, 20*3L);
			}, 20*3L);
		}, 10L);	
	}
}
