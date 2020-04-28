package me.wand555.Challenges.ChallengeProfile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

	private InventoryManager inventoryManager;
	
	private Inventory settingsGUI;
	private Inventory punishmentGUI;
	private Inventory alreadyCollectedGUI;
	private Inventory backpackGUI;
	
	public static final int SETTINGS_GUI_SIZE = 27;
	public static final int PUNISHMENT_GUI_SIZE = 36;
	public static final int ALREADY_COLLECTED_GUI_SIZE = 54;
	public static final int BACKPACK_GUI_SIZE = 27;
	
	InventoryManager() {}
	
	/**
	 * @return the alreadyCollectedGUI
	 */
	public Inventory getAlreadyCollectedGUI() {
		if(alreadyCollectedGUI == null) 
			alreadyCollectedGUI = Bukkit.getServer().createInventory(null, ALREADY_COLLECTED_GUI_SIZE	, ChatColor.DARK_GREEN + "Collected Items");
		return alreadyCollectedGUI;
	}

	/**
	 * @param alreadyCollectedGUI the alreadyCollectedGUI to set
	 */
	public void setAlreadyCollectedGUI(Inventory alreadyCollectedGUI) {
		this.alreadyCollectedGUI = alreadyCollectedGUI;
	}

	/**
	 * @return the punishmentGUI
	 */
	public Inventory getPunishmentGUI() {
		if(punishmentGUI == null)
			punishmentGUI = Bukkit.getServer().createInventory(null, PUNISHMENT_GUI_SIZE, ChatColor.RED + "Punishments");
		return punishmentGUI;
	}

	/**
	 * @param punishmentGUI the punishmentGUI to set
	 */
	public void setPunishmentGUI(Inventory punishmentGUI) {
		this.punishmentGUI = punishmentGUI;
	}

	/**
	 * @return the settingsGUI
	 */
	public Inventory getSettingsGUI() {
		if(settingsGUI == null)
			settingsGUI = Bukkit.getServer().createInventory(null, SETTINGS_GUI_SIZE, ChatColor.GREEN + "Settings");
		return settingsGUI;
	}

	/**
	 * @param settingsGUI the settingsGUI to set
	 */
	public void setSettingsGUI(Inventory settingsGUI) {
		this.settingsGUI = settingsGUI;
	}

	/**
	 * @return the backpackGUI
	 */
	public Inventory getBackpackGUI() {
		if(backpackGUI == null)
			backpackGUI = Bukkit.getServer().createInventory(null, BACKPACK_GUI_SIZE, ChatColor.GREEN + "Team Backpack");
		return backpackGUI;
	}

	/**
	 * @param backpackGUI the backpackGUI to set
	 */
	public void setBackpackGUI(Inventory backpackGUI) {
		this.backpackGUI = backpackGUI;
	}
}
