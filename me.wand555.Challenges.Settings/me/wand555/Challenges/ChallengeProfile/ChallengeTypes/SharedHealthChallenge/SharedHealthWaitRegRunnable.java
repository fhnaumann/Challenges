package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public class SharedHealthWaitRegRunnable extends BukkitRunnable {

	public SharedHealthWaitRegRunnable(Challenges plugin) {
		this.runTaskLater(plugin, 10L);
	}
	
	@Override
	public void run() {
		((SharedHealthChallenge) GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH)).setSharedHealthWaitRegRunnableID(0);
	}
}
