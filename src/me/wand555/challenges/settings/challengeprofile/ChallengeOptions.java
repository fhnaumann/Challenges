package me.wand555.challenges.settings.challengeprofile;

import org.bukkit.entity.Player;

import me.wand555.challenges.settings.challengeprofile.types.GenericChallenge;

public interface ChallengeOptions {

	public <T extends GenericChallenge> void endChallenge(T rawType, ChallengeEndReason reason, Object[] extraData, Player... causer);
	public void restoreChallenge();
	public void resetChallenge();
}
