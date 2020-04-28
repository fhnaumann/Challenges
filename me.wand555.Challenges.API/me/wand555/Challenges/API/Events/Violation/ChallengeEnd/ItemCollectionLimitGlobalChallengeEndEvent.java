package me.wand555.Challenges.API.Events.Violation.ChallengeEnd;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableSortedMap;

import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;

public class ItemCollectionLimitGlobalChallengeEndEvent extends ChallengeEndEvent<ItemCollectionLimitGlobalChallenge> {

	private ImmutableSortedMap<UUID, Integer> mostItemsCollected;
	
	public ItemCollectionLimitGlobalChallengeEndEvent(ItemCollectionLimitGlobalChallenge challenge,
			ChallengeEndReason endReason, String message, LinkedHashMap<UUID, Integer> mostItemsCollected, Player p) {
		super(challenge, endReason, message, p);
		this.mostItemsCollected = ImmutableSortedMap.copyOf(mostItemsCollected);
	}

	/**
	 * @return the mostItemsCollected
	 */
	public ImmutableSortedMap<UUID, Integer> getMostItemsCollected() {
		return mostItemsCollected;
	}

}
