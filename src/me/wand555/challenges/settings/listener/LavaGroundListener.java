package me.wand555.challenges.settings.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.lavaground.LavaGroundChallenge;
import me.wand555.challenges.start.Challenges;

public class LavaGroundListener extends CoreListener {

	public LavaGroundListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onLavaGroundChallengeChangeEvent(PlayerMoveEvent event) {
		if(event.getPlayer().isOnGround()) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.canTakeEffect()) {
				if(cProfile.isInChallenge(event.getPlayer())) {
					LavaGroundChallenge lavaGroundChallenge = GenericChallenge.getChallenge(ChallengeType.GROUND_IS_LAVA);
					if(lavaGroundChallenge.isActive()) {
						if(event.getFrom().getBlock().getType() != Material.LAVA) {
							if(lavaGroundChallenge.getChangeTimers().stream().noneMatch(timer -> isSameBlock(timer.getBlockLoc().clone().add(0, 1, 0), event.getFrom()))) {
								lavaGroundChallenge.startFresh(event.getFrom().clone().subtract(0, 1, 0));
							}
						}					
					}
				}
			}	
		}	
	}
	
	private boolean isSameBlock(Location from, Location to) {
		return from.getBlockX() == to.getBlockX() 
				&& from.getBlockY() == to.getBlockY() 
				&& from.getBlockZ() == to.getBlockZ();
	}

	
}
