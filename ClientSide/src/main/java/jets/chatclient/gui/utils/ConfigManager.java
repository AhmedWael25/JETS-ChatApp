package jets.chatclient.gui.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jets.chatclient.gui.models.User;
import jets.chatclient.gui.models.UserCredentials;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {

      JAXBContext jaxbContext;

    public void createConfigFile(UserCredentials userCredentials){
        try {
            jaxbContext = JAXBContext.newInstance(UserCredentials.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(userCredentials, new FileWriter("config.xml"));

        } catch (JAXBException | IOException e ) {
            e.printStackTrace();
        }

    }

    public UserCredentials readConfigFile(){
        Unmarshaller unmarshaller = null;
        UserCredentials userCredentials =null;
        try {
            jaxbContext = JAXBContext.newInstance(UserCredentials.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            userCredentials = (UserCredentials) unmarshaller.unmarshal(new FileReader("config.xml"));

        } catch (JAXBException | IOException e) {
            userCredentials = new UserCredentials("","");

        }
        return userCredentials;
    }

    public void saveUserId(String userId){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("userCredentials");

            Element userIdElement = document.createElement("userPhone");
            userIdElement.setTextContent(userId);
            root.appendChild(userIdElement);
            document.appendChild(root);

            DOMSource domSource = new DOMSource(document);
            // Result is xml streamresult
            File xmlfile = new File("config.xml");
            StreamResult streamResult = new StreamResult(xmlfile);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(domSource,streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

    }
    public boolean checkIfPasswordSaved() {
        File xmlfile = new File("config.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = null;
        try {
            parser = factory.newDocumentBuilder();

            Document document = parser.parse(xmlfile);
            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("encryptedPassword");
            if (list.getLength() != 0)
                return true;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();

        }
        return false;
    } }
