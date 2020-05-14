package me.wand555.challenges.settings.timer;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.challenges.settings.challengeprofile.ChallengeEndReason;
import me.wand555.challenges.settings.challengeprofile.ChallengeProfile;
import me.wand555.challenges.settings.config.LanguageMessages;
import me.wand555.challenges.start.Challenges;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SecondTimer extends BukkitRunnable {

	private TimerOrder order;
	private TimerMessage messageType;
	private long time;
	
	public SecondTimer(Challenges plugin, TimerMessage messageType) {
		this.messageType = messageType;
		this.runTaskTimer(plugin, 0L, 20L);
	}
	
	public SecondTimer(Challenges plugin, long time) {
		this.time = time;
		this.messageType = time == 0 ? TimerMessage.START_TIMER : TimerMessage.TIMER_PAUSED;
		this.runTaskTimer(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		ChallengeProfile cProfile = ChallengeProfile.getInstance();
		if(cProfile.getParticipants().isEmpty()) return;
		
		if(cProfile.hasStarted && !cProfile.isPaused) {
			if(this.order == TimerOrder.DESC && time <= 0) {
				cProfile.endChallenge(null, ChallengeEndReason.NO_TIME_LEFT, null);
				return;
			}
			this.time = order == TimerOrder.ASC ? this.time+1 : this.time-1;
			if(messageType == TimerMessage.DEATHRUN_RUNNING) {
				
			}
			else {
				String displayTime = DateUtil.formatDuration(time);
				TextComponent component = new TextComponent(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + displayTime);
				cProfile.getParticipants()
					.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component));
			}
			
		}
		else {
			TextComponent component;
			switch(messageType) {
			case START_TIMER:			
				component = new TextComponent(LanguageMessages.timerMessageStart);
				break;
			case TIMER_PAUSED:
				component = new TextComponent(LanguageMessages.timerMessagePause.replace("[TIME]", DateUtil.formatDuration(getTime())));
				break;
			case TIMER_FINISHED:
				component = new TextComponent(LanguageMessages.timerMessageFinished.replace("[TIME]", DateUtil.formatDuration(getTime())));
				break;
			default:
				component = new TextComponent(ChatColor.RED + "ERROR! PLEASE RESTART THE SERVER!");
				break;
			}
			//System.out.println(component);
			cProfile.getParticipants()
				.forEach(p -> {
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
					p.getWorld().spawnParticle(Particle.PORTAL, p.getLocation().add(0, 1, 0), 50);
				});
		}
	}
	
	public long getTime() {
		return this.time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public TimerMessage getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(TimerMessage messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the order
	 */
	public TimerOrder getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(TimerOrder order) {
		this.order = order;
	}
}
