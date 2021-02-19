package jets.chatclient.gui.helpers;

import commons.sharedmodels.P2PMessageDto;
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

        System.out.println(chatsMap);
        System.out.println(chatsMap.get(i));
        System.out.println(chatsMap.get(i).getFriendId());

        return chatsMap.get(activeP2PChat).getFriendId();
    }

    public  List<P2PMessageModel> getActiveChatMsgList(){
        return messages.getOrDefault(activeP2PChat,new ArrayList<P2PMessageModel>());
    }


}
