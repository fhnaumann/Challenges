package me.wand555.Challenges.ChallengeProfile;

import org.bukkit.entity.Player;

public interface ChallengeOptions {

	public void endChallenge(Player causer, ChallengeEndReason reason);
	public void restoreChallenge();
	public void resetChallenge();
}
