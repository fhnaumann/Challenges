package me.wand555.Challenges.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;
import me.wand555.EndLinking.EndHelper;
import me.wand555.NetherLinking.Gate;

public class WorldUtil extends ConfigUtil {
	
	public static void storeInbetweenPunishment(Player p) {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, p.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Challenge.Inventory", Arrays.asList(p.getInventory().getContents()));
		saveCustomYml(cfg, file);
	}
	
	public static void punishmentClearOneItem(Player p) {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, p.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> inv = (ArrayList<ItemStack>)cfg.getList("Challenge.Inventory");
		if(inv.size() == 0) return;
		ArrayList<ItemStack> invNonNull = inv.stream().filter(item -> item != null).collect(Collectors.toCollection(ArrayList::new));
		ItemStack randomItem = invNonNull.get(ThreadLocalRandom.current().nextInt(0, invNonNull.size()));
		inv.set(inv.indexOf(randomItem), null);		
		cfg.set("Challenge.Inventory", inv);
		saveCustomYml(cfg, file);
	}
	
	public static void punishmentClearAllItems(Player p) {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, p.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> inv = (ArrayList<ItemStack>)cfg.getList("Challenge.Inventory");
		if(inv.size() == 0) return;
		for(int i=0; i<inv.size(); i++) {
			inv.set(i, null);
		}
		cfg.set("Challenge.Inventory", inv);
		saveCustomYml(cfg, file);
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPlayerInformationInChallengeAndApply(Player player) {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, player.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		if(cfg.getString("Challenge.Location") == null) {
			player.teleport(Bukkit.getWorld("ChallengeOverworld").getSpawnLocation());
			player.getInventory().clear();
			return;
		}
		player.teleport(deserializeLocationDetailed(cfg.getString("Challenge.Location")));
		player.setGameMode(GameMode.valueOf(cfg.getString("Challenge.GameMode")));
		if(cfg.isSet("Challenge.Health.MaxHealth")) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cfg.getDouble("Challenge.Health.MaxHealth"));
		player.setHealth(cfg.isSet("Challenge.Health.Health") ? cfg.getDouble("Challenge.Health.Health") : player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
		player.setHealthScale(player.getHealth());
		player.setFoodLevel(cfg.getInt("Challenge.Hunger"));
		player.setSaturation((float)cfg.getDouble("Challenge.Saturation"));
		player.setExp((float)cfg.getDouble("Challenge.Experience"));
		player.setRemainingAir(cfg.getInt("Challenge.Air"));
		if(cfg.isSet("Challenge.Inventory")) {
			player.getInventory().setContents(((ArrayList<ItemStack>)cfg.getList("Challenge.Inventory")).stream().toArray(ItemStack[]::new));
		}
		else {
			player.getInventory().clear();
		}
		player.addPotionEffects(deserializePotionEffects(cfg.getStringList("Challenge.PotionEffects")));
		
		Object vehicle = cfg.get("Challenge.Vehicle.Location");
		if(vehicle != null) {
			EntityType type = EntityType.valueOf(cfg.getString("Challenge.Vehicle.Type"));
			Location vehicleLoc = deserializeLocationDetailed(cfg.getString("Challenge.Vehicle.Location"));
			Collection<Entity> entities = vehicleLoc.getWorld().getNearbyEntities(vehicleLoc, 1, 1, 1, e -> e.getType() == type);
			if(entities.size() > 0) {
				entities.stream().findFirst().get().addPassenger(player);
			}
		}
	}
	
	
	public static void storePlayerInformationInChallenge(Player player) {
		clearPlayerChallengeFile(player.getUniqueId().toString()+".yml");
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, player.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Challenge.Location", serializeLocationDetailed(player.getLocation()).replace('.', ','));
		cfg.set("Challenge.GameMode", player.getGameMode().toString());
		cfg.set("Challenge.Health.Health", player.getHealth());
		cfg.set("Challenge.Health.MaxHealth", player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		cfg.set("Challenge.Hunger", player.getFoodLevel());
		cfg.set("Challenge.Saturation", player.getSaturation());
		cfg.set("Challenge.Experience", player.getExp());
		cfg.set("Challenge.Air", player.getRemainingAir());
		cfg.set("Challenge.Inventory", Arrays.asList(player.getInventory().getContents())); //maybe has to be converted to list before
		cfg.set("Challenge.PotionEffects", serializePotionEffects(player.getActivePotionEffects()));
		if(player.isInsideVehicle()) {
			cfg.set("Challenge.Vehicle.Location", serializeLocationDetailed(player.getVehicle().getLocation())); //check if vehicle loc stays same on loading information
			cfg.set("Challenge.Vehicle.Type", player.getVehicle().getType().toString());
		}
		saveCustomYml(cfg, file);
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPlayerInformationBeforeChallengeAndApply(Player player) {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, player.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				
		if(cfg.getString("Normal.Location") == null) return;
		player.teleport(deserializeLocationDetailed(cfg.getString("Normal.Location")));
		player.setGameMode(GameMode.valueOf(cfg.getString("Normal.GameMode")));
		if(cfg.isSet("Normal.Health.MaxHealth")) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cfg.getDouble("Normal.Health.MaxHealth"));
		player.setHealth(cfg.isSet("Normal.Health.Health") ? cfg.getDouble("Normal.Health.Health") : player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
		player.setHealthScale(player.getHealth());
		player.setFoodLevel(cfg.getInt("Normal.Hunger"));
		player.setSaturation((float)cfg.getDouble("Normal.Saturation"));
		player.setExp((float)cfg.getDouble("Normal.Experience"));
		player.setRemainingAir(cfg.getInt("Normal.Air"));
		player.getInventory().setContents(((ArrayList<ItemStack>)cfg.getList("Normal.Inventory")).stream().toArray(ItemStack[]::new));
		player.addPotionEffects(deserializePotionEffects(cfg.getStringList("Normal.PotionEffects")));
		
		Object vehicle = cfg.get("Normal.Vehicle.Location");
		if(vehicle != null) {
			EntityType type = EntityType.valueOf(cfg.getString("Normal.Vehicle.Type"));
			Location vehicleLoc = deserializeLocationDetailed(cfg.getString("Normal.Vehicle.Location"));
			Collection<Entity> entities = vehicleLoc.getWorld().getNearbyEntities(vehicleLoc, 1, 1, 1, e -> e.getType() == type);
			if(entities.size() > 0) {
				entities.stream().findFirst().get().addPassenger(player);
			}
		}
	}
	
	public static void storePlayerInformationBeforeChallenge(Player player) {
		clearPlayerNormalFile(player.getUniqueId().toString()+".yml");
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, player.getUniqueId().toString()+".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Normal.Location", serializeLocationDetailed(player.getLocation()).replace('.', ','));
		cfg.set("Normal.GameMode", player.getGameMode().toString());
		//player.setGameMode(GameMode.SURVIVAL);
		cfg.set("Normal.Health.Health", player.getHealth());
		cfg.set("Normal.Health.MaxHealth", player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		//player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		cfg.set("Normal.Hunger", player.getFoodLevel());
		//player.setFoodLevel(20);
		cfg.set("Normal.Saturation", player.getSaturation());
		//player.setSaturation(10);
		cfg.set("Normal.Experience", player.getExp());
		//player.setExp(0);
		cfg.set("Normal.Air", player.getRemainingAir());
		//player.setRemainingAir(300);
		cfg.set("Normal.Inventory", Arrays.asList(player.getInventory().getContents())); //maybe has to be converted to list before
		//player.getInventory().clear();
		cfg.set("Normal.PotionEffects", serializePotionEffects(player.getActivePotionEffects()));
		//player.getActivePotionEffects().clear();
		if(player.isInsideVehicle()) {
			cfg.set("Normal.Vehicle.Location", serializeLocationDetailed(player.getVehicle().getLocation())); //check if vehicle loc stays same on loading information
			cfg.set("Normal.Vehicle.Type", player.getVehicle().getType().toString());
		}
		saveCustomYml(cfg, file);
		
		
	}
	
	/*
	private static void removePlayersFromChallengeWorlds() {
		World mainWorld = Bukkit.getWorlds().get(0);
		ChallengeProfile.getInstance().getParticipantsAsPlayers().stream().forEach(p -> p.teleport(mainWorld.getSpawnLocation(), TeleportCause.PLUGIN));
	}*/
	
	private static void unloadChallengeWorlds() {
		//removePlayersFromChallengeWorlds();
		WorldLinkManager.worlds.stream().forEach(w -> Bukkit.unloadWorld(w, false));
	}
	
	public static void deletePositionData() {
		new File(PLUGIN.getDataFolder()+"", "positions.yml").delete();
		ChallengeProfile.getInstance().getPosManager().getPositions().clear();
	}
	
	public static void deletePortalData() {
		Gate.getGates().clear();
		EndHelper.reset();
		boolean b = new File(PLUGIN.getDataFolder()+"", "netherportals.yml").delete();
		boolean b2 = new File(PLUGIN.getDataFolder()+"", "endportals.yml").delete();
	}
	
	private static void deletePlayerData() {
		new File(PLUGIN.getDataFolder()+"", "backpack.yml").delete();
		
		try {
			FileUtils.deleteDirectory(new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		ChallengeProfile.getInstance().getParticipantsAsPlayers().stream().forEach(p -> {
			System.out.println("Deleting player: " + p.getUniqueId().toString());
			File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"PlayerData"+File.separatorChar, p.getUniqueId().toString()+".yml");
			System.out.println(file.getPath());
			//YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			//cfg.getKeys(false).forEach(s -> {
			//	System.out.println(s);
			//	cfg.set(s, null);
			//});
			//saveCustomYml(cfg, file);
			boolean b = file.delete();
			System.out.println(b);
		});*/
		new File(PLUGIN.getDataFolder()+"", "profilesAndTimers.yml").delete();
	}
	
	private static void deleteWorld(File path) {
		if(path.exists()) {
			for(File file : path.listFiles()) {
				if(file.isDirectory()) {
					deleteWorld(file);
				}
				else {
					//System.out.println("deleting...");
					file.delete();
				}
			}
		}
		path.delete();
		return;
	}
	
	public static void deleteChallengeWorldsAndPlayerData() {	
		deletePlayerData();
		//GenericChallenge.clearAllChallenges();
		unloadChallengeWorlds();
		for(World w : WorldLinkManager.worlds) {
			File path = new File(w.getWorldFolder(), "");
			deleteWorld(path);
		}
	} 
}
