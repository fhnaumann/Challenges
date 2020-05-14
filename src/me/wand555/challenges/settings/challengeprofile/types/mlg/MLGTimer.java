package me.wand555.challenges.settings.challengeprofile.types.mlg;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.start.Challenges;

public class MLGTimer extends BukkitRunnable {

	/**
	 * The total time, not being subtracted from. Stays the same for a whole "MLG Iteration"
	 */
	private long totalTimeToMLG;
	/**
	 * Changes every second (-20) until its down to or below 0. 
	 */
	private long timeToMLG;
	
	public MLGTimer(Challenges plugin, MLGChallenge mlgChallenge) {
		totalTimeToMLG = ThreadLocalRandom.current()
				.nextLong(mlgChallenge.getEarliest()*20, (mlgChallenge.getLatest()+1)*20);
		timeToMLG = totalTimeToMLG;
		this.runTaskTimer(plugin, 0, 20L);
	}
	
	public MLGTimer(Challenges plugin, long totalTimeToMLG, long timeToMLG) {
		this.totalTimeToMLG = totalTimeToMLG;
		this.timeToMLG = timeToMLG;
		this.runTaskTimer(plugin, 0L, 20L);
	}

	@Override
	public void run() {
		if(ChallengeProfile.getInstance().getParticipants().size() == 0) return;
		if(!ChallengeProfile.getInstance().canTakeEffect()) {
			//this.cancel();
			return;
		}
		System.out.println(timeToMLG);
		if(timeToMLG <= 0) {
			totalTimeToMLG = 0;
			timeToMLG = 0;
			MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
			//if(ChallengeProfile.getInstance().canTakeEffect()) {
				mlgChallenge.onMLGPrepare(ChallengeProfile.getInstance().getParticipants().stream().filter(p -> !p.isDead()).collect(Collectors.toSet()));
			//}
			this.cancel();
		}
		else {
			timeToMLG -= 20;
		}	
	}

	/**
	 * @return the timeToMLG
	 */
	public long getTimeToMLG() {
		return timeToMLG;
	}

	/**
	 * @param timeToMLG the timeToMLG to set
	 */
	public void setTimeToMLG(long timeToMLG) {
		this.timeToMLG = timeToMLG;
	}

	/**
	 * @return the totalTimeToMLG
	 */
	public long getTotalTimeToMLG() {
		return totalTimeToMLG;
	}

	/**
	 * @param totalTimeToMLG the totalTimeToMLG to set
	 */
	public void setTotalTimeToMLG(long totalTimeToMLG) {
		this.totalTimeToMLG = totalTimeToMLG;
	}
}
