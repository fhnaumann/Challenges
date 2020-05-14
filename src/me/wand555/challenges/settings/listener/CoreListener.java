package me.wand555.challenges.settings.listener;

import org.bukkit.event.Listener;

import me.wand555.challenges.api.events.violation.CallViolationEvent;
import me.wand555.challenges.start.Challenges;

public abstract class CoreListener implements Listener, CallViolationEvent {

	protected Challenges plugin;
	
	public CoreListener(Challenges plugin) {
		this.plugin = plugin;
	}
}
