package me.wand555.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.GUI.Holders.SettingsHolder;

public class GUI implements ItemDisplayCreator {

	public HashMap<UUID, Integer> punishmentChallengeTypeOpenGUI = new HashMap<UUID, Integer>();
	
	private Challenges plugin;
	
	public GUI(Challenges plugin) {
		this.plugin = plugin;
	}
	
	public <T extends GenericChallenge> void createGUI(Player p, GUIType type, int indexClicked, T challenge) {
		InventoryManager invManager = InventoryManager.getInventoryManager();
		Inventory gui = null;
		if(type == GUIType.OVERVIEW) {
			if(indexClicked >= InventoryManager.SETTINGS_GUI_SIZE) return;	
			if(challenge != null) System.out.println(challenge.getChallengeType());
			if(indexClicked > -1) {
				System.out.println("index clicked before updating: " + indexClicked);
				invManager.changeSetting(indexClicked, challenge);
			}
			gui = invManager.getSettingsGUI();
		}
		else if(type == GUIType.PUNISHMENT) {
			//indexClicked is still the challenge index
			System.out.println("added index: " + indexClicked);
			punishmentChallengeTypeOpenGUI.put(p.getUniqueId(), indexClicked);
			System.out.println("(after) index in map: " + punishmentChallengeTypeOpenGUI.get(p.getUniqueId()));
			gui = invManager.getPunishmentGUI();
		}
		else if(type == GUIType.COLLECTED_ITEMS_LIST) {
			gui = Bukkit.createInventory(null, InventoryManager.ALREADY_COLLECTED_GUI_SIZE, ChatColor.DARK_GREEN + "Collected Items");
			ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
			int endIndex = iCLGChallenge.placeItemsAlreadyCollected(gui, p.getUniqueId());
			for(int i=endIndex<0?0:endIndex; i<gui.getSize(); i++) {
				if(i == 45) {
					gui.setItem(i, createPageItem(false));
				}
				else if(i == 47) {
					gui.setItem(i, iCLGChallenge.getDisplayItem());
				}
				else if(i == 49) {
					gui.setItem(i, createGoBack(type));
				}
				else if(i == 53) {
					gui.setItem(i, createPageItem(true));
				}
				else gui.setItem(i, createGlass());
			}
		}
		p.openInventory(gui);
	}
}
