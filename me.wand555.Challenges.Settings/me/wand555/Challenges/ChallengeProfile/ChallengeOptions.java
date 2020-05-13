package me.wand555.Challenges.ChallengeProfile;

import org.bukkit.entity.Player;

import me.wand555.Challenges.ChallengeProfile.ChallengeTypes.GenericChallenge;

public interface ChallengeOptions {

	public <T extends GenericChallenge> void endChallenge(T rawType, ChallengeEndReason reason, Object[] extraData, Player... causer);
	public void restoreChallenge();
	public void resetChallenge();
}
