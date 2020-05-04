package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

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
		ConversationsHandler handler = ConversationsHandler.getConversationsHandler();
		handler.addAnswer((Player) context.getForWhom(), Boolean.toString(answer));
		System.out.println(answer + " in final");
		return answer ? new DistanceOrTimePrompt(new String[] {DistanceOrTimePrompt.DISTANCE_GOAL, 
				DistanceOrTimePrompt.TIME_GOAL, DistanceOrTimePrompt.BOTH_GOAL})
				: END_OF_CONVERSATION;
	}

}
