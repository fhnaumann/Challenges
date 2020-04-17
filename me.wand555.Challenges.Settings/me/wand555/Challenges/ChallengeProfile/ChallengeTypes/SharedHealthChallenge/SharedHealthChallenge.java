package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.Config.LanguageMessages;

public class SharedHealthChallenge extends GenericChallenge {

	private double sharedHealth;
	
	private int sharedHealthWaitDamageRunnableID;
	private int sharedHealthWaitRegRunnableID;
	
	public SharedHealthChallenge() {
		super(ChallengeType.SHARED_HEALTH);	
		setSharedHealth(getDefaultHealthToSet());
		activeChallenges.put(ChallengeType.SHARED_HEALTH, this);
	}
	
	public double getDefaultHealthToSet() {
		CustomHealthChallenge cHChallenge = super.getChallenge(ChallengeType.CUSTOM_HEALTH);
		return cHChallenge != null ? cHChallenge.getAmount() != 0 ? cHChallenge.getAmount() : 20 : 20;
	}

	@Override
	public ItemStack getDisplayItem() {
		return createItem(Material.GHAST_TEAR, 
				LanguageMessages.guiSharedHealthName, 
				new ArrayList<String>(LanguageMessages.guiSharedHealthLore), 
				super.active);
	}

	/**
	 * @return the sharedHealthWaitDamageRunnableID
	 */
	public int getSharedHealthWaitDamageRunnableID() {
		return sharedHealthWaitDamageRunnableID;
	}

	/**
	 * @param sharedHealthWaitDamageRunnableID the sharedHealthWaitDamageRunnableID to set
	 */
	public void setSharedHealthWaitDamageRunnableID(int sharedHealthWaitDamageRunnableID) {
		this.sharedHealthWaitDamageRunnableID = sharedHealthWaitDamageRunnableID;
	}

	/**
	 * @return the sharedHealthWaitRegRunnableID
	 */
	public int getSharedHealthWaitRegRunnableID() {
		return sharedHealthWaitRegRunnableID;
	}

	/**
	 * @param sharedHealthWaitRegRunnableID the sharedHealthWaitRegRunnableID to set
	 */
	public void setSharedHealthWaitRegRunnableID(int sharedHealthWaitRegRunnableID) {
		this.sharedHealthWaitRegRunnableID = sharedHealthWaitRegRunnableID;
	}

	/**
	 * @return the sharedHealth
	 */
	public double getSharedHealth() {
		return sharedHealth;
	}

	/**
	 * @param sharedHealth the sharedHealth to set
	 */
	public void setSharedHealth(double sharedHealth) {
		this.sharedHealth = sharedHealth;
	}
	
	
}
