package me.wand555.challenges.settings.challengeprofile.types.lavaground;

import org.bukkit.Material;

public class LavaGroundBlockData {

	private BlockStatus status;
	private Material previousMaterial;
	
	public LavaGroundBlockData(BlockStatus status, Material previousMaterial) {
		this.status = status;
		this.previousMaterial = previousMaterial;
	}

	/**
	 * @return the status
	 */
	public BlockStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(BlockStatus status) {
		this.status = status;
	}

	/**
	 * @return the previousMaterial
	 */
	public Material getPreviousMaterial() {
		return previousMaterial;
	}
}
