package me.wand555.Challenges.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.Config.LanguageMessages;

public class ItemCollectionLimitGlobalListener implements Listener {

	public ItemCollectionLimitGlobalListener(Challenges plugin) {
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
	
	@EventHandler
	public void onPlayerCraftItemEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(!cProfile.canTakeEffect()) return;
		ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
		if(!iCLGChallenge.isActive()) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
		String title = event.getView().getTitle(); 
		if(title.equalsIgnoreCase(ChatColor.GREEN + "Settings") 
				|| title.equalsIgnoreCase(ChatColor.GREEN + "Backpack")
				|| title.equalsIgnoreCase(ChatColor.DARK_GREEN + "Collected Items")) return;
		Player player = (Player) event.getWhoClicked();
		
		if(event.getCurrentItem() != null && !event.getCurrentItem().getType().isAir()) {
			//Player has something on their cursor now
			if(Challenges.hasClickedTop(event)) {
				//Player has clicked top
				boolean added = iCLGChallenge.addToUniqueItems(event.getCurrentItem().getType(), player.getUniqueId());
				if(added) {
					if(iCLGChallenge.isOverLimit()) {
						cProfile.endChallenge(ChallengeEndReason.TOO_MANY_ITEMS_GLOBAL, player);
					}
					else {
						String message = iCLGChallenge.createItemCollectLogMessage(player.getName(), event.getCurrentItem().getType());
						cProfile.sendMessageToAllParticipants(message);
					}
				}
			}
		}		
	}
	/*
	@EventHandler
	public void onPlayerDragItemEvent(InventoryDragEvent event) {
		if(GenericChallenge.isActive(ChallengeType.ITEM_LIMIT_GLOBAL)) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.canTakeEffect()) {
				Player player = (Player) event.getWhoClicked();
				ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
				for(ItemStack bottomInvItem : event.getView().getBottomInventory().getContents()) {
					if(bottomInvItem == null || bottomInvItem.getType().isAir()) continue;
					boolean added = iCLGChallenge.addToUniqueItems(bottomInvItem.getType(), player.getUniqueId());
					if(added) {
						if(iCLGChallenge.isOverLimit()) {
							cProfile.endChallenge(ChallengeEndReason.TOO_MANY_ITEMS_GLOBAL, player);
						}
						else {
							String message = iCLGChallenge.createItemCollectLogMessage(player.getName(), bottomInvItem.getType());
							cProfile.sendMessageToAllParticipants(message);
						}
					}
				}
			}
		}		
	}
	
	*/
	
	@EventHandler
	public void playerDropItemEvent(ItemSpawnEvent event) {
		ItemCollectionLimitGlobalChallenge iCLGChallenge = GenericChallenge.getChallenge(ChallengeType.ITEM_LIMIT_GLOBAL);
		if(iCLGChallenge.isActive()) {
			if(iCLGChallenge.getUniqueItems().containsKey(event.getEntity().getItemStack().getType())) {
				event.getEntity().setCustomName(LanguageMessages.itemOnGroundAlreadyCollectedName);
				event.getEntity().setCustomNameVisible(true);
			}
		}
		
		
	}	
}
