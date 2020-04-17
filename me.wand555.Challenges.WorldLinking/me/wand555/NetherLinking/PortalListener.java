package me.wand555.NetherLinking;


import java.util.List;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.scheduler.BukkitRunnable;
import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;
import me.wand555.EndLinking.EndHelper;

public class PortalListener implements Listener {
	
	private Challenges plugin;
	
	public PortalListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerCreatePortalEvent(PortalCreateEvent event) {
		if(event.getReason() == CreateReason.FIRE) {
			if(WorldLinkManager.worlds.contains(event.getWorld())) {
				//System.out.println(Bukkit.getWorld("ChallengeNether"));
				//event.getBlocks().forEach(b -> System.out.println(b.getLocation().getBlockX()+"/"+b.getLocation().getBlockY()+"/"+b.getLocation().getBlockZ()+" Type: "+b.getType()));
				new Gate(event.getBlocks());
				//System.out.println(Arrays.toString(Bukkit.getWorlds().toArray()));
				//Gate netherGate = LocationHelper.findFreeSpot(Bukkit.getWorld("world_nether"), gate);
				//System.out.println(netherGate.getTeleportTo());
				//first.setType(Material.AIR);
			}
			
		}
	}
	
	@EventHandler
	public void onEntityEnterPortalEvent(EntityPortalEvent event) {
		if(WorldLinkManager.worlds.contains(event.getFrom().getWorld())) {
			event.setCancelled(true);
			event.setSearchRadius(0);
			Gate gate = Gate.getGateEnteredFromLoc(event.getFrom().clone());
			if(gate != null) {
				Location loc = gate.findOrCreateAndReturnLocPortal(gate, gate.getEnvironment() == Environment.NORMAL ?
						WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NETHER).findFirst().get()
						: WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get(), event.getFrom());
				new BukkitRunnable() {
					
					@Override
					public void run() {
						//with passenger doesn't work in vanilla anyway
						if(loc != null) {
							final List<Entity> passengers = event.getEntity().getPassengers();
							if(passengers.isEmpty()) {
								event.getEntity().teleport(loc);
							}
							else {
								event.getEntity().teleport(loc);
								passengers.stream().forEachOrdered(e -> event.getEntity().addPassenger(e));
							}
						}
							
							
					}
				}.runTaskLater(plugin, 2L);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerEnterPortal(PlayerPortalEvent event) {	
		if(WorldLinkManager.worlds.contains(event.getFrom().getWorld())) {
			event.setCanCreatePortal(false);
			event.setCancelled(true);
			if(event.getCause() == TeleportCause.NETHER_PORTAL) {
				Gate gate = Gate.getGateEnteredFromLoc(event.getFrom().clone());
				//System.out.println(gate.getEnvironment());
				if(gate != null) {
					if(gate.getPointingTo() != null) {
						event.getPlayer().teleport(gate.getPointingTo().getTeleportTo());
					}
					else {
						Location loc = gate.findOrCreateAndReturnLocPortal(gate, gate.getEnvironment() == Environment.NORMAL ?
								WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NETHER).findFirst().get()
								: WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get(), event.getFrom());
						new BukkitRunnable() {
							
							@Override
							public void run() {
								if(loc != null)
									event.getPlayer().teleport(loc);
							}
						}.runTaskLater(plugin, 2L);
					}
					
				}
			}
			else if(event.getCause() == TeleportCause.END_PORTAL) {
				Location teleportTo = EndHelper.createOrFindPlatformAndAir(WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.THE_END).findFirst().get());
				event.getPlayer().teleport(teleportTo);
			}
		}
	}
	
	@EventHandler
	public void onPortalUnusualbreakingEvent(BlockPhysicsEvent event) {
		if(event.getChangedType() == Material.NETHER_PORTAL) {
			if(WorldLinkManager.worlds.contains(event.getBlock().getWorld())) {
				boolean b = Gate.removeGateFromLoc(event.getSourceBlock().getLocation());
			}
		}
	}
	
}
