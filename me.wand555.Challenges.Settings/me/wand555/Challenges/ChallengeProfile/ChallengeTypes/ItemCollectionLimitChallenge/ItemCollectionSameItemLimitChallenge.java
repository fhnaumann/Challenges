package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ReasonNotifiable;

/**
 * A unique item cannot be in multiple inventories at the same time
 * @author Felix
 *
 */
public class ItemCollectionSameItemLimitChallenge extends ItemCollectionLimitChallenge implements Punishable, ReasonNotifiable {

	private HashMap<Material, UUID> totalInventoryItems = new HashMap<>();
	/**
	 * Has no user other than to know which item caused the challenge to end/lead to a punishment
	 */
	private Material latestAdded;
	
	private PunishType punishType;
	
	public ItemCollectionSameItemLimitChallenge() {
		super(ChallengeType.NO_SAME_ITEM);
		activeChallenges.put(ChallengeType.NO_SAME_ITEM, this);
	}
	
	@Override
	public int getLimit() {
		//do nothing
		return 0;
	}
	
	@Override
	public void setLimit(int limit) {
		//do nothing
	}
	
	
	public boolean canBeObtained(Material material, UUID uuid) {
		return totalInventoryItems.entrySet().stream().filter(entry -> !entry.getValue().equals(uuid)).noneMatch(entry -> entry.getKey() == material);
	}
	
	public boolean shouldBeRemoved(Material material) {
		return totalInventoryItems.keySet().stream().anyMatch(mat -> mat == material);
	}
	
	public boolean isOnlyOneWithMaterial(Material material, UUID uuid) {
		return totalInventoryItems.entrySet().stream()
				.filter(entry -> entry.getKey() == material)
				.filter(entry -> entry.getValue().equals(uuid))
				.count() == 1;
	}
	
	public boolean addToTotalInventoryItems(Material mat, UUID uuid) {
		Object obj = totalInventoryItems.putIfAbsent(mat, uuid);
		latestAdded = mat;
		super.currentAmount = totalInventoryItems.size();
		return obj == null;
	}
	
	public void removeFromTotalInventoryitems(Material mat, UUID uuid) {
		totalInventoryItems.remove(mat, uuid);
		super.currentAmount = totalInventoryItems.size();
	}

	@Override
	public PunishType getPunishType() {
		return punishType;
	}

	@Override
	public void setPunishType(PunishType punishType) {
		this.punishType = punishType;
	}

	@Override
	public ChallengeType getPunishCause() {
		return type;
	}

	@Override
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.HOPPER_MINECART, 
				"sdg", 
				Lists.newArrayList("df"), 
				punishType, 
				super.active);
	}

	/**
	 * @return the totalInventoryItems
	 */
	public HashMap<Material, UUID> getTotalInventoryItems() {
		return totalInventoryItems;
	}

	/**
	 * @param totalInventoryItems the totalInventoryItems to set
	 */
	public void setTotalInventoryItems(HashMap<Material, UUID> totalInventoryItems) {
		this.totalInventoryItems = totalInventoryItems;
	}

	/**
	 * @return the latestAdded
	 */
	public Material getLatestAdded() {
		return latestAdded;
	}

	/**
	 * @param latestAdded the latestAdded to set
	 */
	public void setLatestAdded(Material latestAdded) {
		this.latestAdded = latestAdded;
	}

}
