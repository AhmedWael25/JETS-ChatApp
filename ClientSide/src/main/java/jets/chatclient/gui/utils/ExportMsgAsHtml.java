package jets.chatclient.gui.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jets.chatclient.gui.models.GpMessageModel;
import jets.chatclient.gui.models.P2PMessageModel;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportMsgAsHtml{

    ExportMsgUtils exportMsgUtils = new ExportMsgUtils();

    
    private void xmlTransformer(DOMResult res, String xsltPath, String outputHtmlPath) {
        try {
           
            // Use a Transformer for output
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            InputStream inputStream = getClass().getResourceAsStream(xsltPath);
            StreamSource stylesource = new StreamSource(inputStream);
            Transformer transformer = transformFactory.newTransformer(stylesource);

            DOMSource source = new DOMSource((Document) res.getNode());
            // Acts as an holder for a transformation result, which 
            // may be XML, plain Text, HTML, or some other form of markup.
            StreamResult result = new StreamResult(new File(outputHtmlPath));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private DOMResult marshalingToDom(Object messageObject, Class<?> classToBeBound) {

       
        Marshaller jaxbMarshaller = null;

         // Acts as a holder for a transformation result tree in the form of a Document Object Model (DOM) tree.
         DOMResult res = new DOMResult();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classToBeBound);
            // The Marshaller class provides the client application the ability to convert a Java content tree back into XML data.
            jaxbMarshaller = jaxbContext.createMarshaller();
            // The marshalled XML data is formatted with linefeeds and indentation.
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshal the messages list in console
            jaxbMarshaller.marshal(messageObject, System.out);

            // Get the node that will contain the result DOM tree.
    

            //Marshal the messages list in file
            // Marshal the content tree rooted at jaxbElement into the specified javax.xml.transform.Result.
            jaxbMarshaller.marshal(messageObject, res);

            
        } catch (PropertyException e) {
            System.out.println("Property Exception  : " + e.getMessage());
            e.printStackTrace();
        } catch (JAXBException e) {
            System.out.println("JAXB Exception  : " + e.getMessage());
            e.printStackTrace();
        }

        return res;
    }


    public void exportP2PMessages(ArrayList<P2PMessageModel> peersMessages){
        // Generate Modified message Model.
        List<P2PMessageModelAdaptor> modifiedP2PMsgList = new ArrayList<P2PMessageModelAdaptor>();
        modifiedP2PMsgList =  exportMsgUtils.generateModifiedP2PChatList(peersMessages);

        Class<P2PChatJAXBHelper> toBeboundClass = jets.chatclient.gui.utils.P2PChatJAXBHelper.class;
        String xsltPath = "/xslt/P2PChatStyle.xslt";

        String outputHtmlPath = "P2PMessages.html";

        P2PChatJAXBHelper p2PMessages = new P2PChatJAXBHelper();
        p2PMessages.setP2PMessage(modifiedP2PMsgList);

        DOMResult res = marshalingToDom(p2PMessages, toBeboundClass);
        xmlTransformer(res, xsltPath, outputHtmlPath);
    }



    public void exportGroupMessages(ArrayList<GpMessageModel> gpMessages){
        // Generate Modified message Model.
        List<GpMessageModelAdaptor> modifiedGpMsgList = new ArrayList<GpMessageModelAdaptor>();
        modifiedGpMsgList =  exportMsgUtils.generateModifiedGpChatList(gpMessages);

        Class<GpChatJAXBHelper> toBeboundClass = jets.chatclient.gui.utils.GpChatJAXBHelper.class;
        String xsltPath = "/xslt/GpChatStyle.xslt";
        // String xsltPath = "src\\main\\resources\\xslt\\GroupMessagesTable.xslt";
        String outputHtmlPath = "GpMessages.html";

        GpChatJAXBHelper gpmessages = new GpChatJAXBHelper();
        gpmessages.setGpMessage(modifiedGpMsgList);

        DOMResult res = marshalingToDom(gpmessages, toBeboundClass);
        xmlTransformer(res, xsltPath, outputHtmlPath);
    }

}
