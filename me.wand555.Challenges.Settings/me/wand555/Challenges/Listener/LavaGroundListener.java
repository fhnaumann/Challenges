package me.wand555.Challenges.Listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.LavaGroundChallenge.LavaGroundChallenge;

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
