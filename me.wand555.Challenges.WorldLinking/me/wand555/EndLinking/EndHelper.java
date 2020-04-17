package me.wand555.EndLinking;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import me.wand555.Challenges.Challenges;

public class EndHelper {

	public static Location createOrFindPlatformAndAir(World world) {
		if(ObsidianPlatform.isCreated()) {
			Block center = ObsidianPlatform.getPlatform().getTeleportTo().getBlock();
			for(int y=0; y<=4; y++) {
				for(int x=-2; x<=2; x++) {
					for(int z=-2; z<=2; z++) {
						Block block = world.getBlockAt(center.getX()+x, center.getY()+y, center.getZ()+z);
						if(y == 0) {
							block.setType(Material.OBSIDIAN);
						}
						else {
							block.setType(Material.AIR);
						}
						
						
					}
				}
			}
			return ObsidianPlatform.getPlatform().getTeleportTo().clone().add(0.5, 1, 0.5);
		}
		else {
			Block center = determineCenter(world);
			for(int y=0; y<=4; y++) {
				for(int x=-2; x<=2; x++) {
					for(int z=-2; z<=2; z++) {
						Block block = world.getBlockAt(center.getX()+x, center.getY()+y, center.getZ()+z);
						if(y == 0) {
							block.setType(Material.OBSIDIAN);
						}
						else {
							block.setType(Material.AIR);
						}
						
						
					}
				}
			}
			ObsidianPlatform platform = new ObsidianPlatform(center.getLocation());
			ObsidianPlatform.setCreated(true);
			return platform.getTeleportTo().clone().add(0.5, 1, 0.5);
		}
	}
	
	private static Block determineCenter(World world) {
		int minX = -100;
		int minY = 40;
		int minZ = -100;
		int maxX = 100;
		int maxY = 70;
		int maxZ = 100;
		int rndmX = Challenges.random.nextInt(maxX-minX) + minX;
		int rndmY = Challenges.random.nextInt(maxY-minY) + minY;
		int rndmZ = Challenges.random.nextInt(maxZ-minZ) + minZ;
		
		return world.getBlockAt(rndmX, rndmY, rndmZ);	
	}
	
	public static void reset() {
		if(ObsidianPlatform.isCreated()) {
			ObsidianPlatform.setPlatform(null);
			ObsidianPlatform.setCreated(false);
		}
	}
}
