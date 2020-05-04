package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.DeathRunSettingType;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.Extension.EnhancedBooleanPrompt;
import me.wand555.Challenges.Challenges;

public class OutOnDeathPrompt extends EnhancedBooleanPrompt {

	@Override
	public String getPromptText(ConversationContext arg0) {
		return Challenges.PREFIX + ChatColor.GRAY + "player out on death?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean answer) {
		ConversationsHandler handler = ConversationsHandler.getConversationsHandler();
		handler.addAnswer((Player) context.getForWhom(), Boolean.toString(answer));
		context.getAllSessionData().put(DeathRunSettingType.OUT_ON_DEATH, answer);
		return new ConfirmPrompt();
	}

	
}
