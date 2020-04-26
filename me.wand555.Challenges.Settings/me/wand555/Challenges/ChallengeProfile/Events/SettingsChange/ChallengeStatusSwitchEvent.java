package me.wand555.Challenges.ChallengeProfile.Events.SettingsChange;

import org.bukkit.entity.Player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;

/**
 * Called when a player switches a challenge setting.
 * You don't need to fill in T when listening to this event.
 * Some Challenges have their own event. 
 * <br>See below:
 * <br>{@link CustomHealthChallengeStatusSwitchEvent} if status == true otherwise this event.
 * 
 * @author wand555
 *
 * @param <T> The challenge this was called with (T extends GenericChallenge).
 */
public class ChallengeStatusSwitchEvent<T> extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private T challenge;
	private GenericChallenge genericCasted;
	private boolean newStatus;
	private Player player;
	private String deniedMessage;
	private boolean cancelled;
	
	/**
	 * 
	 * @param challenge The challenge that triggered the call.
	 * @param player The player who changed the setting.
	 */
	public ChallengeStatusSwitchEvent(T challenge, Player player) {
		this.challenge = challenge;
		genericCasted = (GenericChallenge) this.challenge;
		this.newStatus = !genericCasted.isActive();
		this.player = player;
	}
	
	/**
	 * The new state that will be applied unless {@link #isCancelled()} is set to true.
	 * @return the to-be-applied status.
	 */
	public boolean getNewStatus() {
		return newStatus;
	}
	
	/**
	 * 
	 * @return The player who changed the setting.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * 
	 * @return The challenge type that triggered the call.
	 */
	public ChallengeType getChallengeType() {
		return genericCasted.getChallengeType();
	}
	
	/**
	 * @see PunishableChallengeStatusSwitchEvent
	 * @return Whether the challenge is punishable or not.
	 */
	public boolean isPunishable() {
		return challenge instanceof Punishable;
	}
	
	/**
	 * Gets the message that will be send to the player if the event is cancelled. 
	 * If no message is specified, the event will still cancel and the player won't be notified.
	 * @return The message if the event is cancelled or null.
	 */
	public String getDeniedMessage() {
		return this.deniedMessage;
	}
	
	/**
	 * Sets the message that will be send to the player if the event is cancelled.
	 * If no message is specified, the event will still cancel and the player won't be notified.
	 * @param deniedMessage The message for the player.
	 */
	public void setDeniedMessage(String deniedMessage) {
		this.deniedMessage = deniedMessage;
	}
	
	/**
	 * Checks if a message has been set. Mostly for internal use.
	 * @return if a message has been set.
	 */
	public boolean hasDeniedMessage() {
		return this.deniedMessage != null && !this.deniedMessage.isEmpty();
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
