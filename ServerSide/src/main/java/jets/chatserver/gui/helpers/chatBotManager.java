package jets.chatserver.gui.helpers;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.io.File;

public class chatBotManager {

    private String botsPath = "D:\\ComputerScience\\hpd\\chatBot\\src\\main\\resources\\com.chatbot";

//    private String botsPath = "D:\\ComputerScience\\chatProject\\master\\JETS-ChatApp\\ServerSide\\src\\main\\resources\\bots";

//    private String botsPath = getPath();


//    private String botsPath = getClass().getResource("bots").getPath();
    Bot bot = new Bot("bots/super",botsPath);
    Chat chat = new Chat(bot);

    public chatBotManager() {
    }

    public String sendMsgToBots(String msg) {

        String botResponse = chat.multisentenceRespond(msg);

        if (!botResponse.equals("I have no answer for that.")) {
            return botResponse;
        }
        return "I have no answer for that BS.";
    }

    private static String getPath()
    {

        File currDir =new File(".");
        String path = currDir.getAbsolutePath();
        String resourcePath = path + File.separator +"src" + File.separator +"main" + File.separator +"resources";

        return resourcePath;
    }

}
