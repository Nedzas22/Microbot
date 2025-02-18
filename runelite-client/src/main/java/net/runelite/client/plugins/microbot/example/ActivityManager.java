package net.runelite.client.plugins.microbot.example;

import java.util.Random;

public class ActivityManager {
    private static final Random random = new Random();
    private Activity currentActivity;
    private long startTime;
    private long duration;  // Kiek laiko bus vykdoma veikla

    public ActivityManager() {
        chooseNewActivity();
}
    public void chooseNewActivity() {
        Activity[] activities = Activity.values();
        this.currentActivity = activities[random.nextInt(activities.length)];
        this.startTime = System.currentTimeMillis();
        this.duration = (random.nextInt(30) + 15) * 60 * 1000; // 15-45 min
    }

    public boolean shouldSwitchActivity() {
        return (System.currentTimeMillis() - startTime) > duration;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }
}
