package me.wand555.challenges.settings.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.wand555.challenges.api.events.violation.NoDamageChallengeViolationEvent;
import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoDamageChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.config.DisplayUtil;
import me.wand555.challenges.start.Challenges;
import me.wand555.challenges.worldlinking.WorldLinkManager;

public class NoDamageListener extends CoreListener {

	public NoDamageListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerTookDamageEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(WorldLinkManager.worlds.contains(event.getEntity().getWorld())) {
				if(ChallengeProfile.getInstance().canTakeEffect()) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					Player player = (Player) event.getEntity();
					if(cProfile.isInChallenge(player)) {
						if(event.getFinalDamage() > 0) {
							NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
							if(noDamageChallenge.isActive()) {
								if(noDamageChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
									cProfile.endChallenge(noDamageChallenge, 
											ChallengeEndReason.NO_DAMAGE, 
											new Object[] {event.getFinalDamage(),
													event instanceof EntityDamageByEntityEvent ? 
													DisplayUtil.displayDamageDealtBy(((EntityDamageByEntityEvent)event).getDamager())
														: DisplayUtil.displayDamageCause(event.getCause())		
											},
											player);
								}
								else {
									String message = noDamageChallenge.createNoDamageReasonMessage(
											player.getName(), 
											event instanceof EntityDamageByEntityEvent ? ((EntityDamageByEntityEvent)event).getDamager() : null, 
											event.getCause(), 
											event.getFinalDamage());
									NoDamageChallengeViolationEvent violationEvent = new NoDamageChallengeViolationEvent(noDamageChallenge, noDamageChallenge.getPunishType(), 
											message, event.getCause(), event.getFinalDamage(), player);
									Bukkit.getServer().getPluginManager().callEvent(violationEvent);
									if(!violationEvent.isCancelled()) {
										noDamageChallenge.enforcePunishment(noDamageChallenge.getPunishType(), cProfile.getParticipants(), player);
										cProfile.sendMessageToAllParticipants(violationEvent.getLogMessage());
									}
									else {
										event.setCancelled(true);
										if(violationEvent.hasDeniedMessage()) player.sendMessage(violationEvent.getDeniedMessage());
									}
								}		
							}
							else {
								if(cProfile.logDamage) {
									cProfile.sendMessageToAllParticipants(noDamageChallenge.createDamageLogMessage(
											player.getName(), 
											event instanceof EntityDamageByEntityEvent ? ((EntityDamageByEntityEvent)event).getDamager() : null,
													event.getCause(), 
													event.getFinalDamage()));
								}
							}
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
