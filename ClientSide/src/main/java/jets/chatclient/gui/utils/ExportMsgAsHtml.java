package jets.chatclient.gui.utils;



import jets.chatclient.gui.models.GpMessageModel;

import java.io.File;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

public class ExportMsgAsHtml{
    
    private void xmlTransformer(DOMResult res, String xsltPath, String outputHtmlPath) {
        try {
           
            // Use a Transformer for output
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            StreamSource stylesource = new StreamSource(xsltPath);
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

    public void exportGroupMessages(ArrayList<GpMessageModel> gpMessages){
        Class<GpChatJAXBHelper> toBeboundClass = jets.chatclient.gui.utils.GpChatJAXBHelper.class;
        String xsltPath = getClass().getResource("/xslt/GroupMessagesTransformer.xslt").getPath();
       // String xsltPath = "src\\main\\resources\\xslt\\GroupMessagesTransformer.xslt";
        String outputHtmlPath = "GpMessages.html";
        
        GpChatJAXBHelper gpmessages = new GpChatJAXBHelper();
        gpmessages.setGpMessage(gpMessages);

        DOMResult res = marshalingToDom(gpmessages, toBeboundClass);
        xmlTransformer(res, xsltPath, outputHtmlPath);
    }


    // public void exportP2PMessages(ArrayList<GpMessageModel> p2pMessages){
    //     Class<P2PChatJAXBHelper> toBeboundClass = jets.chatclient.gui.models.P2PChatJAXBHelper.class;
    //     String xsltPath = "src\\main\\resources\\P2Prules.xslt";
    //     String outputHtmlPath = "P2PMessages.html";
        
    //     P2PChatJAXBHelper p2pmessages = new P2PChatJAXBHelper();
    //     gpmessages.setP2PMessage(p2pMessages);

    //     DOMResult res = marshalingToDom(gpmessages, toBeboundClass);
    //     xmlTransformer(res, xsltPath, outputHtmlPath);
    // }
    
    
}
