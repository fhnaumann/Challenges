package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import org.bukkit.Material;

public interface Randomizer {

	final Material[] NORMAL_MATERIALS = Material.values();
	
	public void randomizeItems();
	
	public Material getRandomizedMaterial(Material mat);
	
	public boolean isRandomized();
}
