package me.wand555.Challenges.API.Events;

import javax.annotation.Nullable;

public interface Overridable {

	/**
	 * This message will be send to the player if you override a setting.
	 * If you don't set a override message, it will still apply the changes you set to the setting, but not inform the player.
	 * @return the override message or null
	 * @see Overridable#hasOverrideMessage()
	 * @see Overridable#setOverrideMessage(String)
	 */
	@Nullable
	public String getOverrideMessage();
	
	/**
	 * Sets the message that will be send to the player if you overide a setting.
	 * If you don't set a override message, it will still apply the changes you set to the setting, but not inform the player.
	 * @param overrideMessage The message the user sees.
	 * @see Overridable#hasOverrideMessage()
	 * @see Overridable#getOverrideMessage()
	 */
	public void setOverrideMessage(String overrideMessage);
	
	/**
	 * Checks if a override message is set. Mostly for internal use.
	 * @return if override message is set.
	 */
	public boolean hasOverrideMessage();
}
