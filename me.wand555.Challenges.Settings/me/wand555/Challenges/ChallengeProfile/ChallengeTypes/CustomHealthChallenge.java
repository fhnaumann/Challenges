package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.Config.LanguageMessages;

public class CustomHealthChallenge extends GenericChallenge implements Amountable {

	private double customHP = 20;
	
	public CustomHealthChallenge() {
		super(ChallengeType.CUSTOM_HEALTH);
		activeChallenges.put(ChallengeType.CUSTOM_HEALTH, this);
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItemHealthWithAmount(Material.COMMAND_BLOCK, 
				LanguageMessages.guiCustomHealthName, 
				new ArrayList<String>(LanguageMessages.guiCustomHealthLore), 
				LanguageMessages.guiCustomHealthAmount,
				customHP, 
				super.active);
	}

	@Override
	public double getAmount() {
		return customHP;
	}

	@Override
	public void setAmount(double amount) {
		this.customHP = amount;
	}

}
