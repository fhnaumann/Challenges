package me.wand555.Challenges.API.Events.Violation;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.PunishType;
import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.OnBlockChallenge.OnBlockChallenge;

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
