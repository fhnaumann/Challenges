package me.wand555.Challenges.ChallengeProfile.Events.SettingsChange;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.Events.Overridable;

/**
 * Called when a player activates the custom Health Challenge.
 * @author wand555
 *
 */
public class CustomHealthChallengeStatusSwitchEvent extends ChallengeStatusSwitchEvent<CustomHealthChallenge> implements Overridable {

	private int customHP;
	private String overrideMessage;
	
	/**
	 * 
	 * @param challenge The challenge that triggered the call.
	 * @param customHP The custom health the player entered.
	 * @param player The player who changed the setting.
	 */
	public CustomHealthChallengeStatusSwitchEvent(CustomHealthChallenge challenge, int customHP, Player player) {
		super(challenge, player);
		this.customHP = customHP;
	}
	
	/**
	 * Gets the custom Health the player set.
	 * @return
	 */
	public int getCustomHP() {
		return customHP;
	}
	
	/**
	 * Sets the custom Health for this challenge. Will override user input
	 * @param customHP
	 * @see Overridable#setOverrideMessage(String)
	 */
	public void setCustomHP(int customHP) {
		this.customHP = customHP;
	}

	@Override
	public String getOverrideMessage() {
		return overrideMessage;
	}

	@Override
	public void setOverrideMessage(String overrideMessage) {
		this.overrideMessage = overrideMessage;
		
	}

	@Override
	public boolean hasOverrideMessage() {
		return overrideMessage != null && !overrideMessage.isEmpty();
	}

}
