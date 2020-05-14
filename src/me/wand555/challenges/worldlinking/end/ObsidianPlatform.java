package me.wand555.challenges.worldlinking.end;

import org.bukkit.Location;

public class ObsidianPlatform {

	private static boolean isCreated = false;
	private static ObsidianPlatform platform;
	private final Location teleportTo;
	
	public ObsidianPlatform(Location teleportTo) {
		this.teleportTo = teleportTo;
		setPlatform(this);
	}

	/**
	 * @return the teleportTo
	 */
	public Location getTeleportTo() {
		return teleportTo;
	}

	/**
	 * @return the isCreated
	 */
	public static boolean isCreated() {
		return isCreated;
	}

	/**
	 * @param isCreated the isCreated to set
	 */
	public static void setCreated(boolean isCreated) {
		ObsidianPlatform.isCreated = isCreated;
	}

	/**
	 * @return the platform
	 */
	public static ObsidianPlatform getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public static void setPlatform(ObsidianPlatform platform) {
		ObsidianPlatform.platform = platform;
	}
}
