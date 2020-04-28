package me.wand555.Challenges.API.Events.Violation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.wand555.Challenges.API.Events.Beaten.ChallengeBeatenEvent;
import me.wand555.Challenges.API.Events.Beaten.ItemCollectionLimitGlobalChallengeBeatenEvent;
import me.wand555.Challenges.API.Events.Violation.ChallengeEnd.ChallengeEndEvent;
import me.wand555.Challenges.API.Events.Violation.ChallengeEnd.ItemCollectionLimitGlobalChallengeEndEvent;
import me.wand555.Challenges.API.Events.Violation.ChallengeEnd.TimerHitZeroEvent;
import me.wand555.Challenges.ChallengeProfile.ChallengeEndReason;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ChallengeType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.Timer.TimerMessage;

public interface CallViolationEvent {

	default <T extends GenericChallenge & Punishable> void callViolationPunishmentEventAndActUpon(T rawType, String logMessage, Player... players) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		Punishable punishable = (Punishable) rawType;
		ChallengeViolationPunishmentEvent<T> violationEvent = new ChallengeViolationPunishmentEvent<T>(rawType, punishable.getPunishType(), logMessage, players);
		Bukkit.getServer().getPluginManager().callEvent(violationEvent);
		if(!violationEvent.isCancelled()) {
			punishable.enforcePunishment(punishable.getPunishType(), cProfile.getParticipantsAsPlayers(), players);
			cProfile.sendMessageToAllParticipants(violationEvent.getLogMessage());
		}
		else {
			if(violationEvent.hasDeniedMessage()) cProfile.sendMessageToAllParticipants(violationEvent.getDeniedMessage());
		}
		
	}
	
	default void callForceBlockEventAndActUpon(OnBlockChallenge onBlockChallenge, String logMessage, Material toStayOn, Player... players) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		ForceBlockChallengeViolationEvent violationEvent = new ForceBlockChallengeViolationEvent(onBlockChallenge, onBlockChallenge.getPunishType(), logMessage, toStayOn, players);
		Bukkit.getServer().getPluginManager().callEvent(violationEvent);
		if(!violationEvent.isCancelled()) {
			onBlockChallenge.enforcePunishment(onBlockChallenge.getPunishType(), cProfile.getParticipantsAsPlayers(), players);
			cProfile.sendMessageToAllParticipants(violationEvent.getLogMessage());
		}
		else {
			if(violationEvent.hasDeniedMessage()) cProfile.sendMessageToAllParticipants(violationEvent.getDeniedMessage());
		}
	}
	
	
	default <T extends GenericChallenge> void callEndChallengeEventAndActUpon(T rawType, ChallengeEndReason endReason, String message, Player... players) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		ChallengeEndEvent<T> endEvent = new ChallengeEndEvent<T>(rawType, endReason, message, players);
		Bukkit.getServer().getPluginManager().callEvent(endEvent);
		if(!endEvent.isCancelled()) {
			cProfile.setPaused();
			cProfile.setDone();
			cProfile.getSecondTimer().setMessageType(TimerMessage.TIMER_FINISHED);
			for(Player p : cProfile.getParticipantsAsPlayers()) {
				p.sendMessage(endEvent.getEndMessage());
				p.setGameMode(GameMode.SPECTATOR);
			}	
		}
		else {
			if(endEvent.hasDeniedMessage())	for(Player p : players) p.sendMessage(endEvent.getDeniedMessage());
		}
	}
	
	default void callEndChallengeItemCollectionLimitGlobalChallengeEventAndActUpon(ItemCollectionLimitGlobalChallenge iCLGChallenge, ChallengeEndReason endReason,
			String message, LinkedHashMap<UUID, Integer> sorted, Player player) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		ItemCollectionLimitGlobalChallengeEndEvent endEvent = new ItemCollectionLimitGlobalChallengeEndEvent(iCLGChallenge, endReason, message, sorted, player);
		Bukkit.getServer().getPluginManager().callEvent(endEvent);
		if(!endEvent.isCancelled()) {
			cProfile.setPaused();
			cProfile.setDone();
			cProfile.getSecondTimer().setMessageType(TimerMessage.TIMER_FINISHED);
			for(Player p : cProfile.getParticipantsAsPlayers()) {
				p.sendMessage(endEvent.getEndMessage());
				p.setGameMode(GameMode.SPECTATOR);
				if(!sorted.isEmpty()) {
					int place = 0;
					for(Map.Entry<UUID, Integer> entry : sorted.entrySet()) {
						String toSend = ChatColor.GRAY + "" + (++place) + ": " + ChatColor.DARK_GREEN
								+ Bukkit.getOfflinePlayer(entry.getKey()).getName() + " " + ChatColor.YELLOW + entry.getValue() + ChatColor.GRAY + " Items!";
						p.sendMessage(toSend);
					}
				}
			}	
		}
		else {
			if(endEvent.hasDeniedMessage()) player.sendMessage(endEvent.getDeniedMessage());
		}
	}
	
	default void callEndChallengeNoTimeLeft(String message) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		TimerHitZeroEvent zeroEvent = new TimerHitZeroEvent(message);
		Bukkit.getServer().getPluginManager().callEvent(zeroEvent);
		for(Player p : cProfile.getParticipantsAsPlayers()) {
			p.sendMessage(zeroEvent.getEndMessage());
			p.setGameMode(GameMode.SPECTATOR);
		}
	}
	
	default void callChallengeBeatenEvent(List<ChallengeType> activeChallenges, String message) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		ChallengeBeatenEvent beatenEvent = new ChallengeBeatenEvent(activeChallenges, message);
		Bukkit.getServer().getPluginManager().callEvent(beatenEvent);
		for(Player p : cProfile.getParticipantsAsPlayers()) {
			p.sendMessage(beatenEvent.getEndMessage());
			p.setGameMode(GameMode.SPECTATOR);
		}
	}
	
	default void callItemCollectionLimitGlobalChallengeBeatenEvent(List<ChallengeType> activeChallenges, String message, LinkedHashMap<UUID, Integer> sorted) {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		ItemCollectionLimitGlobalChallengeBeatenEvent beatenEvent = new ItemCollectionLimitGlobalChallengeBeatenEvent(activeChallenges, message, sorted);
		Bukkit.getServer().getPluginManager().callEvent(beatenEvent);
		for(Player p : cProfile.getParticipantsAsPlayers()) {
			p.sendMessage(beatenEvent.getEndMessage());
			if(!sorted.isEmpty()) {
				int place = 0;
				for(Map.Entry<UUID, Integer> entry : sorted.entrySet()) {
					String toSend = ChatColor.GRAY + "" + (++place) + ": " + ChatColor.DARK_GREEN
							+ Bukkit.getOfflinePlayer(entry.getKey()).getName() + " " + ChatColor.YELLOW + entry.getValue() + ChatColor.GRAY + " Items!";
					p.sendMessage(toSend);
				}
			}
			p.setGameMode(GameMode.SPECTATOR);
		}
	}
}
