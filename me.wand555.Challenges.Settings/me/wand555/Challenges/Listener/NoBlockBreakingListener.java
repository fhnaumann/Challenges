package me.wand555.Challenges.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoBlockBreakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;

public class NoBlockBreakingListener implements Listener {
	
	public NoBlockBreakingListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.NO_BLOCK_BREAKING)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				if(cProfile.isInChallenge(event.getPlayer().getUniqueId())) {
					NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
					if(nBBChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
						cProfile.endChallenge(ChallengeEndReason.NO_BLOCK_PLACE, event.getPlayer());
					}
					else {
						nBBChallenge.enforcePunishment(nBBChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), event.getPlayer());
						String message = nBBChallenge.createReasonMessage(nBBChallenge.getPunishCause(), nBBChallenge.getPunishType(), event.getPlayer());
						cProfile.sendMessageToAllParticipants(message);
					}
					if(nBBChallenge.getPunishType() == PunishType.NOTHING) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
