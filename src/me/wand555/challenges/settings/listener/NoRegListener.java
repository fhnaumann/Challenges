package me.wand555.challenges.settings.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.start.Challenges;

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
					if(cProfile.isInChallenge(player)) {
						System.out.println("blocked");
						event.setCancelled(true);
					}
				}	
			}	
		}
	}
	
	@EventHandler
	public void onNoRegHardAbsorptionEvent(EntityPotionEffectEvent event) {
		if(event.getEntity() instanceof Player) {
			if(event.getNewEffect() != null) {
				if(event.getNewEffect().getType().getId() == 22 || event.getNewEffect().getType().getId() == 10) {
					if(GenericChallenge.isActive(ChallengeType.NO_REG_HARD)) {
						ChallengeProfile cProfile = ChallengeProfile.getInstance();
						Player player = (Player) event.getEntity();
						if(cProfile.isInChallenge(player)) {
							System.out.println("blockedPotion");
							event.setCancelled(true);
						}
					}
				}		
			}	
		}
	}
}
