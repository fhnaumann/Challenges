package me.wand555.challenges.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;

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
