package me.wand555.challenges.settings.challengeprofile.types.sharedhealth;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.start.Challenges;

public class SharedHealthWaitRegRunnable extends BukkitRunnable {

	public SharedHealthWaitRegRunnable(Challenges plugin) {
		this.runTaskLater(plugin, 10L);
	}
	
	@Override
	public void run() {
		((SharedHealthChallenge) GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH)).setSharedHealthWaitRegRunnableID(0);
	}
}
