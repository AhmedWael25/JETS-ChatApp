package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.InvitationDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.daoImpl.InvitationDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;

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
            //==================== TODO Refactor To An Adapter ============
            //TODO SO UGLY DONT FORGET TO REFACTOR =====
            invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
            List<DBInvitations> dbInvitations =  invitationsDao.getAllUserReceivedInvitations(userId);


           invitationDtos =  dbInvitations.parallelStream().map(DBinvitation -> {
               InvitationDto invitationDto = new InvitationDto();

               //Mapping Done Here
               invitationDto.setInvitationId(DBinvitation.getInvitationId());
               invitationDto.setInvitationContent(DBinvitation.getContent());
               invitationDto.setReceiverId(DBinvitation.getReceiverId());
               invitationDto.setSenderId(DBinvitation.getSenderId());
               invitationDto.setSenderName(DBinvitation.getSenderName());
               invitationDto.setReceiverName(DBinvitation.getReceiverName());

               try {
                   invitationDto.setSenderImg(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(DBinvitation.getSenderId()));
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }

               return invitationDto;
           }).collect(Collectors.toList());
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

            DBInvitations dbInv = new DBInvitations();
            dbInv.setSenderId(senderId);
            dbInv.setReceiverId(receiverId);
            dbInv.setSenderName(invitationDto.getSenderName());
            dbInv.setReceiverName(UserDaoImpl.getUserDaoInstance().getUserNameById(invitationDto.getReceiverId()));

            invitationsDao.addNewInvitation(dbInv);

            //Now it's Time To Call Back The User who was sent the Invitation
            //TODO Adapter First Thing Morning
            ClientInterface clientInterface = currentConnectedUsers.get(receiverId);
            invitationDto.setSenderImg(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(senderId));
            clientInterface.sendNewInvToUser(invitationDto);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
