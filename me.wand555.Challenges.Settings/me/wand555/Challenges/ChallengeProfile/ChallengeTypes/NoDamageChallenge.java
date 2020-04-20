package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class NoDamageChallenge extends GenericChallenge implements Punishable, ReasonNotifiable {

	private PunishType punishType;
	
	public NoDamageChallenge() {
		super(ChallengeType.NO_DAMAGE);
		activeChallenges.put(ChallengeType.NO_DAMAGE, this);
	}
	
	@Override
	public ItemStack getDisplayItem() {
		return createPunishmentItem(Material.WITHER_ROSE, 
				LanguageMessages.guiNoDamageName, 
				new ArrayList<String>(LanguageMessages.guiNoDamageLore), 
				punishType,
				super.active);
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
		return super.type;
	}

}
