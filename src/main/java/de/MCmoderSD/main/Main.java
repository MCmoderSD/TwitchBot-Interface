package de.MCmoderSD.main;

import com.fasterxml.jackson.databind.JsonNode;
import de.MCmoderSD.UI.Frame;
import de.MCmoderSD.utilities.json.JsonUtility;

public class Main {

    // Bot Config
    public static final String BOT_CONFIG = "/config/BotConfig.json";

    // PSVM
    public static void main(String[] args) {

        // Custom Config
        boolean customConfig = args.length > 0;
        String path = customConfig ? args[0] : BOT_CONFIG;

        // Utilities
        JsonUtility jsonUtility = new JsonUtility();
        JsonNode jsonNode;
        try {
            jsonNode = jsonUtility.load(path, customConfig);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }
        // Frame
        new Frame(jsonNode);
    }
}