package me.wand555.challenges.settings.challengeprofile.types;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.challenges.settings.config.LanguageMessages;

public class NoBlockBreakingChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {
	
	private PunishType punishType;
	
	public NoBlockBreakingChallenge() {
		super(ChallengeType.NO_BLOCK_BREAKING);
		activeChallenges.put(ChallengeType.NO_BLOCK_BREAKING, this);
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
		return createPunishmentItem(Material.STONE, 
				LanguageMessages.guiNoBreakingName, 
				new ArrayList<String>(LanguageMessages.guiNoBreakingLore), 
				this.punishType, 
				super.active);
	}
}
