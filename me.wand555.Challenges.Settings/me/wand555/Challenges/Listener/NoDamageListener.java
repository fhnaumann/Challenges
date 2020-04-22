package me.wand555.Challenges.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoDamageChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
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
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					Player player = (Player) event.getEntity();
					if(cProfile.isInChallenge(player.getUniqueId())) {
						NoDamageChallenge noDamageChallenge = GenericChallenge.getChallenge(ChallengeType.NO_DAMAGE);
						if(noDamageChallenge.isActive()) {
							if(noDamageChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
								cProfile.endChallenge(ChallengeEndReason.NO_DAMAGE, player);
							}
							else {
								noDamageChallenge.enforcePunishment(noDamageChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), player);
								String message = noDamageChallenge.createNoDamageReasonMessage(
										player.getName(), 
										event instanceof EntityDamageByEntityEvent ? ((EntityDamageByEntityEvent)event).getDamager() : null, 
										event.getCause(), 
										event.getFinalDamage());
								cProfile.sendMessageToAllParticipants(message);
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
				else {
					event.setDamage(0);
				}
			}		
		}		
	}
}
