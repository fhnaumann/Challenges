package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class RandomizedCraftingChallenge extends RandomChallenge {

	public RandomizedCraftingChallenge() {
		super(ChallengeType.RANDOMIZE_CRAFTING);
		activeChallenges.put(ChallengeType.RANDOMIZE_CRAFTING, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.ORANGE_TERRACOTTA, 
				LanguageMessages.guiRandomCraftingName, 
				new ArrayList<String>(LanguageMessages.guiRandomCraftingLore), 
				super.active);
	}
}
