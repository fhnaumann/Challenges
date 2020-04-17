package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class RandomizedMobDropsChallenge extends RandomChallenge {

	public RandomizedMobDropsChallenge() {
		super(ChallengeType.RANDOMIZE_MOB_DROPS);
		activeChallenges.put(ChallengeType.RANDOMIZE_MOB_DROPS, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.WHITE_TERRACOTTA, 
				LanguageMessages.guiRandomMobDropsName, 
				new ArrayList<String>(LanguageMessages.guiRandomMobDropsLore), 
				super.active);
	}

}
