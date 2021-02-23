package jets.chatclient.gui.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement(name = "messages")
@XmlAccessorType (XmlAccessType.FIELD)
public class GpChatJAXBHelper {


    @XmlElement(name = "message")
    private List<GpMessageModelAdaptor>  gpMessage = null;

    public List<GpMessageModelAdaptor> getGpMessage() {
        return gpMessage;
    }

    public void setGpMessage(List<GpMessageModelAdaptor> gpMessage) {
        this.gpMessage = gpMessage;
    }
}
