package me.wand555.challenges.settings.challengeprofile.types;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import com.google.common.collect.Lists;

import me.wand555.challenges.settings.config.LanguageMessages;

public class NoRegenerationChallenge extends GenericChallenge {

	public NoRegenerationChallenge() {
		super(ChallengeType.NO_REG);
		activeChallenges.put(ChallengeType.NO_REG, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createPotionItem(Material.POTION, 
				PotionType.INSTANT_HEAL, 
				LanguageMessages.guiNoRegName, 
				new ArrayList<String>(LanguageMessages.guiNoRegLore), 
				super.active);
	}

}
