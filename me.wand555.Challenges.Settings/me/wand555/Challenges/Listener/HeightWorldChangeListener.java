package me.wand555.Challenges.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge.HeightChallenge;

public class HeightWorldChangeListener implements Listener {

	public HeightWorldChangeListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHeightWorldChangeEvent(PlayerChangedWorldEvent event) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(cProfile.isInChallenge(event.getPlayer())) {
			HeightChallenge heightChallenge = GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT);
			if(heightChallenge.isActive()) {
				heightChallenge.removePlayerFromBossBar(event.getPlayer());
				heightChallenge.addPlayerToBossBar(event.getPlayer());
			}
		}
	}
}
