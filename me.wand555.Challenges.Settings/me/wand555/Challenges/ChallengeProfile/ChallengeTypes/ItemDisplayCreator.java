package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.wand555.Challenges.Config.LanguageMessages;
import me.wand555.GUI.GUIType;

public interface ItemDisplayCreator {

	default ItemStack createItemWithoutBoolean(Material mat, String name) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createItem(Material mat, String name, ArrayList<String> lore, boolean enabled) {
		//System.out.println(lore.size());
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		lore.add(LanguageMessages.status.replace("[STATUS]", enabled ? LanguageMessages.enabled : LanguageMessages.disabled));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createItemHealthWithAmount(Material mat, String name, ArrayList<String> lore, String translation, double amount, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			lore.add(translation.replace("[AMOUNT]", Integer.toString((int)amount)));
			//lore.add(ChatColor.GRAY + "Max HP: " + ChatColor.GREEN + ChallengeProfile.getInstance().customHP);
		}
		lore.add(LanguageMessages.status.replace("[STATUS]", enabled ? LanguageMessages.enabled : LanguageMessages.disabled));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createPotionItem(Material mat, PotionType type, String name, ArrayList<String> lore, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(type));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		lore.add(LanguageMessages.status.replace("[STATUS]", enabled ? LanguageMessages.enabled : LanguageMessages.disabled));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createPunishmentItem(Material mat, String name, ArrayList<String> lore, PunishType type, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);	
			if(type != null) {
				switch(type) {
				case NOTHING:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishNothing));
					break;
				case HEALTH_1:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "1")));
					break;
				case HEALTH_2:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "2")));			
					break;
				case HEALTH_3:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "3")));
					break;
				case HEALTH_4:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "4")));
					break;
				case HEALTH_5:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "5")));
					break;
				case HEALTH_6:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "6")));
					break;
				case HEALTH_7:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "7")));			
					break;
				case HEALTH_8:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "8")));
					break;
				case HEALTH_9:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "9")));
					break;
				case HEALTH_10:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealth.replace("[AMOUNT]", "10")));
					break;
				case HEALTH_ALL_1:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "1")));
					break;
				case HEALTH_ALL_2:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "2")));		
					break;
				case HEALTH_ALL_3:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "3")));
					break;
				case HEALTH_ALL_4:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "4")));
					break;
				case HEALTH_ALL_5:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "5")));
					break;
				case HEALTH_ALL_6:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "6")));
					break;
				case HEALTH_ALL_7:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "7")));			
					break;
				case HEALTH_ALL_8:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "8")));
					break;
				case HEALTH_ALL_9:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "9")));
					break;
				case HEALTH_ALL_10:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishHealthAll.replace("[AMOUNT]", "10")));
					break;
				case DEATH:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishDeath));
					break;
				case DEATH_ALL:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishDeathAll));
					break;
				case ONE_ITEM:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishOneRandomItem));
					break;
				case ONE_ITEM_ALL:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishOneRandomItemAll));
					break;
				case ALL_ITEMS:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishAllItems));
					break;
				case ALL_ITEMS_ALL:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishAllItemsAll));
					break;
				case CHALLENGE_OVER:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", LanguageMessages.punishChallengeOver));
					break;
				default:
					lore.add(LanguageMessages.punishItemDisplay.replace("[PUNISHMENT]", "&7-"));
					break;
				
				}
			}
		}
		lore.add(LanguageMessages.status.replace("[STATUS]", enabled ? LanguageMessages.enabled : LanguageMessages.disabled));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createGlass() {
		ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "TBA");
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createGoBack(GUIType currentlyIn) {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		switch(currentlyIn) {
		case PUNISHMENT: meta.setDisplayName(LanguageMessages.guiBackTo.replace("[TO]", "Overview"));
		case COLLECTED_ITEMS_LIST: meta.setDisplayName(LanguageMessages.guiBackTo.replace("[TO]", "Overview"));
		default: break;
		}
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack createPageItem(boolean right) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		if(right) meta.setDisplayName(LanguageMessages.guiPageNext);
		else meta.setDisplayName(LanguageMessages.guiPagePrevious);	
		item.setItemMeta(meta);
		return item;
	}
}
