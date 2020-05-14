package me.wand555.Challenge.DeathRun.distancecalculations;

import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenge.DeathRun.DeathRunHandler;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.extra.DeathRunGoal;
import me.wand555.challenges.start.Challenges;

public class CheckOvertakingTimer extends BukkitRunnable {

	private DeathRunPlaceManager placeManager;
	private DeathRunHandler deathRunHandler;
	
	public CheckOvertakingTimer(Challenges plugin) {
		this.placeManager = DeathRunPlaceManager.getPlaceManager();
		this.deathRunHandler = DeathRunHandler.getDeathRunHandler();
		this.runTaskTimer(plugin, 20L, 10L);
	}
	
	@Override
	public void run() {
		this.placeManager.calculateNewPlacings();
		this.deathRunHandler.updateScoreBoard();
	}

}
