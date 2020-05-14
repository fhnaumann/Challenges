package me.wand555.challenges.settings.config;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class DisplayUtil {

	private static String fitMaterial(String toFit) {
		return toFit.toLowerCase().replace('_', ' ');
	}
	
	public static String displayMaterial(Material mat) {
		return WordUtils.capitalize(fitMaterial(mat.toString()));
	}
	
	public static String displayDamageCause(DamageCause cause) {
		return WordUtils.capitalize(fitMaterial(cause.toString()));
	}
	
	public static String displayDamageDealtBy(Entity damageDealtBy) {
		return WordUtils.capitalize(fitMaterial(damageDealtBy.getType().toString()));
	}
	
	public static String displayBlock(Block block) {
		return WordUtils.capitalize(fitMaterial(block.getType().toString()));
	}
	
	public static String displayItemStack(ItemStack item) {
		return WordUtils.capitalize(displayMaterial(item.getType()));
	}
	
	public static String displayPlayersAlreadyOwningItem(List<Player> players) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<players.size(); i++) {
			stringBuilder.append(players.get(i).getName());
			if(i+1 >= players.size()) {
				//last one
			}
			else {
				stringBuilder.append(", ");
			}
		}
		return stringBuilder.toString();
	}
}
