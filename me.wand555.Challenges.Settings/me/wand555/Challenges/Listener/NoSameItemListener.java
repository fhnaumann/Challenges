package me.wand555.Challenges.Listener;

import java.util.stream.Collectors;
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
import me.wand555.Challenges.Config.DisplayUtil;

public class NoSameItemListener extends CoreListener {

	public NoSameItemListener(Challenges plugin) {
		super(plugin);
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
				if(!iCSILChallenge.canBeObtained(mat, player.getUniqueId())) {
					if(iCSILChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
						cProfile.endChallenge(iCSILChallenge, 
								ChallengeEndReason.SAME_ITEM_IN_INVENTORY,
								new Object[] {
										DisplayUtil.displayItemStack(event.getCurrentItem()),
										DisplayUtil.displayPlayersAlreadyOwningItem(
												cProfile.getParticipants().stream()
													.filter(p -> p.getInventory().contains(event.getCurrentItem().getType()))
													.collect(Collectors.toList()))},
								player);
					}
					else if(iCSILChallenge.getPunishType() == PunishType.NOTHING) {
						event.setCancelled(true);
					}
					else {
						String message = iCSILChallenge.createReasonMessage(iCSILChallenge.getPunishCause(), iCSILChallenge.getPunishType(), player);
						callViolationPunishmentEventAndActUpon(iCSILChallenge, message, player);
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
					if(!iCSILChallenge.canBeObtained(mat, player.getUniqueId())) {
						if(iCSILChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
							cProfile.endChallenge(iCSILChallenge, 
									ChallengeEndReason.SAME_ITEM_IN_INVENTORY, 
									new Object[] {
											DisplayUtil.displayItemStack(event.getItem().getItemStack()),
											DisplayUtil.displayPlayersAlreadyOwningItem(
													cProfile.getParticipants().stream()
														.filter(p -> p.getInventory().contains(event.getItem().getItemStack()))
														.collect(Collectors.toList()))},
									player);
						}
						else if(iCSILChallenge.getPunishType() == PunishType.NOTHING) {
							event.setCancelled(true);
						}
						else {
							String message = iCSILChallenge.createReasonMessage(iCSILChallenge.getPunishCause(), iCSILChallenge.getPunishType(), player);
							callViolationPunishmentEventAndActUpon(iCSILChallenge, message, player);
						}
					}
					
					
				}
			}
		}
	}
}
