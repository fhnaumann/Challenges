package me.wand555.challenges.settings.challengeprofile.types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Material;

import com.google.common.collect.Lists;

public abstract class RandomChallenge extends GenericChallenge implements Randomizer {

	protected static LinkedHashMap<Material, Material> randomizeMapped = new LinkedHashMap<>();
	
	protected RandomChallenge(ChallengeType type) {
		super(type);
	}
	
	@Override
	public HashMap<Material, Material> randomizeItems() {
		if(!isRandomized()) {
			return Lists.newArrayList(NORMAL_MATERIALS).stream()
					.filter(mat -> !mat.isAir())
					.filter(Material::isItem)
					.collect(Collectors.toMap(Function.identity(), 
							mat -> NORMAL_MATERIALS[ThreadLocalRandom.current().nextInt(0, NORMAL_MATERIALS.length)], 
							(v1, v2) -> v1, 
							HashMap::new));
		}
		return null;
	}
	
	@Override
	public Material getRandomizedMaterial(Material mat) {
		return getRandomizeMapped().getOrDefault(mat, Material.AIR);
	}
	
	@Override
	public boolean isRandomized() {
		return !randomizeMapped.isEmpty();
	}
	
	public static boolean clearRandomizationIfCase() {
		if(!isActive(ChallengeType.RANDOMIZE_BLOCK_DROPS) 
				&& !isActive(ChallengeType.RANDOMIZE_MOB_DROPS) 
				&& !isActive(ChallengeType.RANDOMIZE_CRAFTING)) {
			randomizeMapped.clear();
			return true;
		}
		return false;
	}

	public HashMap<Material, Material> getRandomizeMapped() {
		return randomizeMapped;
	}
	
	public void setRandomizedMapped(LinkedHashMap<Material, Material> randomized) {
		randomizeMapped = randomized;
	}
}
