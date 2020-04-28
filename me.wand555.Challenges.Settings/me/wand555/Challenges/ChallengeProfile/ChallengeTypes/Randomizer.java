package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.Material;

public interface Randomizer {

	final Material[] NORMAL_MATERIALS = Material.values();
	
	public HashMap<Material, Material> randomizeItems();
	
	public Material getRandomizedMaterial(Material mat);
	
	public boolean isRandomized();
}
