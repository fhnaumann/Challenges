package me.wand555.Challenges.Listener;

import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionSameItemLimitChallenge;

public class NoSameItemListener implements Listener {

	public NoSameItemListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemActionInInventoryEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(!cProfile.canTakeEffect()) return;
		ItemCollectionSameItemLimitChallenge iCSILChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
		if(!iCSILChallenge.isActive()) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		
		if(event.getCurrentItem() != null && !event.getCurrentItem().getType().isAir()) {
			//Player has something on their cursor now
			Material mat = event.getCurrentItem().getType();
			if(Challenges.hasClickedTop(event)) {
				//Player has clicked top
				if(iCSILChallenge.canBeObtained(mat, player.getUniqueId())) {
					System.out.println("obtained");
					iCSILChallenge.addToTotalInventoryItems(mat, player.getUniqueId());
				}
				else {
					iCSILChallenge.addToTotalInventoryItems(mat, player.getUniqueId());
					if(iCSILChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
						cProfile.endChallenge(ChallengeEndReason.SAME_ITEM_IN_INVENTORY, player);
					}
					else {
						iCSILChallenge.enforcePunishment(iCSILChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), player);
						String message = iCSILChallenge.createReasonMessage(iCSILChallenge.getPunishCause(), iCSILChallenge.getPunishType(), player);
						cProfile.sendMessageToAllParticipants(message);
					}
				}
			}
			else {
				if(event.isShiftClick()) {
					System.out.println("1");
					if(iCSILChallenge.shouldBeRemoved(mat)) {
						System.out.println("2");
						System.out.println(mat);
						if(iCSILChallenge.isOnlyOneWithMaterial(mat, player.getUniqueId())) {
							System.out.println("removed");
							iCSILChallenge.removeFromTotalInventoryitems(mat, player.getUniqueId());
						}		
					}
				}	
				
				else if(event.getClick() == ClickType.NUMBER_KEY){
					if(iCSILChallenge.shouldBeRemoved(mat)) {
						System.out.println("3");
						if(iCSILChallenge.isOnlyOneWithMaterial(mat, player.getUniqueId())) {
							System.out.println("removed");
							iCSILChallenge.removeFromTotalInventoryitems(mat, player.getUniqueId());
						}		
					}
				}
				
			}
		}
		else if(event.getCursor() != null && !event.getCursor().getType().isAir()) {
			Material mat = event.getCursor().getType();
			
			if(Challenges.hasClickedTop(event)) {
				System.out.println("21");
				System.out.println(iCSILChallenge.getTotalInventoryItems().containsKey(mat));
				if(iCSILChallenge.shouldBeRemoved(mat)) {
					System.out.println("22");
					if(iCSILChallenge.isOnlyOneWithMaterial(mat, player.getUniqueId())) {
						System.out.println("removed");
						iCSILChallenge.removeFromTotalInventoryitems(mat, player.getUniqueId());
					}		
				}
			}
		}
	}
	
	@EventHandler
	public void onItemGoToInventoryCheckEvent(EntityPickupItemEvent event) {
		if(event.getEntity() instanceof Player) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.NO_SAME_ITEM)) {
					Player player = (Player) event.getEntity();
					ItemCollectionSameItemLimitChallenge iCSILChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
					Material mat = event.getItem().getItemStack().getType();
					if(iCSILChallenge.canBeObtained(mat, player.getUniqueId())) {
						iCSILChallenge.addToTotalInventoryItems(mat, player.getUniqueId());
					}
					else {
						if(iCSILChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(ChallengeEndReason.SAME_ITEM_IN_INVENTORY, player);
						}
						else {
							iCSILChallenge.enforcePunishment(iCSILChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), player);
							String message = iCSILChallenge.createReasonMessage(iCSILChallenge.getPunishCause(), iCSILChallenge.getPunishType(), player);
							cProfile.sendMessageToAllParticipants(message);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onItemLeaveInventoryCheckEvent(PlayerDropItemEvent event) {
		//if(event.getEntity() instanceof Player) {
			ChallengeProfile cProfile = ChallengeProfile.getInstance();
			if(cProfile.canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.NO_SAME_ITEM)) {
					Player player = event.getPlayer();
					ItemCollectionSameItemLimitChallenge iCSILChallenge = GenericChallenge.getChallenge(ChallengeType.NO_SAME_ITEM);
					Material mat = event.getItemDrop().getItemStack().getType();
					iCSILChallenge.removeFromTotalInventoryitems(mat, player.getUniqueId());	
					System.out.println("removed from dropped");
				}
			}
		//}
	}
	
}
