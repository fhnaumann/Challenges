package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.DeathRunSettingType;
import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.Timer.DateUtil;

public class TimePrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "Enter the time until the DeathRun is over.";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String answer) {
		Player player = (Player) context.getForWhom();
		long enteredTime = DateUtil.getSecondsFromFormattedDuration(answer);
		context.getAllSessionData().put(DeathRunSettingType.TIME, enteredTime);
		if(enteredTime > 0) {
			return new DirectionPrompt();
		}
		else {
			player.sendMessage("wrong time");
			return this;
		}
	}

}
