package me.wand555.challenges.settings.listener.roleassign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.height.HeightChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGChallenge;
import me.wand555.challenges.settings.challengeprofile.types.mlg.MLGTimer;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;
import me.wand555.challenges.settings.config.WorldUtil;
import me.wand555.challenges.start.Challenges;
import me.wand555.challenges.worldlinking.WorldLinkManager;

public class PlayerQuitListener implements Listener {

	public PlayerQuitListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if(WorldLinkManager.worlds.contains(event.getPlayer().getWorld())) {
			ChallengeProfile.getInstance().removeFromParticipants(event.getPlayer());
			if(GenericChallenge.isActive(ChallengeType.ON_BLOCK) && ChallengeProfile.getInstance().canTakeEffect()) {
				((OnBlockChallenge)GenericChallenge.getChallenge(ChallengeType.ON_BLOCK)).removePlayerFromBossBar(event.getPlayer());
				((HeightChallenge)GenericChallenge.getChallenge(ChallengeType.BE_AT_HEIGHT)).removePlayerFromBossBar(event.getPlayer());
			}
			if(ChallengeProfile.getInstance().isInMLGRightNow) {
				if(GenericChallenge.isActive(ChallengeType.MLG)) {
					WorldUtil.loadPlayerInformationInChallengeAndApply(event.getPlayer());
					MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
					mlgChallenge.getInMLGWorld().remove(event.getPlayer().getUniqueId());
					if(mlgChallenge.getInMLGWorld().isEmpty()) {
						mlgChallenge.setTimer(new MLGTimer(Challenges.getPlugin(Challenges.class), mlgChallenge));
						ChallengeProfile.getInstance().setInMLGRightNow();
					}
				}
			}
			
		}
	}
}
