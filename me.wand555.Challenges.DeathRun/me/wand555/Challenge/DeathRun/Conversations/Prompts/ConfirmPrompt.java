package me.wand555.Challenge.DeathRun.Conversations.Prompts;


import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

import me.wand555.Challenge.DeathRun.DeathRunDirection;
import me.wand555.Challenge.DeathRun.DeathRunHandler;
import me.wand555.Challenge.DeathRun.DeathRunSettingType;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.Extension.EnhancedBooleanPrompt;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.extra.DeathRunGoal;
import me.wand555.challenges.settings.challengeprofile.ChallengeMode;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.start.Challenges;

public class ConfirmPrompt extends EnhancedBooleanPrompt {

	private DeathRunGoal goal;
	private int maxDistance;
	private int maxTime;
	private DeathRunDirection direction;
	private int border;
	private boolean outOnDeath;
	
	@Override
	public String getPromptText(ConversationContext context) {
		goal = DeathRunGoal.matchDeathRunGoal(context.getSessionData(DeathRunSettingType.DISTANCE_OR_TIME).toString());
		if(goal == DeathRunGoal.DISTANCE_GOAL) maxDistance = Integer.valueOf(context.getSessionData(DeathRunSettingType.DISTANCE).toString());
		if(goal == DeathRunGoal.TIME_GOAL) maxTime = Integer.valueOf(context.getSessionData(DeathRunSettingType.TIME).toString());
		direction = DeathRunDirection.matchDeathRunDirection(context.getSessionData(DeathRunSettingType.DIRECTION).toString());
		border = Integer.valueOf(context.getSessionData(DeathRunSettingType.BORDER).toString());
		outOnDeath = Boolean.valueOf(context.getSessionData(DeathRunSettingType.OUT_ON_DEATH).toString());
		
		if(goal == DeathRunGoal.DISTANCE_GOAL) {
			return Challenges.PREFIX + ChatColor.GRAY + 
					"Your settings: \nDistance or Time Goal: "
					+ goal.getString()
					+ "\nDistance: " + maxDistance
					+ "\nDirection: " + direction
					+ "\nBorder: " + border
					+ "\nOut On Death: " + outOnDeath;
		}
		else if(goal == DeathRunGoal.TIME_GOAL) {
			return Challenges.PREFIX + ChatColor.GRAY + 
					"Your settings: \nDistance or Time Goal: "
					+ goal.getString()
					+ "\nTime: " + maxTime
					+ "\nDirection: " + direction
					+ "\nBorder: " + border
					+ "\nOut On Death: " + outOnDeath;
		}
		else {
			return Challenges.PREFIX + ChatColor.GRAY + 
					"Your settings: \nDistance or Time Goal: "
					+ goal.getString()
					+ "\nDistance: " + maxDistance
					+ "\nTime: " + maxTime
					+ "\nDirection: " + direction
					+ "\nBorder: " + border
					+ "\nOut On Death: " + outOnDeath;
		}	
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean answer) {
		context.getAllSessionData().put(DeathRunSettingType.CONFIRM, answer);
		DeathRunHandler handler = DeathRunHandler.getDeathRunHandler();
		handler.setEndingType(goal);
		handler.setMaxDistance(maxDistance);
		handler.setMaxTime(maxTime);
		handler.setDirection(direction);
		handler.setBorder(border);
		handler.setOutOnDeath(outOnDeath);
		ChallengeProfile.getInstance().switchTo(ChallengeMode.DEATHRUN);
		handler.initializeDeathRun();
		return END_OF_CONVERSATION;
	}

	
}
