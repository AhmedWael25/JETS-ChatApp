package jets.chatclient.gui.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement(name = "P2PMessages")
@XmlAccessorType (XmlAccessType.FIELD)
public class P2PChatJAXBHelper {

    @XmlElement(name = "message")
    private List<P2PMessageModelAdaptor>  p2PMessage = null;

    public List<P2PMessageModelAdaptor> getP2PMessage() {
        return p2PMessage;
    }

    public void setP2PMessage(List<P2PMessageModelAdaptor> p2PMessage) {
        this.p2PMessage = p2PMessage;
    }
}
