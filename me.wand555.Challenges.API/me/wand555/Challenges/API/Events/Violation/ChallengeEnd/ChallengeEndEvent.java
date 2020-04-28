package me.wand555.Challenges.API.Events.Violation.ChallengeEnd;

import org.bukkit.entity.Player;

import me.wand555.Challenges.API.Events.Violation.ChallengeViolationEvent;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public class ChallengeEndEvent<T extends GenericChallenge> extends ChallengeViolationEvent<T> {

	private ChallengeEndReason endReason;
	private String endMessage;
	private boolean restorable;
	
	public ChallengeEndEvent(T challenge, ChallengeEndReason endReason, String message, Player... players) {
		super(challenge, players);
		this.endReason = endReason;
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
