package me.wand555.challenges.settings.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightChallenge;
import me.wand555.challenges.start.Challenges;

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
