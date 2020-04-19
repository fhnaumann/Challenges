package me.wand555.Challenges.Timer;

import java.time.Duration;

public class DateUtil {
	public static String formatDuration(long time) {
		Duration duration = Duration.ofSeconds(time);
	    long seconds = duration.getSeconds();
	    long absSeconds = Math.abs(seconds);
	    String positive = String.format(
	        "%d:%02d:%02d",
	        absSeconds / 3600,
	        (absSeconds % 3600) / 60,
	        absSeconds % 60);
	    return seconds < 0 ? "-" + positive : positive;
	}
	
	public static String formatNoHourDuration(long time) {
		Duration duration = Duration.ofSeconds(time);
	    long seconds = duration.getSeconds();
	    long absSeconds = Math.abs(seconds);
	    String positive = String.format(
	        "%02d:%02d",
	        (absSeconds % 3600) / 60,
	        absSeconds % 60);
	    return seconds < 0 ? "-" + positive : positive;
	}
}
