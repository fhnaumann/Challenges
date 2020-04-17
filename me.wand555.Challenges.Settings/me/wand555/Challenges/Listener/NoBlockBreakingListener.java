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
						cProfile.endChallenge(event.getPlayer(), ChallengeEndReason.NO_BLOCK_PLACE);
					}
					else {
						nBBChallenge.enforcePunishment(event.getPlayer(), cProfile.getParticipantsAsPlayers(), nBBChallenge.getPunishType());
						String message = nBBChallenge.createReasonMessage(event.getPlayer(), nBBChallenge.getPunishCause(), nBBChallenge.getPunishType());
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
