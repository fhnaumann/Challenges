package me.wand555.Challenges.ChallengeProfile;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.Challenges;

public class Backpack {

	public static final int BACKPACK_SIZE = 27;
	private final Challenges plugin;
	
	private HashSet<UUID> openPlayers = new HashSet<UUID>();
	private ItemStack[] contents = new ItemStack[27];
	
	public Backpack(Challenges plugin) {
		this.plugin = plugin;
	}
	
	public void addToOpenPlayers(UUID uuid) {
		openPlayers.add(uuid);
	}
	
	public void removeFromOpenPlayers(UUID uuid) {
		openPlayers.remove(uuid);
	}
	
	public void updateInventories() {
		openPlayers.stream()
			.map(Bukkit::getPlayer)
			.forEach(p -> Bukkit.getScheduler()
					.runTaskLater(plugin, () -> p.updateInventory(), 1L));
	}

	/**
	 * @return the openPlayers
	 */
	public HashSet<UUID> getOpenPlayers() {
		return openPlayers;
	}

	/**
	 * @param openPlayers the openPlayers to set
	 */
	public void setOpenPlayers(HashSet<UUID> openPlayers) {
		this.openPlayers = openPlayers;
	}

	/**
	 * @return the contents
	 */
	public ItemStack[] getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}

	/**
	 * @return the backpackSize
	 */
	public static int getBackpackSize() {
		return BACKPACK_SIZE;
	}
}
