package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import com.google.common.collect.Lists;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.Config.LanguageMessages;

public class NoRegenerationHardChallenge extends GenericChallenge {
	
	public NoRegenerationHardChallenge() {
		super(ChallengeType.NO_REG_HARD);
		activeChallenges.put(ChallengeType.NO_REG_HARD, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createPotionItem(Material.POTION, 
				PotionType.REGEN, 
				LanguageMessages.guiNoRegHardName, 
				new ArrayList<String>(LanguageMessages.guiNoRegHardLore), 
				super.active);
	}

}

