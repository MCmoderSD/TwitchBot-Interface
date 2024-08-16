# TwitchBot Interface

This is a simple interface for a Twitch bot that uses the [Twitch4J](https://twitch4j.github.io/) library. 
The interface is written in Java and uses Java Swing for the GUI.
You can use the interface to use a bot token to connect to a Twitch channel and send messages to the chat.

## Usage

### 1. Installation 
You need to have [Java 21](https://www.oracle.com/de/java/technologies/downloads/#java21) installed on your machine to run the interface.
Then you can download the latest release of the interface from the [releases page](

### 2. Configuration

You need to obtain a bot token and put them into a ```config.json``` file. <br> 
You can obtain your Twitch token from [here](https://twitchapps.com/tmi/).<br>

The ```config.json``` file should look like this:
```json
{
  "botName": "YOUR_BOT_NAME",
  "botToken": "YOUR_BOT_TOKEN"
}
```

### 3. Running the Interface

You can run the interface by double-clicking the .jar file or by running the following command in the terminal:
```shell
java -jar TwitchBotInterface.jar "PATH_TO_CONFIG.JSON"
```

### 4. Using the Interface
In the bottom left corner you can enter the channel you want to connect to. <br>
The "Send" button will connect the bot to the channel. <br>
You can then enter a message in the text field and send it to the chat by pressing the "Send" button.