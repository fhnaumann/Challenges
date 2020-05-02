package me.wand555.Challenges.Config;

import java.io.File;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.LavaGroundChallenge.BlockChangeTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.LavaGroundChallenge.BlockStatus;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.LavaGroundChallenge.LavaGroundBlockData;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.LavaGroundChallenge.LavaGroundChallenge;

public class ConfigUtil {
	
	protected static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	protected static ArrayList<String> serializeFloorIsLavaTimers(Set<BlockChangeTimer> timers) {
		return timers.stream()
				.map(timer -> serializeLocation(timer.getBlockLoc()) + "\"" + timer.getData().getStatus().toString() + "\"" + timer.getData().getPreviousMaterial().toString())
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	protected static Set<BlockChangeTimer> deserializeFloorIsLavaTimers(LavaGroundChallenge challenge, List<String> timers) {
		Set<BlockChangeTimer> newTimers = new HashSet<BlockChangeTimer>();
		for(String string : timers) {
			String[] splitted = string.split("\"");
			Location blockLoc = deserializeLocation(splitted[0]);
			LavaGroundBlockData data = new LavaGroundBlockData(BlockStatus.valueOf(splitted[1]), Material.matchMaterial(splitted[2]));
			newTimers.add(new BlockChangeTimer(PLUGIN, challenge, blockLoc, data));
		}
		return newTimers;
	}
	
	protected static ArrayList<String> serializePotionEffects(Collection<PotionEffect> effects) {
		return effects.stream().map(pot -> pot.getType().getName()+"/"+pot.getDuration()+"/"+pot.getAmplifier()+"/"
				+pot.isAmbient()+"/"+pot.hasParticles()+"/"+pot.hasIcon()).collect(Collectors.toCollection(ArrayList::new));
		//PotionEffect effect, boolean ambient, boolean particles, boolean icon
	}
	
	protected static ArrayList<PotionEffect> deserializePotionEffects(List<String> potionList) {	
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
		potionList.forEach(effect -> 
		{
			String[] args = ((String) effect).split("/");
			effects.add(new PotionEffect(
					PotionEffectType.getByName(
					args[0]), 
					Integer.valueOf(args[1]), 
					Integer.valueOf(args[2]),
					Boolean.valueOf(args[3]), 
					Boolean.valueOf(args[4]), 
					Boolean.valueOf(args[5])));
		});
	
		//Lösung darüber finden: this.getConfig().getList("Thirst.Low.Effects").stream().map(blablabla)
		return effects;
	}
	
	protected static void saveCustomYml(FileConfiguration cfg, File file) {
		   try {
			   cfg.save(file);
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
	   }
	
	protected static String serializeLocationDetailed(Location loc) {
		return loc.getWorld().getName()+"/"+loc.getX()+"/"+loc.getY()+"/"+loc.getZ()+"/"+loc.getYaw()+"/"+loc.getPitch();
	}
	
	protected static Location deserializeLocationDetailed(String path) {
		String[] args = path.replace(',', '.').split("/");
		return new Location(Bukkit.getWorld(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]),
				Float.valueOf(args[4]), Float.valueOf(args[5]));
	}
	
	protected static String serializeLocation(Location loc) {
		return loc.getWorld().getName()+"/"+loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ();
	}
	
	protected static Location deserializeLocation(String path) {
		String[] args = path.split("/");
		return new Location(Bukkit.getWorld(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
	}
	
	protected static void checkOrdner() {
		   File file = new File(PLUGIN.getDataFolder()+"");
		   if(!file.exists()) {
			   file.mkdir();
		   }
	}
	
	protected static void checkLangOrdner(String lang) {
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 lang+".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	protected static void clearFile(String fileName) {
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Data", fileName);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.getKeys(false).forEach(key -> cfg.set(key, null));
		saveCustomYml(cfg, file);
	}
	
	protected static void clearPlayerNormalFile(String playerFilename) {
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Data"+File.separatorChar+"PlayerData"+File.separatorChar, playerFilename);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.getKeys(false).stream().filter(key -> key.equalsIgnoreCase("Normal")).forEach(key -> cfg.set(key, null));
		saveCustomYml(cfg, file);
	}
	
	protected static void clearPlayerChallengeFile(String playerFilename) {
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Data"+File.separatorChar+"PlayerData"+File.separatorChar, playerFilename);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.getKeys(false).stream().filter(key -> key.equalsIgnoreCase("Challenge")).forEach(key -> cfg.set(key, null));
		saveCustomYml(cfg, file);
	}
}
