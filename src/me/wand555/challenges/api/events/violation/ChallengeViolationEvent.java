package me.wand555.challenges.api.events.violation;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.wand555.challenges.api.events.ModifiedCancellable;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;

public abstract class ChallengeViolationEvent<T extends GenericChallenge> extends Event implements ModifiedCancellable {

	private static final HandlerList handlers = new HandlerList();	

	private T challenge;
	private ChallengeType challengeType;
	private Player[] players;
	//private boolean isPunishable;
	private boolean cancelled;
	private String deniedMessage;
	
	public ChallengeViolationEvent(T challenge, Player... players) {
		this.challenge = challenge;
		this.challengeType = challenge.getChallengeType();
		this.players = players;
	}
	
	/**
	 * @return the getChallengeType
	 */
	public ChallengeType getChallengeType() {
		return challengeType;
	}

	/**
	 * @return the player
	 */
	public Player[] getPlayers() {
		return players;
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
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
