package me.wand555.NetherLinking;


import java.util.HashSet;

import java.util.Set;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Orientable;

import me.wand555.Challenges.Challenges;

public class LocationHelper {
	
	public static final int MAX_SEARCH_RADIUS_XY = 128;
	
	//returns newly created gate or null
	public static Gate findFreeSpot(World worldTo, Location to, Axis axis, Environment environment) {
		//search in nether
		//if(gate.getEnvironment() == Environment.NORMAL) gate.fromNormalToNetherAdjustment();
		//search in normal
		//else gate.fromNetherToNormalAdjustment();
		
		Block b = to.getBlock();
		
		int netherX = b.getX();
		int netherY = b.getY();
		int netherZ = b.getZ();
		
		int x = 0;
		int z = 0;
		int dx = 0;
		int dz = -1;
		int t = Math.max(Math.abs(netherX), Math.abs(netherZ));
		
		for(int i=0; i<Math.pow(MAX_SEARCH_RADIUS_XY, 2); i++) {
			if((-Math.abs(netherX)/2 <= x) && (x <= Math.abs(netherX)/2) && (-Math.abs(netherZ)/2 <= z) && (z <= Math.abs(netherZ)/2)) {
				int rndmX = Challenges.random.nextInt((netherX+x+2)-(netherX+x-2)) + (netherX+x-2);
				int rndmZ = Challenges.random.nextInt((netherZ+z+2)-(netherZ+z-2)) + (netherZ+z-2);
				Block block = findFirstFreeHeight(worldTo, rndmX, rndmZ);
				if(block != null) {
					if(portalFitInArea(worldTo, axis, block.getLocation())) {
						return spawnGate(worldTo, axis, environment, block.getLocation());
					}
				}
				
			}
			if((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1-z))) {
				t = dx;
				dx = -dz;
				dz = t;
			}
		     x += dx;
		     z += dz;
			i++;
		}
		
