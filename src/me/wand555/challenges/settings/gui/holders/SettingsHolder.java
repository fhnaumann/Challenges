package me.wand555.challenges.settings.gui.holders;

import java.util.ArrayList;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.ItemDisplayCreator;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.settings.gui.InventoryManager;

public class SettingsHolder implements InventoryHolder, InitializableInventory, ItemDisplayCreator {

	private final Inventory inv;
	
	public SettingsHolder() {
		this.inv = Bukkit.createInventory(this, InventoryManager.SETTINGS_GUI_SIZE, "Settings");
		this.initializeInventory();
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	/**
	 * Changes the itemstack in the gui. It is assumed that all needed changes are already applied and 'stored' in the challenge
	 * This is only for the visual update in the gui.
	 * @param <T>
	 * @param index
	 * @param challenge
	 */
	public <T extends GenericChallenge> void changeSetting(int index, T challenge) {
		this.inv.setItem(index, challenge.getDisplayItem());
	}

	@Override
	public void initializeInventory() {
		Object[] slotToChallenge = InventoryManager.getInventoryManager().getSlotToChallenge();
		for(int i=0; i<inv.getSize(); i++) {
			if(i <= 19) inv.setItem(i, ((GenericChallenge)slotToChallenge[i]).getDisplayItem());
			else if(i == 26) {
				inv.setItem(i, createItem(Material.CHEST, 
						LanguageMessages.guiBackpackName, 
						new ArrayList<String>(LanguageMessages.guiBackpackLore), 
						ChallengeProfile.getInstance().getBackpack().isEnabled()));
			}
			else inv.setItem(i, createGlass());
		}
	}
}
