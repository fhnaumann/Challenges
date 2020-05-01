package me.wand555.GUI.Holders;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.wand555.Challenges.ChallengeProfile.InventoryManager;
public class SettingsHolder implements InventoryHolder {

	private final Inventory inv;
	
	public SettingsHolder() {
		this.inv = Bukkit.createInventory(this, InventoryManager.SETTINGS_GUI_SIZE, "Settings");
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}

}
