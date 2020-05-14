package me.wand555.challenges.settings.challengeprofile.types.lavaground;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.challenges.start.Challenges;

/**
 * 
 * @author wand555
 *
 */
public class BlockChangeTimer extends BukkitRunnable {

	private LavaGroundChallenge lavaGroundChallenge;
	private Location blockLoc;
	private LavaGroundBlockData data;
	
	
	public BlockChangeTimer(Challenges plugin, LavaGroundChallenge lavaGroundChallenge, Location blockLoc, LavaGroundBlockData data) {
		this.lavaGroundChallenge = lavaGroundChallenge;
		this.blockLoc = blockLoc;
		this.data = data;
		this.runTaskTimer(plugin, lavaGroundChallenge.getTimeToTransition(), lavaGroundChallenge.getTimeToTransition());
	}
	
	@Override
	public void run() {
		switch (data.getStatus()) {
		case FIRST_PHASE:
			blockLoc.getBlock().setType(Material.MAGMA_BLOCK);
			data.setStatus(BlockStatus.SECOND_PHASE);
			break;
		case SECOND_PHASE:
			blockLoc.getBlock().setType(Material.LAVA);
			data.setStatus(BlockStatus.THIRD_PHASE);
			break;
		case THIRD_PHASE:
			if(!lavaGroundChallenge.isLavaStay()) blockLoc.getBlock().setType(data.getPreviousMaterial());		
			lavaGroundChallenge.getChangeTimers().remove(this);
			data = null;
			this.cancel();
			break;
		}
	}
	
	public Location getBlockLoc() {
		return this.blockLoc;
	}

	/**
	 * @return the data
	 */
	public LavaGroundBlockData getData() {
		return data;
	}

}
