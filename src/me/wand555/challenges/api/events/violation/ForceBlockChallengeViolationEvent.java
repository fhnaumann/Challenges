package me.wand555.challenges.api.events.violation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.wand555.challenges.settings.challengeprofile.types.PunishType;
import me.wand555.challenges.settings.challengeprofile.types.onblock.OnBlockChallenge;

public class ForceBlockChallengeViolationEvent extends ChallengeViolationPunishmentEvent<OnBlockChallenge> {

	private Material toStayOn;
	
	public ForceBlockChallengeViolationEvent(OnBlockChallenge challenge, PunishType punishType, String logMessage, Material toStayOn,
			Player... players) {
		super(challenge, punishType, logMessage, players);
		this.toStayOn = toStayOn;
	}

	/**
	 * @return the toStayOn
	 */
	public Material getToStayOn() {
		return toStayOn;
	}

}
