package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.DeathRunHandler;
import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.Extension.EnhancedBooleanPrompt;
import me.wand555.Challenges.Challenges;

public class SwitchToDeathRunModePrompt extends EnhancedBooleanPrompt {
	
	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "Switch to DeathRun mode?";
	}

	@Override
	public Prompt acceptValidatedInput(ConversationContext context, boolean answer) {
		System.out.println(answer + " in final");
		DeathRunHandler.getDeathRunHandler().initializeDeathRun();
		return answer ? new DistanceOrTimePrompt()
				: END_OF_CONVERSATION;
	}

}
