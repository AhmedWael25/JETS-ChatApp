package jets.chatclient.gui.utils;

import jakarta.xml.bind.annotation.*;
import jets.chatclient.gui.models.GpMessageModel;

import java.util.List;


@XmlRootElement(name = "gpmessages")
@XmlAccessorType (XmlAccessType.FIELD)
public class GpChatJAXBHelper {


    @XmlElement(name = "message")
    private List<GpMessageModel>  gpMessage = null;

    public List<GpMessageModel> getGpMessage() {
        return gpMessage;
    }

    public void setGpMessage(List<GpMessageModel> gpMessage) {
        this.gpMessage = gpMessage;
    }
}
