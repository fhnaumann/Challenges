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
							cProfile.endChallenge(event.getPlayer(), ChallengeEndReason.NO_BLOCK_PLACE);
						}
						else {
							nSChallenge.enforcePunishment(event.getPlayer(), cProfile.getParticipantsAsPlayers(), nSChallenge.getPunishType());
							String message = nSChallenge.createReasonMessage(event.getPlayer(), nSChallenge.getPunishCause(), nSChallenge.getPunishType());
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
