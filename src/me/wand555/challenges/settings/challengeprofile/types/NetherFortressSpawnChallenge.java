package me.wand555.challenges.settings.challengeprofile.types;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.challenges.settings.config.LanguageMessages;

public class NetherFortressSpawnChallenge extends GenericChallenge {

	public NetherFortressSpawnChallenge() {
		super(ChallengeType.NETHER_FORTRESS_SPAWN);
		activeChallenges.put(ChallengeType.NETHER_FORTRESS_SPAWN, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.BLAZE_ROD, 
				LanguageMessages.guiFortressSpawnName, 
				new ArrayList<String>(LanguageMessages.guiFortressSpawnLore), 
				super.active);
	}
}
