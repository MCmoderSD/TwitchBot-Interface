package de.MCmoderSD.UI;

import com.fasterxml.jackson.databind.JsonNode;
import de.MCmoderSD.core.BotClient;
import de.MCmoderSD.objects.TwitchMessageEvent;
import de.MCmoderSD.utilities.image.ImageReader;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static de.MCmoderSD.utilities.other.Calculate.*;

public class Frame extends JFrame {

    // Attributes
    private final LogPanel logPanel;

    // Constructor
    public Frame(JsonNode botConfig) {

        // Init Frame
        super("TwitchBot-Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Bot Client
        // Associations
        BotClient botClient = new BotClient(this, botConfig);


        // Set Colors
        setBackground(DARK);
        setForeground(PURPLE);

        // Set Layout
        setLayout(new BorderLayout());

        // Icon
        ImageReader imageReader = new ImageReader();
        setIconImage(imageReader.read("/images/icon.png"));

        // Variables
        var multiplier = 0.75;
        var rawHeight = getToolkit().getScreenSize().getHeight() * multiplier;
        var rawWidth = rawHeight * ((double) 4 / 3);
        var height = Math.toIntExact(Math.round(rawHeight));
        var width = Math.toIntExact(Math.round(rawWidth));
        Dimension size = new Dimension(width, height);

        // Add Panel
        MenuPanel menuPanel = new MenuPanel(this, size, botClient);
        logPanel = new LogPanel(this, size, menuPanel);


        // Set Visible
        pack();
        setLocation(centerJFrame(this));
        setVisible(true);
        requestFocusInWindow();
    }

    // Setter
    public void log(TwitchMessageEvent event) {
        logPanel.appendText(event.getType(), event.getChannel(), event.getUser(), event.getMessage());
    }

    public void log(String type, String channel, String user, String message) {
        logPanel.appendText(type, channel, user, message);
    }
}