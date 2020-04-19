package me.wand555.Challenges.ChallengeProfile;

import org.bukkit.entity.Player;

public interface ChallengeOptions {

	public void endChallenge(ChallengeEndReason reason, Player... causer);
	public void restoreChallenge();
	public void resetChallenge();
}
