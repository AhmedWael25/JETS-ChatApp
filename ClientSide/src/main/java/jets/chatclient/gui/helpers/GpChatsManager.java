package jets.chatclient.gui.helpers;

import commons.sharedmodels.GpMessageDto;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.controllers.GroupChatController;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.GpMessageModel;
import jets.chatclient.gui.models.ParticipantModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GpChatsManager {


    private Map<Integer, GpChatModel> chatsMap = new ConcurrentHashMap<>();
    private Map<Integer, List<GpMessageModel>> chatMsgs = new ConcurrentHashMap<>();
    private Integer activeChat ;

    public void updateParticipantImg(String id,Image img){

        chatsMap.entrySet().forEach(entrySet -> {

            entrySet.getValue().getGpParticipants()
                    .stream()
                    .forEach(participantModel -> {

                        if (participantModel.getParticipantId().equals(id)){
                            participantModel.setParticipantImage(img);
                        }
                     });
        });

    }

    public Circle getParticipantImg(Integer chatId, String participantId){
        GpChatModel chat = chatsMap.get(chatId);
        Circle c = null;
        for (ParticipantModel part : chat.getGpParticipants()){
            if(part.getParticipantId().equals(participantId)){
                c =   part.getParticipantImg();
                break;
            }
        }
        return  c;
    }

    public GpChatModel getChat(Integer chatId){
        return  chatsMap.get(chatId);
    }

    public  void addMsg(GpMessageModel msg){
        List<GpMessageModel>msgList = chatMsgs.getOrDefault(msg.getChatId(), new ArrayList<>());
        msgList.add(msg);
        chatMsgs.put(msg.getChatId(), msgList);
    }

    public  void addGpChat(GpChatModel gpChatModel){
        chatsMap.put(gpChatModel.getGpChatId(),gpChatModel);
    }

     public  void addGpChat(List<GpChatModel> gpChatModelList){
        gpChatModelList.forEach(gpChatModel -> {
            addGpChat(gpChatModel);
        });
     }

    public Integer getActiveChat() {
        return activeChat;
    }

    public  List<GpMessageModel> getActiveChatMsgList(){
        return chatMsgs.getOrDefault(activeChat,new ArrayList<GpMessageModel>());
    }

    public  List<GpMessageModel> getMsgList(Integer chatId){
        return chatMsgs.getOrDefault(chatId,new ArrayList<GpMessageModel>());
    }

    public void setActiveChat(Integer activeChat) {
        this.activeChat = activeChat;
    }

    public  void addMsg(GpMessageDto gpMessageDto){
        GpMessageModel msg = DTOObjAdapter.convertDtoToObj(gpMessageDto);
        addMsg(msg);
        if(activeChat.equals(msg.getChatId())){
            ControllersGetter controllersGetter = ControllersGetter.getInstance();
            GroupChatController groupChatController = controllersGetter.getGpChatController();
            groupChatController.addMsgToUi(msg);
        }
    }
}
