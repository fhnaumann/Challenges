package me.wand555.Challenge.DeathRun.Conversations.Prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.ConversationsHandler;
import me.wand555.Challenge.DeathRun.Conversations.DeathRunSettingType;
import me.wand555.Challenges.Challenges;

public class DistanceOrTimePrompt extends FixedSetPrompt {

	public static final String DISTANCE_GOAL = "distance"; 
	public static final String TIME_GOAL = "time";
	public static final String BOTH_GOAL = "both";
	
	public DistanceOrTimePrompt(String... fixedSet) {
		super(fixedSet);
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return Challenges.PREFIX + ChatColor.GRAY + "Should a certain distance be reached or until a time limit is reached?";
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String answer) {
		ConversationsHandler handler = ConversationsHandler.getConversationsHandler();
		handler.addAnswer((Player) context.getForWhom(), answer);
		context.getAllSessionData().put(DeathRunSettingType.DISTANCE_OR_TIME, answer);
		if(isInputValid(context, answer)) {
			if(answer.equalsIgnoreCase(DISTANCE_GOAL)) return new DistancePrompt();
			else if(answer.equalsIgnoreCase(TIME_GOAL)) return new TimePrompt();
			else if(answer.equalsIgnoreCase(BOTH_GOAL)) return new DistancePrompt(); //check previous answers and load TimePrompt after entering...
		}
		else {
			context.getForWhom().sendRawMessage("Wrong");		
		}
		return new DistanceOrTimePrompt(new String[] {DISTANCE_GOAL, TIME_GOAL, BOTH_GOAL});
	}
}
