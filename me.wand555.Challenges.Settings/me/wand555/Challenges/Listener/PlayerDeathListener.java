package me.wand555.Challenges.Listener;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class PlayerDeathListener implements Listener {

	private Challenges plugin;
	
	public PlayerDeathListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(GenericChallenge.isActive(ChallengeType.END_ON_DEATH)) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.isInChallenge(player.getUniqueId())) {
				if(cProfile.canTakeEffect()) {
					event.getDrops().clear();
					cProfile.endChallenge(player, ChallengeEndReason.NATURAL_DEATH);
					player.spigot().respawn();	
				}
			}
		}	
	}
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(cProfile.isInChallenge(event.getPlayer().getUniqueId())) {
			if(event.isBedSpawn()) {
				if(event.getRespawnLocation() != null) {
					if(!WorldLinkManager.worlds.contains(event.getRespawnLocation().getWorld())) {
						event.setRespawnLocation(Bukkit.getWorld("ChallengeOverworld").getSpawnLocation());
					}
				}
				else {
					event.setRespawnLocation(Bukkit.getWorld("ChallengeOverworld").getSpawnLocation());
				}
			}
			else {
				event.setRespawnLocation(Bukkit.getWorld("ChallengeOverworld").getSpawnLocation());
			}
		}
	}
}
