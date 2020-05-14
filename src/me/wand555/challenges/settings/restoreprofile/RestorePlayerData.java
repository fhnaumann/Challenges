package me.wand555.challenges.settings.restoreprofile;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RestorePlayerData {

	private UUID uuid;
	private ArrayList<ItemStack> invContent = new ArrayList<ItemStack>();
	private Location playerLoc;
	private GameMode gameMode;
	
	public RestorePlayerData(Player p) {
		this.uuid = p.getUniqueId();
		this.invContent = Stream.of(p.getInventory().getContents()).collect(Collectors.toCollection(ArrayList::new));
		this.playerLoc = p.getLocation();
		this.gameMode = p.getGameMode();
	}
	
	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @return the invContent
	 */
	public ArrayList<ItemStack> getInvContent() {
		return invContent;
	}

	/**
	 * @return the playerLoc
	 */
	public Location getPlayerLoc() {
		return playerLoc;
	}

	/**
	 * @return the gameMode
	 */
	public GameMode getGameMode() {
		return gameMode;
	}
}
