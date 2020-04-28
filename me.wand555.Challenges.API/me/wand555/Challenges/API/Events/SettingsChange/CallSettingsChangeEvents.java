package me.wand555.Challenges.API.Events.SettingsChange;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import me.wand555.Challenges.Challenges;
import me.wand555.Challenges.ChallengeProfile.ChallengeProfile;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.CustomHealthChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.Punishable;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.RandomChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.ItemCollectionLimitChallenge.ItemCollectionLimitGlobalChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.MLGChallenge.MLGTimer;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockTimer;
import me.wand555.GUI.GUI;
import me.wand555.GUI.GUIType;

public interface CallSettingsChangeEvents {

	default <T extends GenericChallenge> void callSettingsChangeNormalEventAndActUpon(T rawType, Player p, GUI gui) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		ChallengeStatusSwitchEvent<T> switchEvent = new ChallengeStatusSwitchEvent<T>(rawType, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			genericChallenge.setAround();	
			genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());	
			
			
			if(rawType.getClass().equals(MLGChallenge.class)) {
				MLGChallenge mlgChallenge = (MLGChallenge) rawType;
				if(mlgChallenge.getTimer() != null) {
					mlgChallenge.getTimer().cancel();
					mlgChallenge.setTimer(null);
					mlgChallenge.setEarliest(0);
					mlgChallenge.setLatest(0);
				}
			}
			else if(rawType.getClass().equals(OnBlockChallenge.class)) {
				OnBlockChallenge onBlockChallenge = (OnBlockChallenge) rawType;
				if(onBlockChallenge.getTimer() != null) {
					onBlockChallenge.removePlayerFromBossBar(p);
					onBlockChallenge.getTimer().cancel();
					onBlockChallenge.setTimer(null);
					onBlockChallenge.setEarliestToShow(0);
					onBlockChallenge.setLatestToShow(0);
					onBlockChallenge.setEarliestOnBlock(0);
					onBlockChallenge.setLatestOnBlock(0);
					onBlockChallenge.setBossBarMessageShown(null);
					onBlockChallenge.setToStayOn(null);
				}
			}
			else if(rawType.getClass().equals(ItemCollectionLimitGlobalChallenge.class)) {
				ItemCollectionLimitGlobalChallenge iCLGChallenge = (ItemCollectionLimitGlobalChallenge) rawType;
				iCLGChallenge.setCurrentAmount(0);
				iCLGChallenge.setActive(false);
				iCLGChallenge.setLimit(0);
				iCLGChallenge.getUniqueItems().clear();
			}
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
		}
		gui.createGUI(p, GUIType.OVERVIEW);	
	}
	
	default <T extends GenericChallenge & Punishable> void callSettingsChangePunishableEventAndActUpon(T rawType, PunishType type, Player p, GUI gui, Challenges plugin) {
		GenericChallenge genericChallenge = (GenericChallenge) rawType;
		
		if(rawType.getClass().equals(MLGChallenge.class)) {
			MLGChallenge mlgChallenge = (MLGChallenge) rawType;			
			long timeToMLG = ThreadLocalRandom.current()
					.nextLong(mlgChallenge.getEarliest()*20, (mlgChallenge.getLatest()+1)*20);
			MLGChallengeStatusSwitchEvent switchEvent = new MLGChallengeStatusSwitchEvent(mlgChallenge, timeToMLG, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				mlgChallenge.setPunishType(switchEvent.getPunishType());
				mlgChallenge.setAround();
				mlgChallenge.setEarliest(switchEvent.getEarliest());
				mlgChallenge.setLatest(switchEvent.getLatest());
				mlgChallenge.setHeight(switchEvent.getHeight());
				mlgChallenge.setTimer(new MLGTimer(plugin, switchEvent.getTimeToNextMLG(), switchEvent.getTimeToNextMLG()));
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else if(rawType.getClass().equals(OnBlockChallenge.class)) {
			OnBlockChallenge onBlockChallenge = (OnBlockChallenge) rawType;
			long timeToBlockShown = ThreadLocalRandom.current()
					.nextLong(onBlockChallenge.getEarliestToShow(), (onBlockChallenge.getLatestToShow()+1));
			ForceBlockChallengeStatusSwitchEvent switchEvent = new ForceBlockChallengeStatusSwitchEvent(onBlockChallenge, timeToBlockShown, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				onBlockChallenge.setAround();
				onBlockChallenge.setPunishType(switchEvent.getPunishType());
				onBlockChallenge.setEarliestToShow(switchEvent.getEarliestToShow());
				onBlockChallenge.setLatestToShow(switchEvent.getLatestToShow());
				onBlockChallenge.setEarliestOnBlock(switchEvent.getEarliestOnBlock());
				onBlockChallenge.setLatestOnBlock(switchEvent.getLatestOnBlock());
				onBlockChallenge.setTimer(new OnBlockTimer(plugin, onBlockChallenge, switchEvent.getTimeToBlockShown(), switchEvent.getTimeToBlockShown(), true));
				ChallengeProfile.getInstance().getParticipantsAsPlayers().forEach(player -> onBlockChallenge.addPlayerToBossBar(player));
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		else {
			PunishableChallengeStatusSwitchEvent<T> switchEvent = new PunishableChallengeStatusSwitchEvent<T>(rawType, type, p);
			Bukkit.getServer().getPluginManager().callEvent(switchEvent);
			if(!switchEvent.isCancelled()) {
				Punishable punishable = (Punishable) rawType;	
				punishable.setPunishType(switchEvent.getPunishType());
				genericChallenge.setAround();
				genericChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
			}
			else {
				if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());			
			}
			if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		}
		gui.createGUI(p, GUIType.PUNISHMENT.getGoBack());
	}
	
	default void callSettingsChangeCustomHealthEventAndActUpon(CustomHealthChallenge customHealthChallenge, int amount, Player p, GUI gui) {
		CustomHealthChallengeStatusSwitchEvent switchEvent = new CustomHealthChallengeStatusSwitchEvent(customHealthChallenge, amount, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			customHealthChallenge.setAround();
			customHealthChallenge.setAmount(switchEvent.getCustomHP());
			customHealthChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());		
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW);
	}
	
	default void callSettingsChangeRandomChallengeEventAndActUpon(RandomChallenge randomChallenge, HashMap<Material, Material> randomizedMapped, Player p, GUI gui) {
		RandomChallengeStatusSwitchEvent<RandomChallenge> switchEvent = new RandomChallengeStatusSwitchEvent<RandomChallenge>(randomChallenge, randomizedMapped, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			randomChallenge.setAround();
			randomChallenge.setRandomizedMapped(Maps.newLinkedHashMap(switchEvent.getRandomizedMapped()));
			randomChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
			if(!randomChallenge.isActive()) RandomChallenge.clearRandomizationIfCase();
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());	
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW);
	}
	
	default void callSettingsChangeItemCollectionLimitGlobalChallengeEventAndActUpon(ItemCollectionLimitGlobalChallenge iCLGChallenge, int limit, Player p, GUI gui) {
		ItemCollectionLimitGlobalChallengeStatusSwitchEvent switchEvent = new ItemCollectionLimitGlobalChallengeStatusSwitchEvent(iCLGChallenge, limit, p);
		Bukkit.getServer().getPluginManager().callEvent(switchEvent);
		if(!switchEvent.isCancelled()) {
			iCLGChallenge.setAround();
			iCLGChallenge.setLimit(switchEvent.getLimit());
			iCLGChallenge.sendTitleChangeMessage(ChallengeProfile.getInstance().getParticipantsAsPlayers());
			/*if(!iCLGChallenge.isActive()) {
				iCLGChallenge.setCurrentAmount(0);
				iCLGChallenge.setLimit(0);
				iCLGChallenge.getUniqueItems().clear();
			}*/
		}
		else {
			if(switchEvent.hasDeniedMessage()) p.sendMessage(switchEvent.getDeniedMessage());
		}
		if(switchEvent.hasOverrideMessage()) p.sendMessage(switchEvent.getOverrideMessage());
		gui.createGUI(p, GUIType.OVERVIEW);
	}
}
