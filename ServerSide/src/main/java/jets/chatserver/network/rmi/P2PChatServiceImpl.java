package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.P2PChatServiceInt;
import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.database.dao.P2PChatDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.P2PChatDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ChatBotManager;
import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.network.adapters.EntityDTOAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class P2PChatServiceImpl extends UnicastRemoteObject implements P2PChatServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;
    P2PChatDao chatDao = null;
    UserDao userDao = null;

    protected P2PChatServiceImpl() throws RemoteException {
    }
    public P2PChatServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;

        try {
            userDao = UserDaoImpl.getUserDaoInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<P2PChatDto> fetchAllUserP2PChats(String userId) throws RemoteException {

        List<P2PChatDto> p2pChatsDtos = null;
        try {
            P2PChatDao chatDao = P2PChatDaoImpl.getP2PChatDaoInstance();
            List<DBP2PChat> DBp2pChats = chatDao.fetchAllChatsByUserId(userId);

            p2pChatsDtos =  DBp2pChats.parallelStream().map(EntityDTOAdapter::convertEntityToDto)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p2pChatsDtos;
    }

    @Override
    public boolean sendMessage(P2PMessageDto msgDto) throws RemoteException {

        ClientInterface receiverInterface = currentConnectedUsers.get(msgDto.getReceiverId());

        try {
            // check user availability
            if (receiverInterface != null) {

                receiverInterface.sendNewP2PMessageToUser(msgDto);

                //Check if the user is busy to call chatBot
                if (userDao.getUserStatus(msgDto.getReceiverId()) == 2) {

                    ClientInterface senderInterface = currentConnectedUsers.get(msgDto.getSenderId());

                    // init chatBot
                    ChatBotManager botManager = ModelsFactory.getInstance().getChatBotManager();
                    String botMsg = botManager.sendMsgToBots(msgDto.getMsgBody());
                    msgDto.setMsgBody("Bot: " + botMsg);

                    //swap
                    String senderId = msgDto.getSenderId();
                    String receiverId = msgDto.getReceiverId();
                    msgDto.setReceiverId(senderId);
                    msgDto.setSenderId(receiverId);

                    new Thread().sleep(3000);
                    receiverInterface.sendNewP2PMessageToUser(msgDto);
                    senderInterface.sendNewP2PMessageToUser(msgDto);
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
