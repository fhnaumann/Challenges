package me.wand555.challenges.settings.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoBlockBreakingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.config.DisplayUtil;
import me.wand555.challenges.start.Challenges;

public class NoBlockBreakingListener extends CoreListener {
	
	public NoBlockBreakingListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.NO_BLOCK_BREAKING)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				if(cProfile.isInChallenge(event.getPlayer())) {
					NoBlockBreakingChallenge nBBChallenge = GenericChallenge.getChallenge(ChallengeType.NO_BLOCK_BREAKING);
					if(nBBChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
						cProfile.endChallenge(nBBChallenge, 
								ChallengeEndReason.NO_BLOCK_PLACE, 
								new Object[] {DisplayUtil.displayBlock(event.getBlock())},
								event.getPlayer());
					}
					else {
						String message = nBBChallenge.createReasonMessage(nBBChallenge.getPunishCause(), nBBChallenge.getPunishType(), event.getPlayer());
						callViolationPunishmentEventAndActUpon(nBBChallenge, message, event.getPlayer());
					}
					if(nBBChallenge.getPunishType() == PunishType.NOTHING) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
