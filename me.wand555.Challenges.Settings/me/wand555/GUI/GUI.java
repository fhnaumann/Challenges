package me.wand555.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.Backpack;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemDisplayCreator;
import me.wand555.Challenges.Config.LanguageMessages;

public class GUI implements ItemDisplayCreator {

	public HashMap<UUID, ChallengeType> punishmentChallengeTypeOpenGUI = new HashMap<UUID, ChallengeType>();
	
	private Challenges plugin;
	
	public GUI(Challenges plugin) {
		this.plugin = plugin;
	}
	
	public void createGUI(Player p, GUIType type, ChallengeType...challengeType) {
		Inventory gui = null;
		if(type == GUIType.OVERVIEW) {
			gui = plugin.getServer().createInventory(null, 27, ChatColor.GREEN + "Settings");
			for(int i=0; i<gui.getSize(); i++) {
				switch(i) {
				case 0:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.END_ON_DEATH).getDisplayItem());
					break;
				case 1:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NETHER_FORTRESS_SPAWN).getDisplayItem());
					break;
				case 2:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE).getDisplayItem());
					break;
				case 3:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_REG).getDisplayItem());
					break;			
				case 4:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_REG_HARD).getDisplayItem());
					break;	
				case 5:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.CUSTOM_HEALTH).getDisplayItem());
					break;
				case 6:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH).getDisplayItem());
					break;
				case 7:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING).getDisplayItem());
					break;
				case 8:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING).getDisplayItem());
					break;
				case 9:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING).getDisplayItem());
					break;
				case 10:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING).getDisplayItem());
					break;
				case 11:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS).getDisplayItem());
					break;
				case 12:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_MOB_DROPS).getDisplayItem());
					break;
				case 13:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING).getDisplayItem());
					break;
				case 14:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.MLG).getDisplayItem());
					break;
				case 15:
					gui.setItem(i, GenericChallenge.getChallenge(ChallengeType.ON_BLOCK).getDisplayItem());
					break;
				
				case 26:
					gui.setItem(i, createItem(Material.CHEST, 
							LanguageMessages.guiBackpackName, 
							new ArrayList<String>(LanguageMessages.guiBackpackLore), 
							ChallengeProfile.getInstance().getBackpack().isEnabled()));
					break;
				default:	
					gui.setItem(i, createGlass());
				}
			}
		}
		else if(type == GUIType.PUNISHMENT) {
			//make a small method the get a more convient challenge type name
			gui = plugin.getServer().createInventory(null, 36, ChatColor.RED + "Punishments");
			for(int i=0; i<gui.getSize(); i++) {
				switch(i) {
				case 0:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishNothing));
					break;
				case 2:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishHealth.replace("[AMOUNT]", "1-10")));
					break;
				case 4:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishHealthAll.replace("[AMOUNT]", "1-10")));
					break;
				case 6:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishDeath));
					break;
				case 8:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishDeathAll));
					break;
				case 18:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishOneRandomItem));
					break;
				case 20:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishOneRandomItemAll));
					break;
				case 22:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishAllItems));
					break;
				case 24:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishAllItemsAll));
					break;
				case 26:
					gui.setItem(i, createItemWithoutBoolean(Material.WRITABLE_BOOK, 
							LanguageMessages.punishChallengeOver));
					break;
				case 31:
					gui.setItem(i, createGoBack(type));
					break;
				default:
					gui.setItem(i, createGlass());
				}
			}
			punishmentChallengeTypeOpenGUI.put(p.getUniqueId(), challengeType[0]);
		}
		else if(type == GUIType.BACKPACK) {
			gui = plugin.getServer().createInventory(null, Backpack.BACKPACK_SIZE, ChatColor.GREEN + "Backpack");
			
		}
		p.openInventory(gui);
	}
}
