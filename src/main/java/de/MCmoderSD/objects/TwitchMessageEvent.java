package de.MCmoderSD.objects;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.eventsub.events.ChannelCheerEvent;
import com.github.twitch4j.eventsub.events.ChannelSubscriptionMessageEvent;
import de.MCmoderSD.utilities.other.Calculate;

import java.sql.Timestamp;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class TwitchMessageEvent {

    // Constants
    private final Timestamp timestamp;

    // Attributes
    private final String channel;
    private final String user;

    // Message
    private final String message;

    // Additional Information
    private final Integer subMonths;
    private final Integer subStreak;
    private final String subTier;
    private final Integer bits;

    // Message Event
    public TwitchMessageEvent(ChannelMessageEvent event) {

        // Set Timestamp
        timestamp = Calculate.getTimestamp();

        // Get Names
        channel = trimMessage(event.getChannel().getName());
        user = trimMessage(event.getUser().getName());

        // Get Message
        message = trimMessage(event.getMessage());

        // Set Additional Information
        subMonths = event.getSubscriberMonths();
        subStreak = null;
        subTier = event.getSubscriptionTier() == 0 ? "NONE" : "TIER" + event.getSubscriptionTier();
        bits = null;
    }

    // Cheer Event
    public TwitchMessageEvent(ChannelCheerEvent event) {

        // Set Timestamp
        timestamp = Calculate.getTimestamp();

        // Get Names
        channel = trimMessage(event.getBroadcasterUserName());
        user = trimMessage(event.getUserName());

        // Get Message
        message = trimMessage(event.getMessage());

        // Get Additional Information
        subMonths = null;
        subStreak = null;
        subTier = null;
        bits = event.getBits();
    }

    // Sub Event
    public TwitchMessageEvent(ChannelSubscriptionMessageEvent event) {

        // Set Timestamp
        timestamp = Calculate.getTimestamp();

        // Get Names
        channel = trimMessage(event.getBroadcasterUserName());
        user = trimMessage(event.getUserName());

        // Get Message
        message = trimMessage(event.getMessage().getText());

        // Get Additional Information
        subMonths = event.getCumulativeMonths();
        subStreak = event.getStreakMonths();
        subTier = event.getTier().ordinalName().toUpperCase();
        bits = null;
    }

    public void logToConsole() {
        System.out.println(getLog());
    }

    // Getter
    public String getType() {
        if (isSub()) return SUBSCRIBE;
        if (isCheer()) return CHEER;
        return MESSAGE;
    }

    public String getChannel() {
        return channel;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    // Checks
    public boolean isSub() {
        return subMonths != null && subStreak != null && subTier != null;
    }

    public boolean isCheer() {
        return bits != null;
    }

    public String getFormattedTimestamp() {
        return "[" + new java.text.SimpleDateFormat("dd-MM-yyyy|HH:mm:ss").format(timestamp) + "]";
    }

    public String getLog() {
        return getFormattedTimestamp() + " " + getType() + " <" + getChannel() + "> " + getUser() + ": " + getMessage();
    }
}