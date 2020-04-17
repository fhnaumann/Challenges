package me.wand555.Challenges.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;

public class MLGListener implements Listener {

	private Challenges plugin;
	
	public MLGListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMLGFailEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.isInChallenge(player.getUniqueId())) {
				MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
				if(mlgChallenge.isActive()) {
					mlgChallenge.onMLGDone(player, false);
				}
			}
		}
	}
	
	@EventHandler
	public void onMLGBeatenEvent(PlayerBucketEmptyEvent event) {
		if(event.getBucket() == Material.WATER_BUCKET) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.isInChallenge(event.getPlayer().getUniqueId())) {
				MLGChallenge mlgChallenge = GenericChallenge.getChallenge(ChallengeType.MLG);
				if(mlgChallenge.isActive()) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							if(mlgChallenge.getInMLGWorld().containsKey(event.getPlayer().getUniqueId())) {
								mlgChallenge.onMLGDone(event.getPlayer(), true);
								event.getBlock().setType(Material.AIR);
							}
						}
					}.runTaskLater(plugin, 20L);
				}
			}
		}
	}
}
