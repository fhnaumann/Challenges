package me.wand555.Challenges.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedBlockDropsChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedCraftingChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomizedMobDropsChallenge;
import me.wand555.Challenges.WorldLinkingManager.WorldLinkManager;

public class RandomizeListener implements Listener {
	
	public RandomizeListener(Challenges plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRandomizedBlockDropEvent(BlockBreakEvent event) {
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.RANDOMIZE_BLOCK_DROPS)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				Player player = event.getPlayer();
				if(cProfile.isInChallenge(player)) {
					Block block = event.getBlock();
					RandomizedBlockDropsChallenge rBDChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_BLOCK_DROPS);
					Material random = rBDChallenge.getRandomizedMaterial(block.getType());
					if(random.isAir()) return;
					if(!random.isItem()) return;
					event.setDropItems(false);
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(random));
				}
			}
		}
	}
	
	@EventHandler
	public void onRandomizeMobDropEvent(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player) return;
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if(entity.getKiller() == null) return;
			Player player = entity.getKiller();
			if(ChallengeProfile.getInstance().canTakeEffect()) {
				if(GenericChallenge.isActive(ChallengeType.RANDOMIZE_MOB_DROPS)) {
					ChallengeProfile cProfile = ChallengeProfile.getInstance();
					if(cProfile.isInChallenge(player)) {
						RandomizedMobDropsChallenge rMDChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_MOB_DROPS);
						event.getDrops().forEach(itemstack -> {
							Material random = rMDChallenge.getRandomizedMaterial(itemstack.getType());
							if(random.isAir()) return;
							if(!random.isItem()) return;
							entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(random, itemstack.getAmount()));
						});
					}
				}
			}
		}	
	}
	
	@EventHandler
	public void onRandomizeCraftingResultEvent(PrepareItemCraftEvent event) {
		if(event.getRecipe() == null) return;
		if(event.getRecipe().getResult() == null || event.getRecipe().getResult().getType() == Material.AIR) return;
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.RANDOMIZE_CRAFTING)) {
				ChallengeProfile cProfile = ChallengeProfile.getInstance();
				if(cProfile.isInChallenge((Player)event.getViewers().get(0))) {
					ItemStack original = event.getRecipe().getResult();
					RandomizedCraftingChallenge rCChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING);
					event.getInventory().setResult(new ItemStack(rCChallenge.getRandomizedMaterial(original.getType()), original.getAmount()));
				}
			}
		}
	}
	
	@EventHandler
	public void onRandomizeFurnaceResultEvent(FurnaceSmeltEvent event) {
		if(event.getResult() == null || event.getResult().getType() == Material.AIR) return;
		if(ChallengeProfile.getInstance().canTakeEffect()) {
			if(GenericChallenge.isActive(ChallengeType.RANDOMIZE_CRAFTING)) {
				//cannot use isInChallenge() because its a ton of work to get the player who opened/placed the furnace
				if(WorldLinkManager.worlds.contains(event.getBlock().getWorld())) {
					RandomizedCraftingChallenge rCChallenge = GenericChallenge.getChallenge(ChallengeType.RANDOMIZE_CRAFTING);
					Material random = rCChallenge.getRandomizedMaterial(event.getResult().getType());
					if(random.isAir()) return;
					if(!random.isItem()) return;
					event.setResult(new ItemStack(random, event.getResult().getAmount()));
				}
			}
		}
	}

}
