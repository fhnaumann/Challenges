package me.wand555.GUI.Holders;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import InventoryManager.InitializableInventory;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemDisplayCreator;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.GUI.GUIType;
import me.wand555.GUI.InventoryManager;

public class PunishmentHolder implements InventoryHolder, InitializableInventory, ItemDisplayCreator {

private final Inventory inv;
	
	public PunishmentHolder() {
		this.inv = Bukkit.createInventory(this, InventoryManager.PUNISHMENT_GUI_SIZE, "Punishments");
		initializeInventory();
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}

	@Override
	public void initializeInventory() {
		for(int i=0; i<inv.getSize(); i++) {
			switch(i) {
			case 0:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishNothing));
				break;
			case 2:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishHealth.replace("[AMOUNT]", "1-10")));
				break;
			case 4:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishHealthAll.replace("[AMOUNT]", "1-10")));
				break;
			case 6:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishDeath));
				break;
			case 8:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishDeathAll));
				break;
			case 18:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishOneRandomItem));
				break;
			case 20:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishOneRandomItemAll));
				break;
			case 22:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishAllItems));
				break;
			case 24:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishAllItemsAll));
				break;
			case 26:
				inv.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
						LanguageMessages.punishChallengeOver));
				break;
			case 31:
				inv.setItem(i, createGoBack(GUIType.PUNISHMENT));
				break;
			default:
				inv.setItem(i, createGlass());
			}
		}
	}
	
	
}
