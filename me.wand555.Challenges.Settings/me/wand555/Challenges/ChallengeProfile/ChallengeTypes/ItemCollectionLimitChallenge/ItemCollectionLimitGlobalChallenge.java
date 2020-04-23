package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.Config.LanguageMessages;

public class ItemCollectionLimitGlobalChallenge extends ItemCollectionLimitChallenge {

	private HashMap<Material, UUID> uniqueItems = new HashMap<>();
	
	public ItemCollectionLimitGlobalChallenge() {
		super(ChallengeType.ITEM_LIMIT_GLOBAL);
		activeChallenges.put(ChallengeType.ITEM_LIMIT_GLOBAL, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItemHealthWithAmount(Material.HOPPER, 
				LanguageMessages.guiItemCollectionLimitGlobalName, 
				new ArrayList<String>(LanguageMessages.guiItemCollectionLimitGlobalLore), 
				LanguageMessages.guiItemCollectionLimit, 
				super.limit, 
				super.active);
	}

	public LinkedHashMap<UUID, Integer> displayReadyStats() {
		ArrayList<UUID> uuids = new ArrayList<UUID>(getUniqueItems().values());
		return uuids.stream()
				.collect(Collectors.toMap(Function.identity(), uuid -> Collections.frequency(uuids, uuid), (k1, k2) -> k1))
				.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(
							Map.Entry::getKey, 
							Map.Entry::getValue, 
							(v1, v2) -> v1, 
							LinkedHashMap::new));
	}
	
	public String createItemCollectLogMessage(String causerName, Material collected) {
		return LanguageMessages.logItemPickUp
				.replace("[PLAYER]", causerName)
				.replace("[MATERIAL]", WordUtils.capitalize(collected.toString().toLowerCase().replace('_', ' ')))
				.replace("[AMOUNT]", Integer.toString(currentAmount))
				.replace("[LIMIT]", Integer.toString(limit));
	}
	
	/**
	 * Call this after a player collected an item to check
	 * @return
	 */
	public boolean isOverLimit() {
		return uniqueItems.size() > super.limit;
	}
	
	public boolean addToUniqueItems(Material mat, UUID uuid) {
		Object object = uniqueItems.put(mat, uuid);
		super.currentAmount = uniqueItems.size();
		return object == null;
	}
	
	/**
	 * @return the uniqueItems
	 */
	public HashMap<Material, UUID> getUniqueItems() {
		return uniqueItems;
	}

	/**
	 * @param uniqueItems the uniqueItems to set
	 */
	public void setUniqueItems(HashMap<Material, UUID> uniqueItems) {
		this.uniqueItems = uniqueItems;
	}
}
