package me.wand555.challenges.settings.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockPlacingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.config.DisplayUtil;
import me.wand555.challenges.start.Challenges;

public class NoBlockPlacingListener extends CoreListener {

	public NoBlockPlacingListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.NO_BLOCK_PLACING)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				if(cProfile.isInChallenge(event.getPlayer())) {
					Material mat = event.getBlockPlaced().getType();
					if(mat != Material.LAVA 
							&& mat != Material.WATER
							&& mat != Material.END_PORTAL_FRAME
							&& !(mat == Material.FIRE 
								&& event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN)) {
						NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);					
						if(nBPChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(nBPChallenge, 
									ChallengeEndReason.NO_BLOCK_PLACE, 
									new Object[] {DisplayUtil.displayBlock(event.getBlock())},
									event.getPlayer());
						}
						else {
							String message = nBPChallenge.createReasonMessage(nBPChallenge.getPunishCause(), nBPChallenge.getPunishType(), event.getPlayer());
							callViolationPunishmentEventAndActUpon(nBPChallenge, message, event.getPlayer());
						}
						if(nBPChallenge.getPunishType() == PunishType.NOTHING) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
