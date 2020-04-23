
package me.wand555.Challenges.Config;



import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.ChallengeProfile.Backpack;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.Settings;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.EndOnDeathChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NetherFortressSpawnChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockBreakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockPlacingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoCraftingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoDamageChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoRegenerationChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoRegenerationHardChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoSneakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedBlockDropsChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedCraftingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedMobDropsChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallengeStatus;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.Positions.Position;
import me.wand555.Challenges.Timer.SecondTimer;
import me.wand555.Challenges.Timer.TimerMessage;
import me.wand555.Challenges.Timer.TimerOrder;
import me.wand555.EndLinking.ObsidianPlatform;
import me.wand555.NetherLinking.Gate;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler extends ConfigUtil {
	
	public static void storeToConfig() {
		storeNetherPortalToConfig();
		storeEndPortalToConfig();
		storeChallengeProfilesAndTimers();
		storePositionsToConfig();
		storeBackpackToConfig();
	}
	
	public static void loadFromConfig() {
		loadNetherPortalFromConfig();
		loadEndPortalFromConfig();
		loadChallengeProfilesAndTimers();
		loadPositionsFromConfig();
		loadBackpackFromConfig();
	}
	
	private static void loadBackpackFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "backpack.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ChallengeProfile.getInstance().getBackpack().setEnabled(cfg.getBoolean("Status"));
		List<?> list = cfg.getList("Content");
		if(list == null) {
			ChallengeProfile.getInstance().getBackpack().setContents(new ItemStack[Backpack.BACKPACK_SIZE]);
		} else ChallengeProfile.getInstance().getBackpack().setContents(list.toArray(new ItemStack[list.size()]));
	}
	
	private static void storeBackpackToConfig() {
		clearFile("backpack.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "backpack.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Status", ChallengeProfile.getInstance().getBackpack().isEnabled());
		cfg.set("Content", Arrays.asList(ChallengeProfile.getInstance().getBackpack().getContents()));
		saveCustomYml(cfg, file);
	}
	
	private static void loadPositionsFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "positions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(String name : cfg.getKeys(false)) {
			ChallengeProfile.getInstance().getPosManager().addToPositions(new Position(
					name, 
					deserializeLocation(cfg.getString(name+".Location")), 
					UUID.fromString(cfg.getString(name+".Creator").trim()), 
					cfg.getString(name+".Date")));
		}
	}
	
	private static void storePositionsToConfig() {
		clearFile("positions.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "positions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(Position pos : ChallengeProfile.getInstance().getPosManager().getPositions()) {
			cfg.set(pos.getName()+".Location", serializeLocation(pos.getLocation()));
			cfg.set(pos.getName()+".Creator", pos.getCreator().toString());
			cfg.set(pos.getName()+".Date", pos.getDate());
		}
		saveCustomYml(cfg, file);
	}
	
	private static void loadChallengeProfilesAndTimers() {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "profilesAndTimers.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cProfile.setParticipants(cfg.getStringList("Participants").stream()
				.map(s -> UUID.fromString(s.trim()))
				.filter(uuid -> Bukkit.getPlayer(uuid) != null)
				.collect(Collectors.toCollection(HashSet::new)));
		//has to be done this way, because I need a new instance, but the timer shouldnt continue.
		cProfile.hasStarted = cfg.getBoolean("hasStarted");
		cProfile.isPaused = cProfile.hasStarted ? true : false;
		new EndOnDeathChallenge().setActive(cfg.getBoolean("endOnDeath"));
		new NetherFortressSpawnChallenge().setActive(cfg.getBoolean("spawnNearFortress"));
		new NoDamageChallenge().setActive(cfg.getBoolean("noDamage.Boolean"));
		new NoRegenerationChallenge().setActive(cfg.getBoolean("noReg"));
		new NoRegenerationHardChallenge().setActive(cfg.getBoolean("noRegHard"));
		new CustomHealthChallenge().setActive(cfg.getBoolean("customHealth"));
		new SharedHealthChallenge().setActive(cfg.getBoolean("sharedHealth"));
		new NoBlockPlacingChallenge().setActive(cfg.getBoolean("noBlockPlacing.Boolean"));
		new NoBlockBreakingChallenge().setActive(cfg.getBoolean("noBlockBreaking.Boolean"));
		new NoCraftingChallenge().setActive(cfg.getBoolean("noCrafting.Boolean"));
		new NoSneakingChallenge().setActive(cfg.getBoolean("noSneaking.Boolean"));
		new RandomizedBlockDropsChallenge().setActive(cfg.getBoolean("randomizedBlockDrops"));
		new RandomizedMobDropsChallenge().setActive(cfg.getBoolean("randomizedMobDrops"));
		new RandomizedCraftingChallenge().setActive(cfg.getBoolean("randomizedCrafting"));	
		new MLGChallenge().setActive(cfg.getBoolean("randomMLG"));
		new OnBlockChallenge().setActive(cfg.getBoolean("onBlock.Boolean"));
		new ItemCollectionLimitGlobalChallenge().setActive(cfg.getBoolean("itemCollectionLimitGlobal.Boolean"));
		
		if(cProfile.hasStarted) {	
			cProfile.setSecondTimer(new SecondTimer(PLUGIN, cfg.getLong("Timer")));
			cProfile.getSecondTimer().setOrder(TimerOrder.valueOf(cfg.getString("Order")));
			
			MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
			if(mlgChallenge.isActive()) {
				
				mlgChallenge.setPunishType(PunishType.valueOf(cfg.getString("MLG.Punishment")));
				mlgChallenge.setEarliest(cfg.getInt("MLG.Earliest"));
				mlgChallenge.setLatest(cfg.getInt("MLG.Latest"));
				mlgChallenge.setHeight(cfg.getInt("MLG.Height"));
				if(cfg.isSet("MLG.TimeToMLG")) {
					mlgChallenge.setTimer(new MLGTimer(PLUGIN, cfg.getLong("MLG.TotalTimeToMLG"), cfg.getLong("MLG.TimeToMLG")));
				}
			}
			CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
			if(cHealthChallenge.isActive()) {
				cHealthChallenge.setAmount(cfg.getDouble("customHealthAmount"));
			}
			NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
			if(noDamageChallenge.isActive()) {
				noDamageChallenge.setPunishType(PunishType.valueOf(cfg.getString("noDamage.Punishment")));
			}
			SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
			if(sHChallenge.isActive()) {
				sHChallenge.setSharedHealth(cfg.getDouble("sharedHealthAmount"));
			}
			NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
			if(nBPChallenge.isActive()) {
				nBPChallenge.setPunishType(PunishType.valueOf(cfg.getString("noBlockPlacing.Punishment")));
			}
			NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
			if(nBBChallenge.isActive()) {
				nBBChallenge.setPunishType(PunishType.valueOf(cfg.getString("noBlockBreaking.Punishment")));
			}
			NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
			if(nCChallenge.isActive()) {
				nCChallenge.setPunishType(PunishType.valueOf(cfg.getString("noCrafting.Punishment")));
			}
			NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
			if(nSChallenge.isActive()) {
				nSChallenge.setPunishType(PunishType.valueOf(cfg.getString("noSneaking.Punishment")));
			}
			//It doesnt have to be block drops, I just need any so I can access the static map
			RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
			if(!RandomChallenge.clearRandomizationIfCase()) {
				randomChallenge.setRandomizedMapped(cfg.getStringList("randomizedDrops").stream()
						.collect(Collectors.toMap(
								string -> Material.valueOf(((String) string).split(",")[0]), 
								string -> Material.valueOf(((String) string).split(",")[1]), 
								(v1, v2) -> v1, 
								LinkedHashMap::new)));
			}
			OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
			if(onBlockChallenge.isActive()) {
				onBlockChallenge.setEarliestToShow(cfg.getInt("onBlock.EarliestToShow"));
				onBlockChallenge.setLatestToShow(cfg.getInt("onBlock.LatestToShow"));
				onBlockChallenge.setEarliestOnBlock(cfg.getInt("onBlock.EarliestOnBlock"));
				onBlockChallenge.setLatestOnBlock(cfg.getInt("onBlock.LatestOnBlock"));
				onBlockChallenge.setStatus(OnBlockChallengeStatus.valueOf(cfg.getString("onBlock.Status")));
				if(cfg.isSet("onBlock.ToStayOn")) onBlockChallenge.setToStayOn(Material.valueOf(cfg.getString("onBlock.ToStayOn")));
				//message will be constructed in Timer constructor		
				if(cfg.isSet("onBlock.TimeTo")) {
					//not really necessary, the boss bar just cannot be null
					onBlockChallenge.createBossBar("", BarColor.WHITE);
					onBlockChallenge.setTimer(new OnBlockTimer(PLUGIN, 
							onBlockChallenge, 
							cfg.getLong("onBlock.TotalTimeTo"), 
							cfg.getLong("onBlock.TimeTo")));
				}
			}
			ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
			if(iCLGChallenge.isActive()) {
				iCLGChallenge.setCurrentAmount(cfg.getInt("itemCollectionLimitGlobal.currentAmount"));
				iCLGChallenge.setLimit(cfg.getInt("itemCollectionLimitGlobal.Limit"));
				if(cfg.isList("itemCollectionLimitGlobal.uniqueItems")) {
					iCLGChallenge.setUniqueItems(cfg.getStringList("itemCollectionLimitGlobal.uniqueItems").stream()
							.filter(obj -> obj != null)
							.collect(Collectors.toMap(
									string -> Material.valueOf(((String) string).split(",")[0]), 
									string -> UUID.fromString(string.trim()),
									(v1, v2) -> v2,
									HashMap::new)));
				}		
			}
		}
		else {
			cProfile.setSecondTimer(new SecondTimer(PLUGIN, TimerMessage.START_TIMER));
		}
		
		
		
			/*
			noDamage = cfg.getBoolean("noDamage");
			noReg = cfg.getBoolean("noReg");
			noRegHard = cfg.getBoolean("noRegHard");
			isCustomHealth = cfg.getBoolean("isCustomHealth");
			customHP = cfg.getInt("customHP");
			isSharedHealth = cfg.getBoolean("isSharedHealth");
			sharedHP = cfg.getInt("sharedHP");
			noBlockPlace = cfg.getBoolean("noBlockPlace");
			noBlockBreaking = cfg.getBoolean("noBlockBreaking");
			noCrafting = cfg.getBoolean("noCrafting");
			noSneaking = cfg.getBoolean("noSneaking");	
			isRandomizedBlockDrops = cfg.getBoolean("randomizeBlockDrops");
			isRandomizedMobDrops = cfg.getBoolean("randomizeMobDrops");
			isRandomizedCrafting = cfg.getBoolean("randomizeCrafting");
			isMLG = cfg.getBoolean("isMLG.Bool");
		}	
		
		if(isRandomizedBlockDrops || isRandomizedMobDrops || isRandomizedCrafting) {
			ChallengeProfile.setRandomizeMapped(cfg.getStringList("randomizedDropsPattern").stream()
					.collect(Collectors.toMap(
							string -> Material.valueOf(((String) string).split(",")[0]), 
							string -> Material.valueOf(((String) string).split(",")[1]), 
							(v1, v2) -> v1, 
							HashMap::new)));
		}
		
		if(hasStarted) {
			ChallengeProfile.setSecondTimer(new SecondTimer(PLUGIN, TimerMessage.TIMER_PAUSED));
			ChallengeProfile.getSecondTimer().setTime(cfg.getLong("Timer"));
		}
		else {
			ChallengeProfile.setSecondTimer(new SecondTimer(PLUGIN, TimerMessage.START_TIMER));
		}
		
		if(isMLG) {
			MLG.setMlg(new MLG(cfg.getInt("isMLG.Earliest"), cfg.getInt("isMLG.Latest"), cfg.getInt("isMLG.Height")));
			if(cfg.isSet("isMLG.TimeToMLG")) {
				long timeToMLG = cfg.getLong("isMLG.TimeToMLG");
				ChallengeProfile.getSecondTimer().setMlgTime(timeToMLG);
			}
		}*/
	
	}
	
	private static void storeChallengeProfilesAndTimers() {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		clearFile("profilesAndTimers.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "profilesAndTimers.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Participants", ChallengeProfile.getInstance().getParticipants().stream().map(uuid -> uuid.toString()).collect(Collectors.toList())); //maybe convert to List
		cfg.set("hasStarted", cProfile.hasStarted);
		cfg.set("endOnDeath", GenericChallenge.isActive(ChallengeType.END_ON_DEATH));
		cfg.set("spawnNearFortress", GenericChallenge.isActive(ChallengeType.NETHER_FORTRESS_SPAWN));
		cfg.set("noDamage.Boolean", GenericChallenge.isActive(ChallengeType.NO_DAMAGE));
		cfg.set("noReg", GenericChallenge.isActive(ChallengeType.NO_REG));
		cfg.set("noRegHard", GenericChallenge.isActive(ChallengeType.NO_REG_HARD));
		cfg.set("customHealth", GenericChallenge.isActive(ChallengeType.CUSTOM_HEALTH));
		cfg.set("sharedHealth", GenericChallenge.isActive(ChallengeType.SHARED_HEALTH));
		cfg.set("noBlockPlacing.Boolean", GenericChallenge.isActive(ChallengeType.NO_BLOCK_PLACING));
		cfg.set("noBlockBreaking.Boolean", GenericChallenge.isActive(ChallengeType.NO_BLOCK_BREAKING));
		cfg.set("noCrafting.Boolean", GenericChallenge.isActive(ChallengeType.NO_CRAFTING));
		cfg.set("noSneaking.Boolean", GenericChallenge.isActive(ChallengeType.NO_SNEAKING));
		cfg.set("randomizedBlockDrops", GenericChallenge.isActive(ChallengeType.RANDOMIZE_BLOCK_DROPS));
		cfg.set("randomizedMobDrops", GenericChallenge.isActive(ChallengeType.RANDOMIZE_MOB_DROPS));
		cfg.set("randomizedCrafting", GenericChallenge.isActive(ChallengeType.RANDOMIZE_CRAFTING));		
		cfg.set("randomMLG", GenericChallenge.isActive(ChallengeType.MLG));
		cfg.set("onBlock.Boolean", GenericChallenge.isActive(ChallengeType.ON_BLOCK));
		cfg.set("itemCollectionLimitGlobal.Boolean", GenericChallenge.isActive(ChallengeType.ITEM_LIMIT_GLOBAL));
		
		if(cProfile.hasStarted) {
			cfg.set("Order", cProfile.getSecondTimer().getOrder().toString());
			cfg.set("Timer", cProfile.getSecondTimer().getTime());
			MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
			if(mlgChallenge.isActive()) {
				cfg.set("MLG.Punishment", mlgChallenge.getPunishType().toString());
				cfg.set("MLG.Earliest", mlgChallenge.getEarliest());
				cfg.set("MLG.Latest", mlgChallenge.getLatest());
				cfg.set("MLG.Height", mlgChallenge.getHeight());
				if(mlgChallenge.getTimer() != null) {
					cfg.set("MLG.TimeToMLG", mlgChallenge.getTimer().getTimeToMLG());
					cfg.set("MLG.TotalTimeToMLG", mlgChallenge.getTimer().getTotalTimeToMLG());
				}
			}
			CustomHealthChallenge cHealthChallenge = GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH);
			if(cHealthChallenge.isActive()) {
				cfg.set("customHealthAmount", cHealthChallenge.getAmount());
			}
			SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
			if(sHChallenge.isActive()) {
				cfg.set("sharedHealthAmount", sHChallenge.getSharedHealth());
			}
			NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
			if(noDamageChallenge.isActive()) {
				cfg.set("noDamage.Punishment", noDamageChallenge.getPunishType().toString());
			}
			NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
			if(nBPChallenge.isActive()) {
				cfg.set("noBlockPlacing.Punishment", nBPChallenge.getPunishType().toString());
			}
			NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
			if(nBBChallenge.isActive()) {
				cfg.set("noBlockBreaking.Punishment", nBBChallenge.getPunishType().toString());
			}
			NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
			if(nCChallenge.isActive()) {
				cfg.set("noCrafting.Punishment", nCChallenge.getPunishType().toString());
			}
			NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
			if(nSChallenge.isActive()) {
				cfg.set("noSneaking.Punishment", nSChallenge.getPunishType().toString());
			}
			//It doesnt have to be block drops, I just need any so I can access the static map
			RandomChallenge randomChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
			if(!RandomChallenge.clearRandomizationIfCase()) {
				cfg.set("randomizedDrops", randomChallenge.getRandomizeMapped().entrySet().stream()
						.map(entry -> entry.getKey().toString() + "," + entry.getValue().toString())
						.collect(Collectors.toList()));
			}
			
			OnBlockChallenge onBlockChallenge = GenericChallenge.getChallenge(ChallengeType.ON_BLOCK);
			if(onBlockChallenge.isActive()) {
				cfg.set("onBlock.EarliestToShow", onBlockChallenge.getEarliestToShow());
				cfg.set("onBlock.LatestToShow", onBlockChallenge.getLatestToShow());
				cfg.set("onBlock.EarliestOnBlock", onBlockChallenge.getEarliestOnBlock());
				cfg.set("onBlock.LatestOnBlock", onBlockChallenge.getLatestOnBlock());
				cfg.set("onBlock.Status", onBlockChallenge.getStatus().toString());
				if(onBlockChallenge.getToStayOn() != null) cfg.set("onBlock.ToStayOn", onBlockChallenge.getToStayOn().toString());
				//message has to be constructed on start again, because user can change language while stored in config
				if(onBlockChallenge.getTimer() != null) {
					onBlockChallenge.getBossBar().removeAll();
					cfg.set("onBlock.TimeTo", onBlockChallenge.getTimer().getTimeTo());
					cfg.set("onBlock.TotalTimeTo", onBlockChallenge.getTimer().getTotalTimeTo());
				}
			}
			ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
			if(iCLGChallenge.isActive()) {
				cfg.set("itemCollectionLimitGlobal.currentAmount", iCLGChallenge.getCurrentAmount());
				cfg.set("itemCollectionLimitGlobal.Limit", iCLGChallenge.getLimit());
				cfg.set("itemCollectionLimitGlobal.uniqueItems", iCLGChallenge.getUniqueItems().entrySet().stream()
						.map(entry -> entry.getKey().toString() + "," + entry.getValue().toString())
						.collect(Collectors.toList()));
			}
		}
		saveCustomYml(cfg, file);
	}
		/*
		cfg.set("noDamage", noDamage);
		cfg.set("noReg", noReg);
		cfg.set("noRegHard", noRegHard);
		cfg.set("isCustomHealth", isCustomHealth);
		cfg.set("customHP", customHP);
		cfg.set("isSharedHealth", isSharedHealth);
		cfg.set("sharedHP", sharedHP);
		cfg.set("noBlockPlace", noBlockPlace);
		cfg.set("noBlockBreaking", noBlockBreaking);
		cfg.set("noCrafting", noCrafting);
		cfg.set("noSneaking", noSneaking);
		cfg.set("randomizeBlockDrops", isRandomizedBlockDrops);
		cfg.set("randomizeMobDrops", isRandomizedMobDrops);
		cfg.set("randomizeCrafting", isRandomizedCrafting);
		cfg.set("isMLG.Bool", isMLG);
		if(isMLG) {
			cfg.set("isMLG.Earliest", MLG.getMlg().getEarliest());
			cfg.set("isMLG.Latest", MLG.getMlg().getLatest());
			cfg.set("isMLG.Height", MLG.getMlg().getHeight());
			if(MLG.getMlg().getTimer() != null) {
				//here when MLG is selected and timer wasn't on pause on the beginning.
				cfg.set("isMLG.TimeToMLG", ChallengeProfile.getSecondTimer().getMlgTime());
			}
		}
		//cfg.set("isInMLGRightNow", isInMLGRightNow);
		if(isRandomizedBlockDrops || isRandomizedMobDrops || isRandomizedCrafting) {
			cfg.set("randomizedDropsPattern", 
				ChallengeProfile.getRandomizeMapped().entrySet().stream()
					.map(entry -> entry.getKey().toString() + "," + entry.getValue().toString())
					.collect(Collectors.toList()));
		}
		else {
			cfg.set("randomizedDropsPattern", null);
		}
		if(hasStarted) {
			cfg.set("Timer", ChallengeProfile.getSecondTimer().getTime());
		}
		
		saveCustomYml(cfg, file);
	}*/
	
	private static void loadEndPortalFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "endportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ObsidianPlatform.setCreated(cfg.getBoolean("Created"));
		if(ObsidianPlatform.isCreated()) {
			new ObsidianPlatform(deserializeLocation(cfg.getString("TeleportTo")));
		}
	}
	
	private static void storeEndPortalToConfig() {
		clearFile("endportals.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "endportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Created", ObsidianPlatform.isCreated());
		if(ObsidianPlatform.isCreated()) {
			cfg.set("TeleportTo", serializeLocation(ObsidianPlatform.getPlatform().getTeleportTo()));
		}
		else {
			
		}
		
		saveCustomYml(cfg, file);
	}
	
	private static void loadNetherPortalFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "netherportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(String key : cfg.getKeys(false)) {
			new Gate(cfg.getStringList(key+".FrameBlockLocs").stream().map(sLoc -> deserializeLocation(sLoc).getBlock()).collect(Collectors.toCollection(HashSet::new)), 
					cfg.getStringList(key+".PortalBlockLocs").stream().map(sLoc -> deserializeLocation(sLoc).getBlock()).collect(Collectors.toCollection(HashSet::new)), 
					Axis.valueOf(cfg.getString(key+".Axis")), 
					Environment.valueOf(cfg.getString(key+".Environment")), 
					deserializeLocationDetailed(key));
		}
	}
	
	private static void storeNetherPortalToConfig() {
		clearFile("netherportals.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "netherportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);	
		for(Gate gate : Gate.getGates()) {
			String key = serializeLocationDetailed(gate.getTeleportTo()).replace('.', ',');
			cfg.set(key+".FrameBlockLocs", gate.getFrameBlocks().stream()
					.map(b -> serializeLocation(b.getLocation())).collect(Collectors.toList()));
			cfg.set(key+".PortalBlockLocs", gate.getPortalBlocks().stream()
					.map(b -> serializeLocation(b.getLocation())).collect(Collectors.toList()));
			cfg.set(key+".Axis", gate.getAxis().toString());
			cfg.set(key+".Environment", gate.getEnvironment().toString());
		}
		Gate.getGates().clear();
		saveCustomYml(cfg, file);
	} 
}
