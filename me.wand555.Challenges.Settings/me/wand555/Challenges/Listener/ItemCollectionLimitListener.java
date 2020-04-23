package me.wand555.Challenges.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;

public class ItemCollectionLimitListener implements Listener {

	public ItemCollectionLimitListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerPickUpItemEvent(EntityPickupItemEvent event) {
		if(event.getEntity() instanceof Player) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.ITEM_LIMIT_GLOBAL)) {
					Player player = (Player) event.getEntity();
					ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
					boolean added = iCLGChallenge.addToUniqueItems(event.getItem().getItemStack().getType(), player.getUniqueId());
					if(added) {
						if(iCLGChallenge.isOverLimit()) {
							cProfile.endChallenge(ChallengeEndReason.TOO_MANY_ITEMS_GLOBAL, player);
						}
						else {
							String message = iCLGChallenge.createItemCollectLogMessage(player.getName(), event.getItem().getItemStack().getType());
							cProfile.sendMessageToAllParticipants(message);
						}
					}
				}
			}
		}
	}
}
