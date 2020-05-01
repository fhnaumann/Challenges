package me.wand555.Challenges.Listener;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthWaitDamageRunnable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthWaitRegRunnable;

public class SharedHealthPlayerChangeLifeListener implements Listener {

	private Challenges plugin;
	
	public SharedHealthPlayerChangeLifeListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onSharedHealthPlayerTookDamageEvent(EntityDamageEvent event) {
		if(event.getCause() != DamageCause.CUSTOM) {
			if(event.getEntity() instanceof Player) {
				if(ChallengeProfile.getInstance().canTakeEffect()) {
					if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
						Player player = (Player) event.getEntity();
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {	
							event.setCancelled(true);
							SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
							if(sHChallenge.getSharedHealthWaitDamageRunnableID() == 0) {
								sHChallenge.setSharedHealthWaitDamageRunnableID(new SharedHealthWaitDamageRunnable(plugin).getTaskId());
								sHChallenge.setSharedHealth(sHChallenge.getSharedHealth() - event.getFinalDamage());
								if(sHChallenge.getSharedHealth() > 0) {
									ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
										.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
										.forEach(p -> {
											p.setHealth(sHChallenge.getSharedHealth());
											p.playEffect(EntityEffect.HURT);
										});
								}
								else {
									ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
									.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
									.forEach(p -> {
										if(p != null) p.setHealth(0);
									});
									sHChallenge.setSharedHealth(sHChallenge.getDefaultHealthToSet());
								}
							}			
						}	
					}
				}		
			}
		}	
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onSharedHealthPlayerRegainHealthEvent(EntityRegainHealthEvent event) {
		if(event.getRegainReason() != RegainReason.CUSTOM) {
			if(event.getEntity() instanceof Player) {
				if(ChallengeProfile.getInstance().canTakeEffect()) {
					if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
						Player player = (Player) event.getEntity();
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
							SharedHealthChallenge sHChallenge = GenericChallenge.getChallenge(ChallengeType.SHARED_HEALTH);
							event.setCancelled(true);
							if(sHChallenge.getSharedHealthWaitRegRunnableID() == 0) {
								sHChallenge.setSharedHealthWaitRegRunnableID(new SharedHealthWaitRegRunnable(plugin).getTaskId());
								sHChallenge.setSharedHealth(sHChallenge.getSharedHealth() + event.getAmount());
								if(sHChallenge.getSharedHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
									ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
									.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
									.filter(p -> !p.isDead())
									.forEach(p -> {
										p.setHealth(sHChallenge.getSharedHealth());
										p.setAbsorptionAmount(player.getAbsorptionAmount());
									});
								}
								else {
									ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
									.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
									.filter(p -> !p.isDead())
									.forEach(p -> {
										p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
										//p.setAbsorptionAmount(player.getAbsorptionAmount());
									});
									sHChallenge.setSharedHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
								}
							}
						}
					}
				}
			}
		}		
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onSharedHealthPlayerGainAbsorbtionEvent(EntityPotionEffectEvent event) {
		if(event.getNewEffect() != null) {
			if(event.getNewEffect().getType().getName().equals(PotionEffectType.ABSORPTION.getName())) {
				if(event.getEntity() instanceof Player) {
					
					if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
						Player player = (Player) event.getEntity();
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
							
							//event.setCancelled(true);
							Bukkit.getScheduler().runTaskLater(plugin, () -> {
								ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
								.filter(p -> !p.hasPotionEffect(PotionEffectType.ABSORPTION))
								.forEach(p -> {
									boolean b = p.addPotionEffect(event.getNewEffect());
								});
							}, 2L);
							
						}
					}
				}
			}	
		}
		else {
			if(event.getOldEffect().getType().getName().equals(PotionEffectType.ABSORPTION.getName())) {
				if(event.getEntity() instanceof Player) {
					if(GenericChallenge.isActive(ChallengeType.SHARED_HEALTH)) {
						Player player = (Player) event.getEntity();
						if(ChallengeProfile.getInstance().isInChallenge(player.getUniqueId())) {
							//event.setCancelled(true);
							Bukkit.getScheduler().runTaskLater(plugin, () -> {
								ChallengeProfile.getInstance().getParticipantsAsPlayers().stream()
								.filter(p -> !p.hasPotionEffect(PotionEffectType.ABSORPTION))
								.forEach(p -> {
									p.removePotionEffect(event.getOldEffect().getType());
								});
							}, 2L);
							
						}
					}
				}
			}
		}
	}
}
