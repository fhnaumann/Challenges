package me.wand555.NetherLinking;

import java.util.HashSet;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public class Gate {

	public static final int MAX_SEARCH_RADIUS = 128;
	private static Set<Gate> gates = new HashSet<Gate>();
	
	private final Set<Block> frameBlocks;
	private final Set<Block> portalBlocks;
	//private final Set<Block> relevantBlocks;
	private final Axis axis;
	private final Environment environment;
	private Location teleportTo;
	private Gate pointingTo;
	
	public Gate(List<BlockState> blocks) {
		frameBlocks = blocks.stream().filter(s -> s.getType() == Material.OBSIDIAN).map(s -> s.getBlock()).collect(Collectors.toCollection(HashSet::new));
		portalBlocks = blocks.stream().filter(s -> s.getType() == Material.NETHER_PORTAL).map(s -> s.getBlock()).collect(Collectors.toCollection(HashSet::new));
		axis = findAxis();
		environment = blocks.stream().findFirst().get().getWorld().getEnvironment();
		Block first = blocks.stream().filter(s -> s.getType() == Material.NETHER_PORTAL).findFirst().get().getBlock();
		
		int max = portalBlocks.stream().mapToInt(b -> b.getLocation().getBlockY()).max().getAsInt();
		Location loc = portalBlocks.stream().filter(b -> b.getLocation().getBlockY() < max).map(b -> b.getLocation()).findAny().get();
		teleportTo = loc.add(0.5, 0, 0.5);
		/*if(axis == Axis.X) {
			Block east = first.getRelative(BlockFace.EAST);
			Block west = first.getRelative(BlockFace.WEST);
			if(portalBlocks.contains(east)) {
				System.out.println("here3");
				this.teleportTo = east.getLocation().clone().add(0.5, 0, 0);
			}
			else {
				System.out.println("here4");
				this.teleportTo = west.getLocation().clone().add(-0.5, 0, 0);
			}
		}
		else {
			Block north = first.getRelative(BlockFace.NORTH);
			Block south = first.getRelative(BlockFace.SOUTH);
			if(portalBlocks.contains(north)) {
				System.out.println("here1");
				this.teleportTo = north.getLocation().clone().add(0, 0, -0.5);
			}
			else {
				System.out.println("here2");
				this.teleportTo = south.getLocation().clone().add(0, 0, 0.5);
			}		
		}*/
		gates.add(this);
	}
	
	public Gate(Set<Block> frameBlocks, Set<Block> portalBlocks, Axis axis, Environment environment) {
		this.frameBlocks = frameBlocks;
		this.portalBlocks = portalBlocks;
		this.axis = axis;
		this.environment = environment;
		Block first = portalBlocks.stream().filter(s -> s.getType() == Material.NETHER_PORTAL).findAny().get();
		/*
		if(axis == Axis.X) {
			Block east = first.getRelative(BlockFace.EAST);
			Block west = first.getRelative(BlockFace.WEST);
			if(portalBlocks.contains(east)) {
				System.out.println("here3");
				this.teleportTo = east.getLocation().clone().add(0.5, 0, 0);
			}
			else {
				System.out.println("here4");
				this.teleportTo = west.getLocation().clone().add(-0.5, 0, 0);
			}
		}
		else {
			Block north = first.getRelative(BlockFace.NORTH);
			Block south = first.getRelative(BlockFace.SOUTH);
			if(portalBlocks.contains(north)) {
				System.out.println("here1");
				this.teleportTo = north.getLocation().clone().add(0, 0, -0.5);
			}
			else {
				System.out.println("here2");
				this.teleportTo = south.getLocation().clone().add(0, 0, 0.5);
			}		
		}
		*/
		int maxY = portalBlocks.stream().mapToInt(b -> b.getLocation().getBlockY()).max().getAsInt();
		Location loc = portalBlocks.stream()
				.filter(b -> b.getLocation().getBlockY() < maxY)
				.map(b -> b.getLocation())
				.findAny().get();
		
		this.teleportTo = loc.add(0.5, 0, 0.5);
		
		gates.add(this);
	}
	
	public Gate(Set<Block> frameBlocks, Set<Block> portalBlocks, Axis axis, Environment environment, Location teleportTo) {
		this.frameBlocks = frameBlocks;
		this.portalBlocks = portalBlocks;
		this.axis = axis;
		this.environment = environment;
		this.teleportTo = teleportTo;
		gates.add(this);
	}
	
	//doesnt work
	public Gate fromNormalToNetherAdjustment() {
		frameBlocks.forEach(b -> {
			Location loc = b.getLocation();
			loc.setX(loc.getBlockX()/8);
			loc.setZ(loc.getBlockZ()/8);
			//System.out.println(b.getLocation().getBlockX());
		});
		portalBlocks.forEach(b -> {
			Location loc = b.getLocation();
			loc.setX(loc.getBlockX()/8);
			loc.setZ(loc.getBlockZ()/8);
		});
		return this;
	}
	
	//doesnt work
	public Gate fromNetherToNormalAdjustment() {
		frameBlocks.forEach(b -> {
			Location loc = b.getLocation();
			loc.setX(loc.getBlockX()*8);
			loc.setZ(loc.getBlockZ()*8);
		});
		portalBlocks.forEach(b -> {
			Location loc = b.getLocation();
			loc.setX(loc.getBlockX()*8);
			loc.setZ(loc.getBlockZ()*8);
		});
		return this;
	}
	
	private Axis findAxis() {
		Block first = portalBlocks.stream().findFirst().get();
		if(first.getRelative(BlockFace.NORTH).getType() == Material.NETHER_PORTAL 
				|| first.getRelative(BlockFace.SOUTH).getType() == Material.NETHER_PORTAL) {
			return Axis.X;
		}
		//west, east
		else {
			return Axis.Z;
		}
	}
	
	/**
	 * 
	 * @param gate Gate player entered
	 * @param worldTo world to
	 * @param from the exact location the player entered. Often close to gate.{@link #getTeleportTo()}
	 * @return
	 */
	public Location findOrCreateAndReturnLocPortal(Gate gate, World worldTo, Location from) {
		if(GenericChallenge.isActive(ChallengeType.NETHER_FORTRESS_SPAWN)) {
			if(worldTo.getEnvironment() == Environment.NETHER) {
				Location clone = from.clone();
				clone.setWorld(worldTo);
				Location fortressLoc = worldTo.locateNearestStructure(clone, StructureType.NETHER_FORTRESS, 400, true);
				if(fortressLoc != null) {
					//dunno if working correctly
					Gate nearFortressGate = LocationHelper.findFreeSpot(worldTo, fortressLoc, gate.getAxis(), gate.getEnvironment());
					if(nearFortressGate != null) {
						gate.setPointingTo(nearFortressGate);
						nearFortressGate.setPointingTo(gate);
						return nearFortressGate.getTeleportTo();
					}
				}
				Bukkit.broadcastMessage("Couldn't find a nether fortress...");
			}	
		}
		
		
		Gate existingGate = portalInRadius(gate.getTeleportTo(), worldTo);
		if(existingGate != null) {
			return existingGate.getTeleportTo();
		}
		else {
			Gate newGate = LocationHelper.findFreeSpot(worldTo, gate.getTeleportTo(), gate.getAxis(), gate.getEnvironment());
			return newGate != null ? newGate.getTeleportTo() : null;
		}
	}
	
	private Gate portalInRadius(Location loc, World worldTo) {
		Location clone = teleportTo.clone();
		//USUALLY MULITPLY/DIVIDE BY 8
		clone.setWorld(worldTo);
		
		return gates.stream()
				.filter(gate -> gate.getEnvironment() == worldTo.getEnvironment())//.forEach(gate -> System.out.println(gate.getTeleportTo().getWorld().getName()));
				.filter(gate -> gate.getTeleportTo().distance(clone) <= MAX_SEARCH_RADIUS)
				.findFirst().orElse(null);
		//return null;
	}
	
	@Nullable
	public static Gate getGateEnteredFromLoc(@Nonnull Location loc) {
		return gates.stream().filter(gate -> portalBlocksContainLoc(gate, loc)).findFirst().orElse(null);
	}
	
	private static boolean portalBlocksContainLoc(Gate gate, Location loc) {
		//System.out.println("Gate: X: " + gate.getTeleportTo().getBlockX() + " Y: " + gate.getTeleportTo().getBlockY() + " Z: " + gate.getTeleportTo().getBlockZ());
		//gate.portalBlocks.forEach(b -> System.out.println(b.getLocation()));
		//System.out.println();
		//System.out.println("PlayerLoc: X: " + (loc.getBlockX()) + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ());
		return gate.portalBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().add(1, 0, 0).getBlock().getLocation()))
				|| gate.portalBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().add(0, 0, 1).getBlock().getLocation()))
				|| gate.portalBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().add(-1, 0, 0).getBlock().getLocation()))
				|| gate.portalBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().add(0, 0, -1).getBlock().getLocation()))
				|| gate.portalBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().getBlock().getLocation()))
				|| gate.frameBlocks.stream().anyMatch(b -> b.getLocation().equals(loc.clone().getBlock().getLocation()));
	}
	
	public static boolean removeGateFromLoc(Location loc) {
		Gate toRemove = getGateEnteredFromLoc(loc);
		return toRemove != null ? gates.remove(toRemove) : false;
	}

	/**
	 * @return the pointingTo
	 */
	public Gate getPointingTo() {
		return pointingTo;
	}

	/**
	 * @param pointingTo the pointingTo to set
	 */
	public void setPointingTo(Gate pointingTo) {
		this.pointingTo = pointingTo;
	}

	/**
	 * @return the gates
	 */
	public static Set<Gate> getGates() {
		return gates;
	}

	/**
	 * @return the frameBlocks
	 */
	public Set<Block> getFrameBlocks() {
		return frameBlocks;
	}

	/**
	 * @return the portalBlocks
	 */
	public Set<Block> getPortalBlocks() {
		return portalBlocks;
	}

	/**
	 * @return the axis
	 */
	public Axis getAxis() {
		return axis;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @return the teleportTo
	 */
	public Location getTeleportTo() {
		return teleportTo;
	}

	/**
	 * @param teleportTo the teleportTo to set
	 */
	public void setTeleportTo(Location teleportTo) {
		this.teleportTo = teleportTo;
	}
}
