package me.wand555.Challenge.DeathRun.Conversations.Prompts;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import me.wand555.Challenge.DeathRun.DeathRunSettingType;
import me.wand555.Challenge.DeathRun.Conversations.Prompts.extra.DeathRunGoal;
import me.wand555.Challenges.Challenges;

public class DistanceOrTimePrompt extends FixedSetPrompt {
	
	public DistanceOrTimePrompt() {
		super(Stream.of(DeathRunGoal.values())
				.map(DeathRunGoal::getString).collect(Collectors.toList())
				.toArray(new String[DeathRunGoal.values().length]));
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "Should a certain distance be reached or until a time limit is reached?";
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String answer) {
		
		if(answer.equalsIgnoreCase(DeathRunGoal.DISTANCE_GOAL.getString())) {
			context.getAllSessionData().put(DeathRunSettingType.DISTANCE_OR_TIME, DeathRunGoal.DISTANCE_GOAL.getString());
			return new DistancePrompt();
		}
		else if(answer.equalsIgnoreCase(DeathRunGoal.TIME_GOAL.getString())) {
			context.getAllSessionData().put(DeathRunSettingType.DISTANCE_OR_TIME, DeathRunGoal.TIME_GOAL.getString());
			return new TimePrompt();
		}
		else if(answer.equalsIgnoreCase(DeathRunGoal.BOTH_GOAL.getString())) {
			context.getAllSessionData().put(DeathRunSettingType.DISTANCE_OR_TIME, DeathRunGoal.BOTH_GOAL.getString());
			return new DistancePrompt(); //check previous answers and load TimePrompt after entering...
		}
		return new DistanceOrTimePrompt();
	}
}
