package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.Config.LanguageMessages;

public abstract class GenericChallenge implements ItemDisplayCreator {

	protected static HashMap<ChallengeType, GenericChallenge> activeChallenges = new HashMap<>();
	
	protected boolean active;
	protected ChallengeType type;
	
	protected GenericChallenge(ChallengeType type) {
		this.type = type;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setAround() {
		active = !active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public abstract ItemStack getDisplayItem();
	
	public void sendTitleChangeMessage(Collection<Player> players) {
		String title;
		String subtitle = null;
		if(type.isAmountable()) {
			Amountable amountable = getChallenge(type);
			title = LanguageMessages.titleWithAmountChallengeChange
					.replace("[CHALLENGE]",  getCorrectChallengeNameTranslation(type))
					.replace("[AMOUNT]", active ? Integer.toString((int)amountable.getAmount()) : ChatColor.GRAY+"-")
					.replace("[STATUS]", getCorrectChallengeStatus(active));
			if(type.isPunishable() && active) {
				Punishable punishable = getChallenge(type);
				subtitle = LanguageMessages.subtitleChallengeChange
						.replace("[PUNISHMENT]", getFittingPunishmentMessage2(punishable.getPunishType()));
			}
		}
		else {
			title = LanguageMessages.titleChallengeChange
					.replace("[CHALLENGE]", getCorrectChallengeNameTranslation(type))
					.replace("[STATUS]", getCorrectChallengeStatus(active));
			if(type.isPunishable() && active) {
				Punishable punishable = getChallenge(type);
				subtitle = LanguageMessages.subtitleChallengeChange
						.replace("[PUNISHMENT]", getFittingPunishmentMessage2(punishable.getPunishType()));
			}
		}
		for(Player p : players) {
			p.sendTitle(title, subtitle, 10, 60, 10);
		}
	}
	
	private String getFittingPunishmentMessage2(PunishType punishment) {
		switch(punishment) {
		case NOTHING: return LanguageMessages.punishNothing;
		case HEALTH_1: return LanguageMessages.punishHealth.replace("[AMOUNT]", "1");
		case HEALTH_2: return LanguageMessages.punishHealth.replace("[AMOUNT]", "2");
		case HEALTH_3:return LanguageMessages.punishHealth.replace("[AMOUNT]", "3");
		case HEALTH_4: return LanguageMessages.punishHealth.replace("[AMOUNT]", "4");
		case HEALTH_5: return LanguageMessages.punishHealth.replace("[AMOUNT]", "5");
		case HEALTH_6: return LanguageMessages.punishHealth.replace("[AMOUNT]", "6");
		case HEALTH_7: return LanguageMessages.punishHealth.replace("[AMOUNT]", "7");
		case HEALTH_8: return LanguageMessages.punishHealth.replace("[AMOUNT]", "8");
		case HEALTH_9: return LanguageMessages.punishHealth.replace("[AMOUNT]", "9");
		case HEALTH_10: return LanguageMessages.punishHealth.replace("[AMOUNT]", "10");
		case HEALTH_ALL_1: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "1");
		case HEALTH_ALL_2: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "2");
		case HEALTH_ALL_3: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "3");
		case HEALTH_ALL_4: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "4");
		case HEALTH_ALL_5: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "5");
		case HEALTH_ALL_6: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "6");
		case HEALTH_ALL_7: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "7");
		case HEALTH_ALL_8: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "8");
		case HEALTH_ALL_9: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "9");
		case HEALTH_ALL_10: return LanguageMessages.punishHealthAll.replace("[AMOUNT]", "10");
		case DEATH: return LanguageMessages.punishDeath;
		case DEATH_ALL: return LanguageMessages.punishDeathAll;
		case ONE_ITEM: return LanguageMessages.punishOneRandomItem;
		case ONE_ITEM_ALL: return LanguageMessages.punishOneRandomItemAll;
		case ALL_ITEMS: return LanguageMessages.punishAllItems;
		case ALL_ITEMS_ALL: return LanguageMessages.punishAllItemsAll;
		case CHALLENGE_OVER: return LanguageMessages.punishChallengeOver;
		default: return "&2Unknown";
		}
	}
	
	private String getCorrectChallengeNameTranslation(ChallengeType type) {
		switch(type) {
		case END_ON_DEATH: return LanguageMessages.guiDeathName;
		case NETHER_FORTRESS_SPAWN: return LanguageMessages.guiFortressSpawnName;
		case NO_DAMAGE: return LanguageMessages.guiNoDamageName;
		case NO_REG: return LanguageMessages.guiNoRegName;
		case NO_REG_HARD: return LanguageMessages.guiNoRegHardName;
		case CUSTOM_HEALTH: return LanguageMessages.guiCustomHealthName;
		case SHARED_HEALTH: return LanguageMessages.guiSharedHealthName;
		case NO_BLOCK_PLACING: return LanguageMessages.guiNoPlacingName;
		case NO_BLOCK_BREAKING: return LanguageMessages.guiNoBreakingName;
		case NO_CRAFTING: return LanguageMessages.guiNoCraftingName;
		case NO_SNEAKING: return LanguageMessages.guiNoSneakingName;
		case RANDOMIZE_BLOCK_DROPS: return LanguageMessages.guiRandomBlockDropsName;
		case RANDOMIZE_MOB_DROPS: return LanguageMessages.guiRandomMobDropsName;
		case RANDOMIZE_CRAFTING: return LanguageMessages.guiRandomCraftingName;
		case MLG: return LanguageMessages.guiRandomMLGName;
		default: return "";
		}
	}
	
	private String getCorrectChallengeStatus(boolean active) {
		return active ? LanguageMessages.enabled : LanguageMessages.disabled;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends GenericChallenge> T getChallenge(ChallengeType type) {
		return (T) activeChallenges.entrySet().stream()
			.filter(entry -> entry.getKey() == type)
			.map(Map.Entry::getValue)
			.findFirst().orElse(null);
	}
	
	public static boolean isActive(ChallengeType type) {
		GenericChallenge challenge = getChallenge(type);
		return challenge != null ? challenge.isActive() : false;
	}
	
	@Deprecated
	public static void removeFromActiveChallenges(ChallengeType type) {
		activeChallenges.remove(type);
	}
}
