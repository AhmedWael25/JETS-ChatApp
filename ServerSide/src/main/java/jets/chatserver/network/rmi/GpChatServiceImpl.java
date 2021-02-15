package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.database.dao.GpChatDao;
import jets.chatserver.database.daoImpl.GpChatDaoImpl;
import jets.chatserver.network.adapters.EntityDTOAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GpChatServiceImpl extends UnicastRemoteObject implements GpChatServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;
    GpChatDao gpChatDao = null;

    protected GpChatServiceImpl() throws RemoteException {
    }

    public GpChatServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public List<GpChatDto> fetchAllUserGpChats(String userId) throws RemoteException {

        List<GpChatDto> gpChatDtos = null;

        try{
            gpChatDao = GpChatDaoImpl.getGpChatDaoInstance();
            List<DBGpChat> dbGpChats = gpChatDao.getAllGpChatsOfUser(userId);

            gpChatDtos =  dbGpChats.parallelStream().map(EntityDTOAdapter::convertEntityToDto)
                    .collect(Collectors.toList());

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return gpChatDtos;
    }
}
