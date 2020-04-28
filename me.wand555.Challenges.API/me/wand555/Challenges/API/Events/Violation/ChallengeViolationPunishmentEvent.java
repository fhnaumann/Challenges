package me.wand555.Challenges.API.Events.Violation;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;

public class ChallengeViolationPunishmentEvent<T extends GenericChallenge & Punishable> extends ChallengeViolationEvent<T> {

	private PunishType punishType;
	private String logMessage;
	
	public ChallengeViolationPunishmentEvent(T challenge, PunishType punishType, String logMessage, Player... players) {
		super(challenge, players);
		this.punishType = punishType;
		this.logMessage = logMessage;
	}

	/**
	 * @return the punishType
	 */
	public PunishType getPunishType() {
		return punishType;
	}

	/**
	 * @return the logMessage
	 */
	public String getLogMessage() {
		return logMessage;
	}

	/**
	 * @param logMessage the logMessage to set
	 */
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

}
