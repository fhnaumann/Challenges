package me.wand555.challenges.api.events.violation.end;

import org.bukkit.entity.Player;

import me.wand555.challenges.api.events.violation.ChallengeViolationEvent;
import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;

public class ChallengeEndEvent<T extends GenericChallenge> extends ChallengeViolationEvent<T> {

	private ChallengeEndReason endReason;
	private String endMessage;
	private boolean restorable;
	
	public ChallengeEndEvent(T challenge, ChallengeEndReason endReason, String message, Player... players) {
		super(challenge, players);
		this.endReason = endReason;
		this.endMessage = message;
		this.restorable = endReason.isRestorable();
	}

	/**
	 * @return the endReason
	 */
	public ChallengeEndReason getEndReason() {
		return endReason;
	}

	/**
	 * @return the endMessage
	 */
	public String getEndMessage() {
		return endMessage;
	}

	/**
	 * @param endMessage the endMessage to set
	 */
	public void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

	/**
	 * @return the restorable
	 */
	public boolean isRestorable() {
		return restorable;
	}

}
