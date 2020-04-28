package me.wand555.Challenges.API.Events.Violation;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoDamageChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;

public class NoDamageChallengeViolationEvent extends ChallengeViolationPunishmentEvent<NoDamageChallenge> {

	private DamageCause cause;
	private double amount;
	
	public NoDamageChallengeViolationEvent(NoDamageChallenge challenge, PunishType punishType, String logMessage, 
			DamageCause cause, double amount, Player player) {
		super(challenge, punishType, logMessage, player);
		this.cause = cause;
		this.amount = amount;
	}

	/**
	 * @return the cause
	 */
	public DamageCause getCause() {
		return cause;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

}
