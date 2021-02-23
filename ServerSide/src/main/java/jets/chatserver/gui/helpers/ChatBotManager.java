package jets.chatserver.gui.helpers;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.io.File;

public class ChatBotManager {


    private String botsPath = getPath();

    // init chat bot
    Bot bot = new Bot("super",botsPath);
    Chat chat = new Chat(bot);

    public ChatBotManager() {}

    public String sendMsgToBots(String msg) {

        String botResponse = chat.multisentenceRespond(msg);

        if (!botResponse.equals("I have no answer for that.")) {
            return botResponse;
        }
        return "Sorry .. I have no answer.";
    }

    private static String getPath() {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String resourcePath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";

        return resourcePath;
    }

}
