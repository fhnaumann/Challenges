package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class RandomizedBlockDropsChallenge extends RandomChallenge {

	public RandomizedBlockDropsChallenge() {
		super(ChallengeType.RANDOMIZE_BLOCK_DROPS);
		activeChallenges.put(ChallengeType.RANDOMIZE_BLOCK_DROPS, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.BLUE_TERRACOTTA, 
				LanguageMessages.guiRandomBlockDropsName, 
				new ArrayList<String>(LanguageMessages.guiRandomBlockDropsLore), 
				super.active);
	}

}
