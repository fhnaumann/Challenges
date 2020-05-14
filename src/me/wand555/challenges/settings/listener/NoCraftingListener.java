package me.wand555.challenges.settings.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.challengeprofile.types.ChallengeType;
import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;
import me.wand555.challenges.settings.challengeprofile.types.NoCraftingChallenge;
import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.config.DisplayUtil;
import me.wand555.challenges.start.Challenges;

public class NoCraftingListener extends CoreListener {

	public NoCraftingListener(Challenges plugin) {
		super(plugin);
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
							if(cProfile.isInChallenge(player)) {
								if(event.getCurrentItem() != null) {
									if(event.getCurrentItem().getType() != Material.AIR) {
										NoCraftingChallenge nCChallenge = GenericChallenge.getChallenge(ChallengeType.NO_CRAFTING);
										if(!nCChallenge.byPassItems.contains(event.getCurrentItem().getType())) {
											if(nCChallenge.getPunishType() == PunishType.CHALLENGE_OVER) {
												cProfile.endChallenge(nCChallenge, 
														ChallengeEndReason.NO_BLOCK_PLACE, 
														new Object[] {DisplayUtil.displayItemStack(event.getCurrentItem())},
														player);
											}
											else {		
												String message = nCChallenge.createReasonMessage(nCChallenge.getPunishCause(), nCChallenge.getPunishType(), player);
												callViolationPunishmentEventAndActUpon(nCChallenge, message, player);
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
}
