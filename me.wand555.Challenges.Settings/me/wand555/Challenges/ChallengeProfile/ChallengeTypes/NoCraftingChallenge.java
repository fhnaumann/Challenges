package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class NoCraftingChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

private PunishType punishType;
	
	public NoCraftingChallenge() {
		super(ChallengeType.NO_CRAFTING);
		activeChallenges.put(ChallengeType.NO_CRAFTING, this);
	}

	@Override
	public ChallengeType getPunishCause() {
		return super.type;
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
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.CRAFTING_TABLE, 
				LanguageMessages.guiNoCraftingName, 
				new ArrayList<String>(LanguageMessages.guiNoCraftingLore), 
				this.punishType, 
				super.active);
	}
}
