package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.DeathRunSettingType;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.Extension.EnhancedBooleanPrompt;
import me.wand555.Challenges.Challenges;

public class ConfirmPrompt extends EnhancedBooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + 
				"Your settings: \nDistance or Time Goal: "
				+ context.getSessionData(DeathRunSettingType.DISTANCE_OR_TIME)
				+ "\nDistance: " + context.getSessionData(DeathRunSettingType.DISTANCE)
				+ "\nBorder: " + context.getSessionData(DeathRunSettingType.BORDER)
				+ "\nOut On Death: " + context.getSessionData(DeathRunSettingType.OUT_ON_DEATH);
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean answer) {
		// TODO Auto-generated method stub
		return null;
	}

}
