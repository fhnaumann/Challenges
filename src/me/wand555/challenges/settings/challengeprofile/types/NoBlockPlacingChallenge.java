package me.wand555.challenges.settings.challengeprofile.types;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.challenges.settings.config.LanguageMessages;

public class NoBlockPlacingChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

	private PunishType punishType;
	
	public NoBlockPlacingChallenge() {
		super(ChallengeType.NO_BLOCK_PLACING);
		activeChallenges.put(ChallengeType.NO_BLOCK_PLACING, this);
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
		return createPunishmentItem(Material.GRASS_BLOCK, 
				LanguageMessages.guiNoPlacingName, 
				new ArrayList<String>(LanguageMessages.guiNoPlacingLore), 
				this.punishType, 
				super.active);
	}
	
	
}
