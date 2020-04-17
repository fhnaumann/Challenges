package me.wand555.Challenges.ChallengeProfile.Positions;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;

import me.wand555.Challenges.Config.LanguageMessages;

public class PositionManager {

	private HashSet<Position> positions = new HashSet<Position>();
	
	public void addToPositions(Position pos) {
		positions.add(pos);
	}
	
	public String displayPosition(Position pos) {
		Location loc = pos.getLocation();
		return LanguageMessages.returnPosition
				.replace("[X]", String.valueOf(loc.getBlockX()))
				.replace("[Y]", String.valueOf(loc.getBlockY()))
				.replace("[Z]", String.valueOf(loc.getBlockZ()))
				.replace("[POSNAME]", pos.getName())
				.replace("[WORLD]", loc.getWorld().getName());
	}
	
	public boolean positionWithNameExists(String name) {
		return positions.stream().anyMatch(pos -> pos.getName().equalsIgnoreCase(name));
	}
	
	public Position getPositionFromName(String name) {
		return positions.stream().filter(pos -> pos.getName().equalsIgnoreCase(name)).findFirst().get();
	}
	
	public HashSet<Position> getAllPositionsFromUUID(UUID uuid) {
		return positions.stream().filter(pos -> pos.getCreator().equals(uuid)).collect(Collectors.toCollection(HashSet::new));
	}

	/**
	 * @return the positions
	 */
	public HashSet<Position> getPositions() {
		return positions;
	}
}
