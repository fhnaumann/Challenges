package me.wand555.GUI.Holders;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemDisplayCreator;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.GUI.InventoryManager;

public class BackpackHolder implements InventoryHolder, ItemDisplayCreator {

	private final Inventory inv;
	
	public BackpackHolder() {
		this.inv = Bukkit.createInventory(this, InventoryManager.BACKPACK_GUI_SIZE, "Team Backpack");
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}

	public ItemStack createBackpackItem() {
		return createItem(Material.CHEST, 
				LanguageMessages.guiBackpackName, 
				new ArrayList<String>(LanguageMessages.guiBackpackLore), 
				ChallengeProfile.getInstance().getBackpack().isEnabled());
	}
	
}
