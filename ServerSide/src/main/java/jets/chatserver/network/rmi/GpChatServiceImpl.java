package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.GpChatUserDto;
import commons.sharedmodels.GpMessageDto;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.database.dao.GpChatDao;
import jets.chatserver.database.daoImpl.GpChatDaoImpl;
import jets.chatserver.network.adapters.EntityDTOAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public boolean createGroupChat(GpChatUserDto chatDto) throws RemoteException {

        try {
            gpChatDao = GpChatDaoImpl.getGpChatDaoInstance();
            int newChatId =gpChatDao.createGroupChat(EntityDTOAdapter.convertDtoToEntity(chatDto));

            //Now Call Back All Clients To Update Their UI with New GP Chat;

            List<ClientInterface> clientInterfaces = new ArrayList<>();
            //To Get List Of Clients Connected
            for(String userIds : chatDto.getGpUserIds()){
                if(currentConnectedUsers.get(userIds) != null){
                    clientInterfaces.add(currentConnectedUsers.get(userIds));
                }
            }
            DBGpChat dbGpChat = GpChatDaoImpl.getGpChatDaoInstance().getGpChatById(newChatId);
            GpChatDto gpChatDto = EntityDTOAdapter.convertEntityToDto(dbGpChat);

            for (ClientInterface ci : clientInterfaces){
                ci.sendNewGpChatToUsers(gpChatDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  true;
    }

    @Override
    public boolean sendMessage(GpMessageDto gpMessageDto) throws RemoteException {

        List<String> participants = null;
        int chatId = gpMessageDto.getChatId();

        try {

            //Get All Participants
            participants = gpChatDao.getAllParticipantsIdsByChatId(chatId);
            //Prep to call Back only online users.
            //Remove Sender From Receivers List
            for (String part : participants){
                if(part.equals(gpMessageDto.getSenderId())) continue;
                ClientInterface ci = currentConnectedUsers.get(part);
                if(ci != null) {
                    ci.sendNewGpMsgToUsers(gpMessageDto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
