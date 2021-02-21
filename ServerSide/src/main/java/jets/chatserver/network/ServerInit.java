package jets.chatserver.network;

import commons.remotes.client.ClientInterface;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.*;
import jets.chatserver.database.daoImpl.*;

import jets.chatserver.network.rmi.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerInit   {

    public void  serverInit(){

        Map<String, ClientInterface> currentConnectedUsers = new ConcurrentHashMap<>();

        try{
            Registry reg = LocateRegistry.createRegistry(3000);

            //TODO Refactor Into Fact./ServiceCreator
            //=========This is Where you will bind all your services>
            reg.rebind("AddFriendService",new AddFriendServiceImpl(currentConnectedUsers));
            reg.rebind("InvitationService",new InvitationServiceImpl(currentConnectedUsers));
            reg.rebind("RegisteringService",new RegisteringServiceImpl(currentConnectedUsers));
            reg.rebind("P2PChatService",new P2PChatServiceImpl(currentConnectedUsers));
            reg.rebind("GroupChatService",new GpChatServiceImpl(currentConnectedUsers));

            //registering users services
            reg.rebind("SignInService",new SignInServiceImpl());


            reg.rebind("SignUpService", new SignUpServiceImpl());

            //UserProfile Services:
            reg.rebind("UserProfileService", new UserProfileServiceImpl(currentConnectedUsers));

            System.out.println("Server Up And Running");

            //===================Testing AREA for all DAOS
            System.out.println("=================================== TESTING INVITAATIONS==================");
//            InvitationsDao invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
//
//
//            invitationsDao.addNewInvitation(inv);
//
//            List<DBInvitations> sender = invitationsDao.getAllUserReceivedInvitations("1");
//
//            for(DBInvitations userin : sender){
//                System.out.println(userin);
//            }

            FriendsDao f = FriendsDaoImpl.getFriendsDaoInstance();
            System.out.println(f.areFriends("1","2"));


            GpChatDao g = GpChatDaoImpl.getGpChatDaoInstance();
            List<String> xd = g.getAllParticipantsIdsByChatId(3);

            List<DBGpChat> gp =  g.getAllGpChatsOfUser("1");

            System.out.println(f.getAllContacts("1"));

//            System.out.println(g.getGpChatById(2));
//
//            System.out.println(g.doesUserHasGpChats("5"));
//            DBGpChat c = new DBGpChat();
//            c.setGpChatName("omk");
//            c.setGpChatAdminId("2");
//            c.setParticipantsId(new ArrayList<>(Arrays.asList(new String[]{"1","4"})));
//            c.setGpChatImg("asas");
//            GpChatDaoImpl.getGpChatDaoInstance().createGroupChat(c);

        }catch (RemoteException | SQLException e){
            e.printStackTrace();
        }

    }
}