		/*
		int x = 0;
		int z = 0;
		
		int l = 1;
		while(true) {
			int k = l-1;
			for(int n=0; n<=k; n++) {
				x = -k+n;
				z = -n;
			}
			for(int n=1; n<=k; n++) {
				x = n;
				z = -k+n;
			}
			for(int n=1; n<=k; n++) {
				x = k-n;
				z = n;
			}
			for(int n=1; n<=k+1; n++) {
				x = -n;
				z = k-n;
			}
			//if(x > MAX_SEARCH_RADIUS_XY || y > MAX_SEARCH_RADIUS_XY) continue;
			int rndmX = Challenge.random.nextInt((b.getX()+x+2)-(b.getX()+x-2)) + (b.getX()+x-2);
			int rndmZ = Challenge.random.nextInt((b.getZ()+z+2)-(b.getZ()+z-2)) + (b.getZ()+z-2);
			Block block = findFirstFreeHeight(worldTo, gate, rndmX, rndmZ);
			if(block != null) {
				if(portalFitInArea(worldTo, gate, block.getLocation())) {
					return spawnGate(worldTo, gate, block.getLocation());
				}
			}
			
			l++;
			if(l>MAX_SEARCH_RADIUS_XY/2) break;
		}
		*/
		return null;
	}
	
	private static Block findFirstFreeHeight(World worldTo, int x, int z) {
		//normal
		if(worldTo.getEnvironment() == Environment.NORMAL) {
			int i = 230;
			while(i > 6) {
				Block block = worldTo.getBlockAt(x, i, z);
				if(block.getType() == Material.AIR || block.getType() == Material.LAVA || block.getType() == Material.WATER) {
					Block under = block.getRelative(BlockFace.DOWN);
					if(under.getType() != Material.AIR && under.getType() != Material.LAVA && under.getType() != Material.WATER) {
						return block;
					}
				}
				i--;
			}
		}
		//nether
		else {
			int i = 116;
			while(i > 6) {
				Block block = worldTo.getBlockAt(x, i, z);
				if(block.getType() == Material.AIR || block.getType() == Material.LAVA) {
					Block under = block.getRelative(BlockFace.DOWN);
					if(under.getType() != Material.AIR && under.getType() != Material.LAVA) {
						return block;
					}
				}
				i--;
			}
		}
		return null;
	}
	
	public static boolean portalFitInArea(World worldTo, Axis axis, Location to) {
		if(axis == Axis.X) {
			for(int l=0; l<=4; l++) {
				for(int h=-1; h<=5; h++) {
					Block block = to.clone().add(l, h, 0).getBlock();
					if(h == -1) {
						if(block.getType() == Material.AIR || block.getType() == Material.LAVA || block.getType() == Material.WATER) {
							return false;
						}
					}
					else {
						if(block.getType() != Material.AIR) {
							return false;
						}
						else {
							Block under = block.getRelative(BlockFace.DOWN);
							if(under.getType() != Material.AIR && under.getType() != Material.LAVA && under.getType() != Material.WATER) {
								continue;
							}
						}
					}
				}
			}
			return true;
		}
		//Z axis
		else {
			for(int l=0; l<=4; l++) {
				for(int h=-1; h<=5; h++) {
					Block block = to.clone().add(0, h, l).getBlock();
					if(h == -1) {
						if(block.getType() == Material.AIR || block.getType() == Material.LAVA || block.getType() == Material.WATER) {
							return false;
						}
					}
					else {
						if(block.getType() != Material.AIR) {
							return false;
						}
						else {
							Block under = block.getRelative(BlockFace.DOWN);
							if(under.getType() != Material.AIR && under.getType() != Material.LAVA && under.getType() != Material.WATER) {
								continue;
							}
						}
					}
				}
			}
			return true;
		}
	}

	
	public static Gate spawnGate(World worldTo, Axis axis, Environment environment, Location to) {
		Set<Block> frameBlocks = new HashSet<Block>();
		Set<Block> portalBlocks = new HashSet<Block>();
		
		
		if(axis == Axis.Z) {
			for(int l=0; l<=4; l++) {
				for(int h=0; h<=5; h++) {
					Block block = to.clone().add(l, h, 0).getBlock();
					//obsidian walls
					if(l == 0 || h == 0 || l == 4 || h == 5) {
						block.setType(Material.OBSIDIAN, false);
						frameBlocks.add(block);
					}
					//portal block
					else {
						block.setType(Material.NETHER_PORTAL, false);				
						portalBlocks.add(block);
					}
				}
			}
		}
		else {
			for(int l=0; l<=4; l++) {
				for(int h=0; h<=5; h++) {
					Block block = to.clone().add(0, h, l).getBlock();
					//obsidian walls
					if(l == 0 || h == 0 || l == 4 || h == 5) {
						block.setType(Material.OBSIDIAN, false);
						frameBlocks.add(block);
					}
					//portal block
					else {
						block.setType(Material.NETHER_PORTAL, false);
						Orientable orientable = (Orientable) block.getBlockData();
						orientable.setAxis(axis);
						block.setBlockData(orientable);
						portalBlocks.add(block);
						System.out.println("HEREEEE");
					}
				}
			}	
		}
		return new Gate(frameBlocks, portalBlocks, axis, environment == Environment.NORMAL ? Environment.NETHER : Environment.NORMAL);
	}
	
	public static Location checkValidSpawn(World world, int x, int y, int z) {
		Block block = world.getBlockAt(x, Math.max(y, 6), z);
		if(block.getType() == Material.AIR) {
			for(; y>5; y--) {
				block = world.getBlockAt(x, y, z);
				if(block.getType() != Material.AIR) break;
			}
			if(block.getType() == Material.AIR) return null;
		}
		if(block.getType() == Material.LAVA) {
			for(; y < 116; y++) {
				block = world.getBlockAt(x, y, z);
				if(block.getType() != Material.LAVA && block.getType() != Material.AIR) break;
			}
			if(block.getType() == Material.AIR || block.getType() == Material.LAVA) return null;
		}
		return block.getLocation();
	}
}
