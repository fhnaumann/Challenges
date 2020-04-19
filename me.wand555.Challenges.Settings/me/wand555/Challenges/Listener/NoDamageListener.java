package me.wand555.Challenges.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class NoDamageListener implements Listener {

	public NoDamageListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerTookDamageEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(WorldLinkManager.worlds.contains(event.getEntity().getWorld())) {
				if(ChallengeProfile.getInstance().canTakeEffect()) {
					if(GenericChallenge.isActive(ChallengeType.NO_DAMAGE)) {
						ChallengeProfile cProfile = ChallengeProfile.getInstance();
						Player player = (Player) event.getEntity();
						if(cProfile.isInChallenge(player.getUniqueId())) {
							cProfile.endChallenge(ChallengeEndReason.NO_DAMAGE, player);
						}
					}			
				}
				else {
					event.setDamage(0);
				}
			}		
		}		
	}
}
