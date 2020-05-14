package me.wand555.challenges.settings.listener;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.config.DisplayUtil;
import me.wand555.challenges.start.Challenges;
import me.wand555.challenges.worldlinking.WorldLinkManager;

public class PlayerDeathListener extends CoreListener {
	
	public PlayerDeathListener(Challenges plugin) {
		super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerDeathEvent(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if(event.getFinalDamage() < player.getHealth()) return;
		
		if(GenericChallenge.isActive(ChallengeType.END_ON_DEATH)) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.isInChallenge(player)) {
				if(cProfile.canTakeEffect()) {
					//event.getDrops().clear();
					Bukkit.getPluginManager().callEvent(new PlayerDeathEvent(player, Arrays.asList(player.getInventory().getContents()), player.getExpToLevel(), ""));
					cProfile.endChallenge(GenericChallenge.getChallenge(ChallengeType.END_ON_DEATH), 
							ChallengeEndReason.NATURAL_DEATH, 
							new Object[] {event instanceof EntityDamageByEntityEvent ? 
									DisplayUtil.displayDamageDealtBy(((EntityDamageByEntityEvent)event).getDamager())
									: DisplayUtil.displayDamageCause(event.getCause())},
							player);
					player.spigot().respawn();
				}
			}
		}	
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerDeathDropItemsEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(GenericChallenge.isActive(ChallengeType.END_ON_DEATH)) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.isInChallenge(player)) {
				event.getDrops().clear();
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(cProfile.isInChallenge(event.getPlayer())) {
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
