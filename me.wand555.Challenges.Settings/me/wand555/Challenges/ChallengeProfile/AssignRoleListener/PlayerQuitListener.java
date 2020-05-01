package me.wand555.Challenges.ChallengeProfile.AssignRoleListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.HeightChallenge.HeightChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.Config.WorldUtil;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class PlayerQuitListener implements Listener {

	public PlayerQuitListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if(WorldLinkManager.worlds.contains(event.getPlayer().getWorld())) {
			ChallengeProfile.getInstance().removeFromParticipants(event.getPlayer().getUniqueId());
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
