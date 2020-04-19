package me.wand555.Challenges.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoSneakingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;

public class NoSneakingListener implements Listener {

	public NoSneakingListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSneakingEvent(PlayerToggleSneakEvent event) {
		if(event.isSneaking()) {
			if(ChallengeProfile.getInstance().canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.NO_SNEAKING)) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					if(cProfile.isInChallenge(event.getPlayer().getUniqueId())) {
						NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
						if(nSChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(ChallengeEndReason.NO_BLOCK_PLACE, event.getPlayer());
						}
						else {
							nSChallenge.enforcePunishment(nSChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), event.getPlayer());
							String message = nSChallenge.createReasonMessage(nSChallenge.getPunishCause(), nSChallenge.getPunishType(), event.getPlayer());
							cProfile.sendMessageToAllParticipants(message);
						}
						if(nSChallenge.getPunishType() == PunishType.NOTHING) {
							event.setCancelled(true);
						}
					}
				}
			}
		}	
	}
}