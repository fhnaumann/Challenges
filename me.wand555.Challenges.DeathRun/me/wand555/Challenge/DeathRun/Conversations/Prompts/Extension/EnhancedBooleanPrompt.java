package me.wand555.Challenge.DeathRun.Conversations.Prompts.Extension;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.conversations.BooleanPrompt;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public abstract class EnhancedBooleanPrompt extends BooleanPrompt {

	private String[] trueAccepted = new String[] {"true", "on", "yes", "y", "1", "right", "correct", "valid", "j", "ja"};
	private String[] falseAccepted = new String[] {"false", "off", "no", "n", "0", "wrong", "incorrect", "invalid", "nein"};
	
	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
        return ArrayUtils.contains(trueAccepted, input.toLowerCase()) || ArrayUtils.contains(falseAccepted, input.toLowerCase());
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input) {
		if(ArrayUtils.contains(trueAccepted, input)) input = "true";
		return acceptValidatedInput(context, Boolean.valueOf(input));
	}
	
	
}
