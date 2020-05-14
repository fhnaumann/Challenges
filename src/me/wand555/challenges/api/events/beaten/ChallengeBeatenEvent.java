package me.wand555.challenges.api.events.beaten;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.google.common.collect.ImmutableList;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;

/**
 * Called when a player has beaten a challenge.
 * @author wand555
 *
 */
public class ChallengeBeatenEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private ChallengeEndReason reason;
	private List<ChallengeType> activeChallenges;
	private String endMessage;
	
	public ChallengeBeatenEvent(List<ChallengeType> activeChallenges, String message) {
		this.activeChallenges = ImmutableList.copyOf(activeChallenges);
		this.reason = ChallengeEndReason.FINISHED;
		this.endMessage = message;
	}
	
	/**
	 * @return the reason
	 */
	public ChallengeEndReason getReason() {
		return reason;
	}

	/**
	 * @return the activeChallenges
	 */
	public List<ChallengeType> getActiveChallenges() {
		return activeChallenges;
	}

	/**
	 * @return the endMessage
	 */
	public String getEndMessage() {
		return endMessage;
	}
	
	public void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

	public HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
