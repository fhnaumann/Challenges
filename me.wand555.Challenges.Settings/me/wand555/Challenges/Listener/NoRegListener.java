package me.wand555.Challenges.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public class NoRegListener implements Listener {
	
	public NoRegListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onNoRegEvent(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			if(ChallengeProfile.getInstance().canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.NO_REG_HARD)) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					Player player = (Player) event.getEntity();
					if(cProfile.isInChallenge(player.getUniqueId())) {
						event.setCancelled(true);
					}
				}	
			}	
		}
	}
	
	@EventHandler
	public void onNoRegHardAbsorptionEvent(EntityPotionEffectEvent event) {
		if(event.getEntity() instanceof Player) {
			if(event.getModifiedType() == PotionEffectType.ABSORPTION) {
				if(GenericChallenge.isActive(ChallengeType.NO_REG_HARD)) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					Player player = (Player) event.getEntity();
					if(cProfile.isInChallenge(player.getUniqueId())) {
						event.setCancelled(true);
					}
				}
			}	
		}
	}
}
