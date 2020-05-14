package me.wand555.challenges.api.events.beaten;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableSortedMap;

import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;

public class ItemCollectionLimitGlobalChallengeBeatenEvent extends ChallengeBeatenEvent {

	private ImmutableSortedMap<UUID, Integer> mostItemsCollected;
	
	public ItemCollectionLimitGlobalChallengeBeatenEvent(List<ChallengeType> activeChallenges, String message, LinkedHashMap<UUID, Integer> mostItemsCollected) {
		super(activeChallenges, message);
		this.mostItemsCollected = ImmutableSortedMap.copyOf(mostItemsCollected);
	}

	/**
	 * @return the mostItemsCollected
	 */
	public ImmutableSortedMap<UUID, Integer> getMostItemsCollected() {
		return mostItemsCollected;
	}
}
