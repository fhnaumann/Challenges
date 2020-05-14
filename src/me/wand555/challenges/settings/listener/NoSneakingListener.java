package me.wand555.challenges.settings.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoSneakingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.start.Challenges;

public class NoSneakingListener extends CoreListener {

	public NoSneakingListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSneakingEvent(PlayerToggleSneakEvent event) {
		if(event.isSneaking()) {
			if(ChallengeProfile.getInstance().canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.NO_SNEAKING)) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					if(cProfile.isInChallenge(event.getPlayer())) {
						NoSneakingChallenge nSChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SNEAKING);
						if(nSChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(nSChallenge, ChallengeEndReason.NO_BLOCK_PLACE, null, event.getPlayer());
						}
						else {
							String message = nSChallenge.createReasonMessage(nSChallenge.getPunishCause(), nSChallenge.getPunishType(), event.getPlayer());
							callViolationPunishmentEventAndActUpon(nSChallenge, message, event.getPlayer());
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
