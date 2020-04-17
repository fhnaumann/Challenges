package me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public class SharedHealthWaitDamageRunnable extends BukkitRunnable {

	public SharedHealthWaitDamageRunnable(Challenges plugin) {
		this.runTaskLater(plugin, 10L);
	}
	
	@Override
	public void run() {
		((SharedHealthChallenge) GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH)).setSharedHealthWaitDamageRunnableID(0);
	}
}
