package me.wand555.Challenge.DeathRun;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;

public class Countdown extends BukkitRunnable {

	private Challenges plugin;
	private int counter;
	
	public Countdown(Challenges plugin, int counter) {
		this.plugin = plugin;
		this.counter = counter;
		this.runTaskTimer(plugin, 20L, 20L);
	}
	
	@Override
	public void run() {
		if(counter > 0) {
			ChallengeProfile.getInstance().getParticipants().forEach(p -> {
				p.sendTitle(Integer.toString(counter), null, 1, 18, 1);
			});
		}
		else {
			DeathRunHandler.getDeathRunHandler().startDeathRun(plugin);
			this.cancel();
		}
		
		counter--;
	}

	
}
