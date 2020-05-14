package me.wand555.challenges.settings.challengeprofile.types.itemcollectionlimit;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.Punishable;
import me.wand555.challenges.settings.challengeprofile.types.ReasonNotifiable;
import me.wand555.challenges.settings.config.LanguageMessages;

/**
 * A unique item cannot be in multiple inventories at the same time
 * @author Felix
 *
 */
public class ItemCollectionSameItemLimitChallenge extends ItemCollectionLimitChallenge implements Punishable, ReasonNotifiable {
	
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
		return ChallengeProfile.getInstance().getParticipants().stream()
				.filter(p -> !p.getUniqueId().equals(uuid))
				.noneMatch(p -> p.getInventory().contains(material));
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
				LanguageMessages.guiItemCollectionSameItemName, 
				new ArrayList<String>(LanguageMessages.guiItemCollectionSameItemLore), 
				punishType, 
				super.active);
	}

}
