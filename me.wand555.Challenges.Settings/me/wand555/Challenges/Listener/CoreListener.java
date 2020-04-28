package me.wand555.Challenges.Listener;

import org.bukkit.event.Listener;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.API.Events.Violation.CallViolationEvent;

public abstract class CoreListener implements Listener, CallViolationEvent {

	protected Challenges plugin;
	
	public CoreListener(Challenges plugin) {
		this.plugin = plugin;
	}
}
