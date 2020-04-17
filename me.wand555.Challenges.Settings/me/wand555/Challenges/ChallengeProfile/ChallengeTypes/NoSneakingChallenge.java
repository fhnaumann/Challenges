package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class NoSneakingChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

private PunishType punishType;
	
	public NoSneakingChallenge() {
		super(ChallengeType.NO_SNEAKING);
		activeChallenges.put(ChallengeType.NO_SNEAKING, this);
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
		return createPunishmentItem(Material.STONE_PRESSURE_PLATE, 
				LanguageMessages.guiNoSneakingName, 
				new ArrayList<String>(LanguageMessages.guiNoSneakingLore), 
				this.punishType, 
				super.active);
	}
}
