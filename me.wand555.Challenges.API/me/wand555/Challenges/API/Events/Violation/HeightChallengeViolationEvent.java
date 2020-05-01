package me.wand555.Challenges.API.Events.Violation;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge.HeightChallenge;

public class HeightChallengeViolationEvent extends ChallengeViolationPunishmentEvent<HeightChallenge> {

	private int toBeOnHeightNormal;
	private int toBeOnHeightNether;

	public HeightChallengeViolationEvent(HeightChallenge challenge, PunishType punishType, String logMessage,
			int toBeOnHeightNormal, int toBeOnHeightNether, Player[] players) {
		super(challenge, punishType, logMessage, players);
		this.toBeOnHeightNormal = toBeOnHeightNormal;
		this.toBeOnHeightNether = toBeOnHeightNether;
	}
	
	/**
	 * @return the toBeOnHeightNormal
	 */
	public int getToBeOnHeightNormal() {
		return toBeOnHeightNormal;
	}

	/**
	 * @return the toBeOnHeightNether
	 */
	public int getToBeOnHeightNether() {
		return toBeOnHeightNether;
	}
}
