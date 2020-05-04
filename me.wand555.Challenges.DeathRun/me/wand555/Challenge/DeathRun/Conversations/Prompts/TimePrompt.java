package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.DeathRunSettingType;
import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.Timer.DateUtil;

public class TimePrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "Enter the time until the DeathRun is over.";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String answer) {
		ConversationsHandler handler = ConversationsHandler.getConversationsHandler();
		Player player = (Player) context.getForWhom();
		handler.addAnswer(player, answer);
		long enteredTime = DateUtil.getSecondsFromFormattedDuration(answer);
		context.getAllSessionData().put(DeathRunSettingType.TIME, enteredTime);
		if(enteredTime > 0) {
			player.sendMessage("Correct");
			return END_OF_CONVERSATION;
		}
		else {
			player.sendMessage("wrong time");
			return new TimePrompt();
		}
	}

}
