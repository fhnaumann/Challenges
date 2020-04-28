package me.wand555.Challenges.API.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChallengePlayerWarpEvent extends Event implements ModifiedCancellable, Overridable {
	
	public static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Location to;
	private String overrideMessage;
	private boolean cancelled;
	private String deniedMessage;
	
	public ChallengePlayerWarpEvent(Player player, Location to) {
		this.player = player;
		this.to = to;
	}
	
	/**
	 * @return the to
	 */
	public Location getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Location to) {
		this.to = to;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	public HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
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
	public String getDeniedMessage() {
		return deniedMessage;
	}

	@Override
	public void setDeniedMessage(String deniedMessage) {
		this.deniedMessage = deniedMessage;
	}

	@Override
	public boolean hasDeniedMessage() {
		return deniedMessage != null && !deniedMessage.isEmpty();
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
