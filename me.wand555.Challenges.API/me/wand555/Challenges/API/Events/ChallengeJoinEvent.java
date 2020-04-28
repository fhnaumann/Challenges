package me.wand555.Challenges.API.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;

public class ChallengeJoinEvent extends ChallengePlayerWarpEvent {

	private boolean isRunning;
	
	public ChallengeJoinEvent(Player player, Location to) {
		super(player, to);
		isRunning = ChallengeProfile.getInstance().hasStarted;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}



}
