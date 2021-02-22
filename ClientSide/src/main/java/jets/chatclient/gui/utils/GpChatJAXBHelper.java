package jets.chatclient.gui.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement(name = "gpmessages")
@XmlAccessorType (XmlAccessType.FIELD)
public class GpChatJAXBHelper {


    @XmlElement(name = "message")
    private List<ModifiedGpMessageModel>  gpMessage = null;

    public List<ModifiedGpMessageModel> getGpMessage() {
        return gpMessage;
    }

    public void setGpMessage(List<ModifiedGpMessageModel> gpMessage) {
        this.gpMessage = gpMessage;
    }
}
