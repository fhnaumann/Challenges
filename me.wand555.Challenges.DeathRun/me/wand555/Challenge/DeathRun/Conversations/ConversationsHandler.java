package me.wand555.Challenge.DeathRun.Conversations;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Conversion;
import org.bukkit.entity.Player;

import me.wand555.Challenge.DeathRun.Conversations.Prompts.SwitchToDeathRunModePrompt;
import me.wand555.Challenges.Challenges;

public class ConversationsHandler {

	private static ConversationsHandler conversationsHandler;
	private static final Challenges plugin = Challenges.getPlugin(Challenges.class);
	
	private Map<Player, LinkedList<String>> answers = new HashMap<>();
	
	private ConversationsHandler() {}
	
	public void addAnswer(Player player, String answer) {
		if(answers.containsKey(player)) {
			answers.get(player).add(answer);
		}
		else {
			answers.put(player, new LinkedList<String>(Collections.singleton(answer)));
		}
	}
	
	public LinkedList<String> getAnswers(Player player) {
		return answers.get(player);
	}
	
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
