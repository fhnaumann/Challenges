package me.wand555.Challenge.DeathRun.Conversations;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.Prompts.SwitchToDeathRunModePrompt;
import me.wand555.Challenges.Challenges;

public class ConversationsHandler {

	private static ConversationsHandler conversationsHandler;
	private static final Challenges plugin = Challenges.getPlugin(Challenges.class);
	
	private ConversationsHandler() {}
	
	public void startConversation(Player player) {
		ConversationFactory cf = new ConversationFactory(plugin);
		Conversation conv = cf.withFirstPrompt(new SwitchToDeathRunModePrompt())
				.withLocalEcho(true)
				.withEscapeSequence("exit")
				.withEscapeSequence("quit")
				.withEscapeSequence("end")
				.withTimeout(120)
				.withModality(true)
				.buildConversation(player);
		conv.begin();
	}
	
	public static ConversationsHandler getConversationsHandler() {
		if(conversationsHandler == null) conversationsHandler = new ConversationsHandler();
		return conversationsHandler;
	}
}
