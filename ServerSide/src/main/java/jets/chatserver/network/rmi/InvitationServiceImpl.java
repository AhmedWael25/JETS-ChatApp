package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.InvitationDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.daoImpl.InvitationDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityObjAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvitationServiceImpl extends UnicastRemoteObject implements InvitationServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;
    InvitationsDao invitationsDao = null;

    protected InvitationServiceImpl() throws RemoteException {
        super();
    }


    public InvitationServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public List<InvitationDto> getAllUserInvitations(String userId) throws RemoteException {


        List<InvitationDto> invitationDtos = null;
        try {
            invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
            List<DBInvitations> dbInvitations =  invitationsDao.getAllUserReceivedInvitations(userId);

           invitationDtos =  dbInvitations.parallelStream().map(EntityObjAdapter::convertEntityToDto)
                   .collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitationDtos;
    }

    @Override
    public boolean deleteInvitation(InvitationDto invitationDto) throws RemoteException {
        System.out.println("Invitation Deleted sa7");
        return false;
    }

    @Override
    public boolean sendInvitation(InvitationDto invitationDto) throws RemoteException {

        String senderId = invitationDto.getSenderId();
        String receiverId =invitationDto.getReceiverId();

        try {

            boolean isInviteExists = invitationsDao.isInviteExists(senderId,receiverId);
            if(isInviteExists) return  false;


            //Convert DTO To Entity
            DBInvitations dbInv = EntityObjAdapter.convertDtoToEntity(invitationDto);
            invitationsDao.addNewInvitation(dbInv);

            //Now it's Time To Call Back The User who was sent the Invitation
            //TODO Adapter First Thing Morning
            ClientInterface clientInterface = currentConnectedUsers.get(receiverId);
            //Attaching the SenderImg with DTO
            invitationDto.setSenderImg(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(senderId));
            clientInterface.sendNewInvToUser(invitationDto);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
