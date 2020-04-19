package me.wand555.Challenges.ChallengeProfile.AssignRoleListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
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
			}
		}
	}
}
