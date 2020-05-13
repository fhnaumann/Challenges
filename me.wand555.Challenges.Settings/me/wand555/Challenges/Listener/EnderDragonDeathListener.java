package me.wand555.Challenges.Listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class EnderDragonDeathListener implements Listener {

	
	public EnderDragonDeathListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEnderDragonDeathEvent(EntityDeathEvent event) {
		if(event.getEntityType() == EntityType.ENDER_DRAGON) {
			if(WorldLinkManager.worlds.contains(event.getEntity().getWorld())) {
				ChallengeProfile.getInstance().endChallenge(null, ChallengeEndReason.FINISHED, null, event.getEntity().getKiller());
			}
		}
	}
}
