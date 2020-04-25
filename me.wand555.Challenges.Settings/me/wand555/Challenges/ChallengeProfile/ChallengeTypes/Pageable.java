package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.inventory.Inventory;

public interface Pageable {

	public HashMap<UUID, Integer> getPageMap();
	public int getPageCurrentlyOn(UUID uuid);
	public int placeItemsAlreadyCollected(Inventory gui, UUID uuid);
	public boolean nextPageExists(int page);
}
