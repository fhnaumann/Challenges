package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

import me.wand555.Challenge.DeathRun.DeathRunDirection;
import me.wand555.Challenge.DeathRun.DeathRunSettingType;
import me.wand555.challenges.start.Challenges;
public class DirectionPrompt extends FixedSetPrompt {

	public DirectionPrompt() {
		super(Stream.of(DeathRunDirection.values())
				.map(DeathRunDirection::getString).collect(Collectors.toList())
				.toArray(new String[DeathRunDirection.values().length]));
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "In welche Richtung soll gerannt werden?";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String answer) {
		context.getAllSessionData().put(DeathRunSettingType.DIRECTION, answer);
		return new BorderPrompt();
	}

}
