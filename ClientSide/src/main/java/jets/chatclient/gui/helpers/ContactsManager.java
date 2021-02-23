package jets.chatclient.gui.helpers;

import commons.sharedmodels.ContactDto;
import commons.utils.ImageEncoderDecoder;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.ContactModel;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ContactsManager {

    private Map<String , ContactModel> contacts = new ConcurrentHashMap<>();


    public void addContacts(List<ContactModel> contacts){
        contacts.forEach(this::addContact);
    }
    public  void addContact(ContactModel contact){
        contacts.put(contact.getContactId(),contact);
    }

    public Circle getBindableContactImg(String contactId){
        ContactModel model = contacts.get(contactId);
        return model.getContactAvatar();
    }

    public Circle getBindableContactStatus(String contactId){
        ContactModel model = contacts.get(contactId);
        return model.getBindableStatus();
    }

    public  void updateContactImg(String contactId, String imgEncoded){
        ContactModel model = contacts.get(contactId);
        if (model != null) {
            Image img = ImageEncoderDecoder.getDecodedImage(imgEncoded);
            model.setContactImage(img);
        }
    }
    public  void updateContactStatus(String contactId, Integer status){
        ContactModel model = contacts.get(contactId);
        System.out.println("in contact status id0 + "+contactId + model);
        if(model != null){
            model.setContactStatus(status);
        }
    }

    public  void updateContactName(String contactId, String name){
        ContactModel model = contacts.get(contactId);
        if (model != null){
            model.setContactName(name);
        }
    }

    public  void addNewContact(ContactDto dto){
        ContactModel model = DTOObjAdapter.convertDtoToObj(dto);
        contacts.put(model.getContactId(), model);

        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        ContactsController contactsController= controllersGetter.getContactsController();
        contactsController.addContactToList(model);

    }

    public String getFriendName(String friendId){
        ContactModel model = contacts.get(friendId);
        String name = new String();
        if (model != null){
            name = model.getContactName();
        }
        return  name;
    }

    public  Image getFriendImage(String friendId){
        ContactModel model = contacts.get(friendId);
        Image img = null;
        if(model != null){
            img = model.getContactImage();
        }
        return  img;
    }



}
