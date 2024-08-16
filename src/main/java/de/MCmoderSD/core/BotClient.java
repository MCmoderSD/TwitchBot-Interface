package de.MCmoderSD.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.eventsub.events.ChannelCheerEvent;
import com.github.twitch4j.eventsub.events.ChannelSubscriptionMessageEvent;

import de.MCmoderSD.UI.Frame;
import de.MCmoderSD.objects.TwitchMessageEvent;

import java.util.ArrayList;
import java.util.Set;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class BotClient {

    // Associations
    private final Frame frame;

    // Constants
    public static String botName;

    // Attributes
    private final TwitchClient client;
    private final TwitchChat chat;

    // Constructor
    public BotClient(Frame frame, JsonNode botConfig) {

        // Init Associations
        this.frame = frame;

        // Load Bot Config
        botName = botConfig.get("botName").asText().toLowerCase();

        // Init Bot Credential
        OAuth2Credential botCredential = new OAuth2Credential("twitch", botConfig.get("botToken").asText());

        // Init Client
        client = TwitchClientBuilder.builder()
                .withDefaultAuthToken(botCredential)
                .withChatAccount(botCredential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build();

        // Init Modules
        chat = client.getChat();
        EventManager eventManager = client.getEventManager();

        // Message Events
        eventManager.onEvent(ChannelMessageEvent.class, event -> frame.log(new TwitchMessageEvent(event)));
        eventManager.onEvent(ChannelCheerEvent.class, event -> frame.log(new TwitchMessageEvent(event)));
        eventManager.onEvent(ChannelSubscriptionMessageEvent.class, event -> frame.log(new TwitchMessageEvent(event)));
    }

    // Setter
    public void write(String channel, String message) {

        // Check Message
        if (message.isEmpty() || message.isBlank()) return;

        // Update Frame
        frame.log(USER, channel, botName, message);

        // Send Message
        chat.sendMessage(channel, message);
    }

    public void joinChannel(String channel) {
        if (chat.isChannelJoined(channel)) return;
        System.out.printf("%s%s %s Joined Channel: %s%s%s", BOLD, logTimestamp(), SYSTEM, channel.toLowerCase(), BREAK, UNBOLD);
        chat.joinChannel(channel);
    }

    public void leaveChannel(String channel) {
        if (!chat.isChannelJoined(channel)) return;
        System.out.printf("%s%s %s Leave Channel: %s%s%s", BOLD, logTimestamp(), SYSTEM, channel.toLowerCase(), BREAK, UNBOLD);
        chat.leaveChannel(channel);
    }

    public void leaveChannel(ArrayList<String> channels) {
        for (String channel : channels) leaveChannel(channel);
    }

    public void close() {
        client.close();
    }

    // Getter
    public Set<String> getChannels() {
        return chat.getChannels();
    }
}