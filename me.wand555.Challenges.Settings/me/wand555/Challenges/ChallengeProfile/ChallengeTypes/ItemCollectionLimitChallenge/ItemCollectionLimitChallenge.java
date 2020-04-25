package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Amountable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

/**
 * An abstract class holding the base information regarding some sort of limitations in player / team inventories.
 * This only means unique items. 3 Stacks of STONE counts as 1 "item" in this challenge.
 * @author Felix
 *
 */
public abstract class ItemCollectionLimitChallenge extends GenericChallenge implements Amountable {

	/**
	 * How many items are collected at this moment (shared across all players)
	 */
	protected int currentAmount;
	/**
	 * The limit set at the start of the challenge.
	 */
	protected int limit;
	
	protected ItemCollectionLimitChallenge(ChallengeType type) {
		super(type);
	}
	
	/**
	 * Gets the limit!
	 */
	@Override
	public double getAmount() {
		return limit;
	}

	/**
	 * Sets the limit!
	 */
	@Override
	public void setAmount(double limit) {
		this.limit = (int) limit;
		
	}

	public int getCurrentAmount() {
		return this.currentAmount;
	}
	
	public int getLimit() {
		return this.limit;
	}

	/**
	 * @param currentAmount the currentAmount to set
	 */
	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
