package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.P2PChatServiceInt;
import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.database.dao.P2PChatDao;
import jets.chatserver.database.daoImpl.P2PChatDaoImpl;
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

    protected P2PChatServiceImpl() throws RemoteException {
    }
    public P2PChatServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;

    }

    @Override
    public List<P2PChatDto> fetchAllUserP2PChats(String userId) throws RemoteException {

        List<P2PChatDto> p2pChatsDtos = null;
        try {
            P2PChatDao  chatDao = P2PChatDaoImpl.getP2PChatDaoInstance();
            List<DBP2PChat> DBp2pChats = chatDao.fetchAllChatsByUserId(userId);

            p2pChatsDtos =  DBp2pChats.parallelStream().map(EntityDTOAdapter::convertEntityToDto)
                    .collect(Collectors.toList());

            System.out.println(p2pChatsDtos);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p2pChatsDtos;
    }

    @Override
    public boolean sendMessage(P2PMessageDto msgDto) throws RemoteException {

        String receiverId = msgDto.getReceiverId();

        ClientInterface clientInterface = currentConnectedUsers.get(receiverId);

        if(clientInterface != null) {
            clientInterface.sendNewP2PMessageToUser(msgDto);
        }

        return true;
    }

}
