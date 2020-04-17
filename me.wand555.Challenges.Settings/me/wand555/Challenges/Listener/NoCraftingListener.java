package me.wand555.Challenges.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.NoCraftingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;

public class NoCraftingListener implements Listener {

	public NoCraftingListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCraftingEvent(InventoryClickEvent event) {
		if(event.getRawSlot() == 0) {
			if(event.getWhoClicked() instanceof Player) {
				if(event.getView().getType() == InventoryType.CRAFTING || event.getView().getType() == InventoryType.WORKBENCH) {
					Player player = (Player) event.getWhoClicked();
					if(ChallengeProfile.getInstance().canTakeEffect()) {
						if(GenericChallenge.isActive(ChallengeType.NO_CRAFTING)) {
							ChallengeProfile cProfile = ChallengeProfile.getInstance();
							if(cProfile.isInChallenge(player.getUniqueId())) {
								if(event.getCurrentItem() != null) {
									if(event.getCurrentItem().getType() != Material.AIR) {
										NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
										if(nCChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
											cProfile.endChallenge(player, ChallengeEndReason.NO_BLOCK_PLACE);
										}
										else {
											nCChallenge.enforcePunishment(player, cProfile.getParticipantsAsPlayers(), nCChallenge.getPunishType());
											String message = nCChallenge.createReasonMessage(player, nCChallenge.getPunishCause(), nCChallenge.getPunishType());
											cProfile.sendMessageToAllParticipants(message);
										}
										if(nCChallenge.getPunishType() == PunishType.NOTHING) {
											event.setCancelled(true);
										}
									}
								}
							}
						}
					}
				}	
			}
		}		
	}
}
