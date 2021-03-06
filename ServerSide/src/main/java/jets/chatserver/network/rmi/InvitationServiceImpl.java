package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.InvitationDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.daoImpl.InvitationDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.network.adapters.EntityDTOAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvitationServiceImpl extends UnicastRemoteObject implements InvitationServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;
    InvitationsDao invitationsDao = null;

    public InvitationServiceImpl() throws RemoteException {
        super();
        currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();
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

           invitationDtos =  dbInvitations.parallelStream().map(EntityDTOAdapter::convertEntityToDto)
                   .collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitationDtos;
    }

    @Override
    public boolean deleteInvitation(InvitationDto invitationDto) throws RemoteException {

        String senderId = invitationDto.getSenderId();
        String receiverId = invitationDto.getReceiverId();

        try {
            invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
            invitationsDao.deleteInvitation(senderId,receiverId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //CallBack To Remove The Invitation Card From The List

        return false;
    }

    @Override
    public boolean deleteInvitation(String senderId, String receiverId) throws RemoteException {

        try {
            invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
            invitationsDao.deleteInvitation(senderId,receiverId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //CallBack To Remove The Invitation Card From The List

        return false;
    }

    @Override
    public boolean sendInvitation(InvitationDto invitationDto) throws RemoteException {

        String senderId = invitationDto.getSenderId();
        String receiverId =invitationDto.getReceiverId();

        try {
            //TODO if Friends Donot Send Invitation.

            boolean isInviteExists = invitationsDao.isInviteExists(senderId,receiverId);
            if(isInviteExists) return  false;


            //Convert DTO To Entity
            DBInvitations dbInv = EntityDTOAdapter.convertDtoToEntity(invitationDto);
            invitationsDao.addNewInvitation(dbInv);

            //Now it's Time To Call Back The User who was sent the Invitation
            //TODO Adapter First Thing Morning
            ClientInterface clientInterface = currentConnectedUsers.get(receiverId);
            //Attaching the SenderImg with DTO-
            if(clientInterface != null){
                invitationDto.setSenderImg(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(senderId));
                clientInterface.sendNewInvToUser(invitationDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isInviteExists(InvitationDto invitationDto) throws RemoteException {
        String senderId = invitationDto.getSenderId();
        String receiverId =invitationDto.getReceiverId();
        boolean isInviteExists = false;
        try {
             isInviteExists = invitationsDao.isInviteExists(senderId,receiverId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  isInviteExists;
    }

    @Override
    public boolean isUserExist(String userId) throws RemoteException {

        boolean isUserExist = false;
        try {
            isUserExist = UserDaoImpl.getUserDaoInstance().isUserExist(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  isUserExist;
    }

    @Override
    public boolean isAlreadyInvited(InvitationDto invDto) throws RemoteException {

        boolean isAlreadyInvited = false;

        try {
            //Switch Sender And Receiver
            isAlreadyInvited = InvitationDaoImpl.getInvitationDaoInstance().isInviteExists(invDto.getReceiverId(),invDto.getSenderId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAlreadyInvited;
    }
}
