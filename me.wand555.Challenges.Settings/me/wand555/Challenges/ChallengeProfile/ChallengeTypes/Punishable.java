package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Punishable {

	public PunishType getPunishType();
	public void setPunishType(PunishType punishType);
	
	public ChallengeType getPunishCause();
	
	default void enforcePunishment(Player causer, Collection<Player> affected, PunishType type) {
		switch(type) {
		case HEALTH_1:
			causer.damage(1);
			break;
		case HEALTH_2:
			causer.damage(2);
			break;
		case HEALTH_3:
			causer.damage(3);		
			break;
		case HEALTH_4:
			causer.damage(4);
			break;
		case HEALTH_5:
			causer.damage(5);
			break;
		case HEALTH_6:
			causer.damage(6);
			break;
		case HEALTH_7:
			causer.damage(7);
			break;
		case HEALTH_8:
			causer.damage(8);
			break;
		case HEALTH_9:
			causer.damage(9);
			break;
		case HEALTH_10:
			causer.damage(10);
			break;
		case HEALTH_ALL_1:
			affected.forEach(p -> p.damage(1));
			break;
		case HEALTH_ALL_2:
			affected.forEach(p -> p.damage(2));
			break;
		case HEALTH_ALL_3:
			affected.forEach(p -> p.damage(3));		
			break;
		case HEALTH_ALL_4:
			affected.forEach(p -> p.damage(4));
			break;
		case HEALTH_ALL_5:
			affected.forEach(p -> p.damage(5));
			break;
		case HEALTH_ALL_6:
			affected.forEach(p -> p.damage(6));
			break;
		case HEALTH_ALL_7:
			affected.forEach(p -> p.damage(7));
			break;
		case HEALTH_ALL_8:
			affected.forEach(p -> p.damage(8));
			break;
		case HEALTH_ALL_9:
			affected.forEach(p -> p.damage(9));
			break;
		case HEALTH_ALL_10:
			affected.forEach(p -> p.damage(10));
			break;
		case DEATH:
			causer.setHealth(0);
			break;
		case DEATH_ALL:
			affected.forEach(p -> p.setHealth(0));
			break;
		case ONE_ITEM:
		{		
			List<ItemStack> causerInv = Stream.of(causer.getInventory().getContents())
					.filter(item -> item != null)
					.collect(Collectors.toList());
			ItemStack randomItem = causerInv.get(ThreadLocalRandom.current().nextInt(0, causerInv.size()));
			causer.getInventory().remove(randomItem);
			break;
		}
		case ONE_ITEM_ALL:
		{
			affected.forEach(p -> {
				List<ItemStack> pInv = Stream.of(p.getInventory().getContents()).filter(item -> item != null).collect(Collectors.toList());
				ItemStack randomItem = pInv.get(ThreadLocalRandom.current().nextInt(0, pInv.size()));
				causer.getInventory().removeItem(randomItem);
			});
			break;
		}
		case ALL_ITEMS:
			causer.getInventory().clear();
			break;
		case ALL_ITEMS_ALL:
			affected.forEach(p -> p.getInventory().clear());
			break;
		default: break;
		}
	}
}
