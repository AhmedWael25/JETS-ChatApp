package jets.chatclient.gui.helpers;

import commons.sharedmodels.P2PMessageDto;
import commons.utils.ImageEncoderDecoder;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class P2PChatManager {

    private Integer activeP2PChat;
    private Map<Integer, P2PChatModel> chatsMap = new ConcurrentHashMap<>();
    private Map<Integer, List<P2PMessageModel>> messages = new ConcurrentHashMap<>();


    public Integer getActiveP2PChat() {
        return activeP2PChat;
    }

    public void addP2PChat(P2PChatModel p2pChatModel){
        chatsMap.put(p2pChatModel.getChatId(), p2pChatModel);
    }

    public  void addP2PChat(List<P2PChatModel> p2pChatModelList){
        p2pChatModelList.forEach( p2pChatModel -> {
            addP2PChat(p2pChatModel);
        });
    }

    public void updateFriendStatus(String id,Integer status){
        chatsMap.forEach((integer, p2PChatModel) -> {
            if(p2PChatModel.getFriendId().equals(id)){
                p2PChatModel.setFriendStatus(status);
            }
        });
    }

    public void updateFriendImg(String id,String img){
        Image newImg = ImageEncoderDecoder.getDecodedImage(img);
        chatsMap.forEach((integer, p2PChatModel) -> {
            if(p2PChatModel.getFriendId().equals(id)){
                p2PChatModel.setFriendImg(newImg);
            }
        });
    }

    public void setActiveP2PChat(Integer activeChat) {
        this.activeP2PChat = activeChat;
    }

    public P2PChatModel getP2PChat(Integer chatId){

        return  chatsMap.get(chatId);
    }

    public  void addMsg(P2PMessageModel msg){
        List<P2PMessageModel> msgList = messages.getOrDefault(msg.getChatId(), new ArrayList<>());
        msgList.add(msg);
        messages.put(msg.getChatId(), msgList);
        System.out.println(messages);
        System.out.println("IN ADD MSSG1" + msgList);
    }

    public void addMsg(P2PMessageDto p2pMsgDto){
        P2PMessageModel msg = DTOObjAdapter.convertDtoToObj(p2pMsgDto);
        addMsg(msg);
        if(activeP2PChat.equals(msg.getChatId())){
            ControllersGetter controllersGetter = ControllersGetter.getInstance();
            P2PChatController p2PChatController = controllersGetter.getP2PChatController();
            p2PChatController.addMsgToUi(msg);
        }

    }

    public  List<P2PMessageModel> getMsgList(Integer chatId){
        return messages.getOrDefault(chatId,new ArrayList<P2PMessageModel>());
    }

    public String getParticipantId(Integer i){
        return chatsMap.get(activeP2PChat).getFriendId();
    }



    public  List<P2PMessageModel> getActiveChatMsgList(){
        return messages.getOrDefault(activeP2PChat,new ArrayList<P2PMessageModel>());
    }

    public Circle getFriendImg(Integer chatId) {

        P2PChatModel chat = chatsMap.get(chatId);
        return chat.getAvatar();
    }

    public  Circle getFriendStatus(Integer chatId){
        P2PChatModel chat = chatsMap.get(chatId);
        return  chat.getStatus();
    }

}
