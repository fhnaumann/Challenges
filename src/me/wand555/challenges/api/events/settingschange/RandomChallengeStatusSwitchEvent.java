package me.wand555.challenges.api.events.settingschange;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.wand555.challenges.api.events.Overridable;
import me.wand555.challenges.settings.challengeprofile.types.RandomChallenge;

/**
 * Called when a player changes the setting of a challenge that involves the randomizedMapped.
 * ChallengeType will only be RANDOMIZE_BLOCK_DROPS, RANDOMIZE_MOB_DROPS or RANDOMIZE_CRAFTING.
 * If newStatus == false the randomizedMapped won't store any modifications.
 * @author Felix
 *
 * @param <T> The challenge this was called with (T extends RandomChallenge and T extends Randomizer).
 */
public class RandomChallengeStatusSwitchEvent<T extends RandomChallenge> extends ChallengeStatusSwitchEvent<T> implements Overridable {

	private ImmutableMap<Material, Material> randomizedMapped;
	private String overrideMessage;
	
	public RandomChallengeStatusSwitchEvent(T challenge, HashMap<Material, Material> randomizedMapped, Player player) {
		super(challenge, player);
		this.randomizedMapped = ImmutableMap.copyOf(randomizedMapped);
	}
	
	public ImmutableMap<Material, Material> getRandomizedMapped() {
		return randomizedMapped;
	}
	
	public void setRandomizedMapped(HashMap<Material, Material> randomizedMapped) {
		this.randomizedMapped = ImmutableMap.copyOf(randomizedMapped);
	}

	@Override
	public String getOverrideMessage() {
		return overrideMessage;
	}

	@Override
	public void setOverrideMessage(String overrideMessage) {
		this.overrideMessage = overrideMessage;		
	}

	@Override
	public boolean hasOverrideMessage() {
		return overrideMessage != null && !overrideMessage.isEmpty();
	}

}
