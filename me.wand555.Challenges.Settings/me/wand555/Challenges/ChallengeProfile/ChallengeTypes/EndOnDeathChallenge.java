package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class EndOnDeathChallenge extends GenericChallenge {

	public EndOnDeathChallenge() {
		super(ChallengeType.END_ON_DEATH);
		activeChallenges.put(type, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		System.out.println(LanguageMessages.guiDeathLore.size());
		return createItem(Material.PLAYER_HEAD, 
				LanguageMessages.guiDeathName, 
				new ArrayList<String>(LanguageMessages.guiDeathLore), 
				super.active);
	}
	
}
