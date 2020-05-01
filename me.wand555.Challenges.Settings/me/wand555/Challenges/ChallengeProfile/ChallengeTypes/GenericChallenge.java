package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.Challenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.SharedHealthChallenge.SharedHealthChallenge;
import me.wand555.Challenges.Config.LanguageMessages;

public abstract class GenericChallenge implements Challenge, ItemDisplayCreator {

	protected static final Challenges PLUGIN = Challenges.getPlugin(Challenges.class);
	
	protected static EnumMap<ChallengeType, GenericChallenge> activeChallenges = new EnumMap<>(ChallengeType.class);
	
	protected boolean active;
	protected ChallengeType type;
	
	protected GenericChallenge(ChallengeType type) {
		this.type = type;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void setAround() {
		active = !active;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public ChallengeType getChallengeType() {
		return type;
	}
	
	public abstract ItemStack getDisplayItem();
	
	public void sendTitleChangeMessage(Collection<Player> players) {
		String title;
		String subtitle = "";
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
		System.out.println(subtitle);
		for(Player p : players) {
			p.sendTitle(title, subtitle, 10, 60, 10);
		}
	}
	
	public String getFittingPunishmentMessage2(PunishType punishment) {
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
		case ON_BLOCK: return LanguageMessages.guiOnBlockName;
		case ITEM_LIMIT_GLOBAL: return LanguageMessages.guiItemCollectionLimitGlobalName;
		case GROUND_IS_LAVA: return LanguageMessages.guiItemFloorIsLavaName;
		case BE_AT_HEIGHT: return "";
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
	
	public static void clearAllChallenges() {
		activeChallenges.clear();
	}
}
