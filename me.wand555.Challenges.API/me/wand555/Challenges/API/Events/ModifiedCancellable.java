package me.wand555.Challenges.API.Events;

import org.bukkit.event.Cancellable;

public interface ModifiedCancellable extends Cancellable {

	/**
	 * Gets the message that will be send to the player if the event is cancelled. 
	 * If no message is specified, the event will still cancel and the player won't be notified.
	 * @return The message if the event is cancelled or null.
	 */
	public String getDeniedMessage();
	
	/**
	 * Sets the message that will be send to the player if the event is cancelled.
	 * If no message is specified, the event will still cancel and the player won't be notified.
	 * @param deniedMessage The message for the player.
	 */
	public void setDeniedMessage(String deniedMessage);
	
	/**
	 * Checks if a message has been set. Mostly for internal use.
	 * @return if a message has been set.
	 */
	public boolean hasDeniedMessage();
}
