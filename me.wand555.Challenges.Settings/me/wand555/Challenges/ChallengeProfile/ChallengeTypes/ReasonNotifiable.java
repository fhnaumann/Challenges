package me.wand555.Challenges.ChallengeProfile.ChallengeTypes;

import org.bukkit.entity.Player;

import me.wand555.Challenges.Config.LanguageMessages;

public interface ReasonNotifiable {

	default String createPassedMessage(ChallengeType cause) {
		switch(cause) {
		case MLG:
			return LanguageMessages.passedMLG;
		case ON_BLOCK:
			return LanguageMessages.passedOnBlock;
		default: return "unknown";
		}
	}
	
	/**
	 * This method will break the plugin if the ChallengeType is something else than the type with that this method was called with.
	 * @param cause
	 * @param punishment
	 * @param causers
	 * 
	 * @return null if challenge is not active otherwise message to be displayed in chat.
	 */
	default String createReasonMessage(ChallengeType cause, PunishType punishment, Player... causers) {
		//if(punishment == PunishType.NOTHING) return null;
		
		for(Player causer : causers) {
			GenericChallenge genericChallenge = GenericChallenge.getChallenge(cause);
			if(!genericChallenge.isActive()) return null;	
			switch(cause) {
			case NO_DAMAGE:
				return LanguageMessages.violationNoDamage.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));
			case NO_BLOCK_PLACING:
				return LanguageMessages.violationBlockPlacing.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));
			case NO_BLOCK_BREAKING:
				return LanguageMessages.violationBlockBreaking.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));
			case NO_CRAFTING:
				return LanguageMessages.violationCrafting.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));
			case NO_SNEAKING:
				return LanguageMessages.violationSneaking.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));	
			case MLG:
				return LanguageMessages.violationMLG.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));	
			case ON_BLOCK:
				return LanguageMessages.violationOnBlock.replace("[PLAYER]", causer.getName())
						.replace("[PUNISHMENT]", getFittingPunishmentMessage(punishment));
			default:
				return causer.getName() + " has failed a challenge (unknown reason) (" + getFittingPunishmentMessage(punishment) + ")!";
			}		
		}
		return "unkown error... Please contact Finex#1120 on discord!";
	}
	
	/**
	 * DO NOT CALL THIS METHOD FROM OUTSIDE. In Java 9 this would be a private interface method (sad java 8 noises).
	 * 
	 * @return punishment formatted to be displayed.
	 */
	default String getFittingPunishmentMessage(PunishType punishment) {
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
}
