package me.wand555.Challenges.Listener;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockPlacingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;

public class NoBlockPlacingListener implements Listener {

	public NoBlockPlacingListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.NO_BLOCK_PLACING)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				if(cProfile.isInChallenge(event.getPlayer().getUniqueId())) {
					Material mat = event.getBlockPlaced().getType();
					if(mat != Material.LAVA 
							&& mat != Material.WATER
							&& mat != Material.END_PORTAL_FRAME
							&& !(mat == Material.FIRE 
								&& event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN)) {
						NoBlockPlacingChallenge nBPChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_PLACING);
						if(nBPChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(event.getPlayer(), ChallengeEndReason.NO_BLOCK_PLACE);
						}
						else {
							nBPChallenge.enforcePunishment(event.getPlayer(), cProfile.getParticipantsAsPlayers(), nBPChallenge.getPunishType());
							String message = nBPChallenge.createReasonMessage(event.getPlayer(), nBPChallenge.getPunishCause(), nBPChallenge.getPunishType());
							cProfile.sendMessageToAllParticipants(message);
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
