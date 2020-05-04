package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.DeathRunSettingType;
import me.wand555.Challenges.Challenges;

public class BorderPrompt extends NumericPrompt {

	@Override
	protected boolean isNumberValid(ConversationContext context, Number number) {
		boolean isNumeric = super.isNumberValid(context, number);
		if(!isNumeric) return false;
		if(number.intValue() > 5 && number.intValue() < 500) return true;
		else return false;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "What should the outer border limit (to the sides) be?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number number) {
		ConversationsHandler handler = ConversationsHandler.getConversationsHandler();
		Player player = (Player) context.getForWhom();
		handler.addAnswer(player, number.toString());
		context.getAllSessionData().put(DeathRunSettingType.BORDER, number.intValue());
		if(isNumberValid(context, number)) {
			return new OutOnDeathPrompt();
		}
		else {
			player.sendMessage("wrong again");
			return new BorderPrompt();
		}
	}

}
