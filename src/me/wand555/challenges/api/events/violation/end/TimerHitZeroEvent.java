package me.wand555.challenges.api.events.violation.end;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimerHitZeroEvent extends Event {

	public static final HandlerList handlers = new HandlerList();
	
	private String endMessage;
	
	public TimerHitZeroEvent(String message) {
		this.setEndMessage(message);
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

	public HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
