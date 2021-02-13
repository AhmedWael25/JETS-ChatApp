package jets.chatserver.network;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.P2PChatDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.dao.P2PChatDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.InvitationDaoImpl;
import jets.chatserver.database.daoImpl.P2PChatDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityDTOAdapter;
import jets.chatserver.network.rmi.AddFriendServiceImpl;
import jets.chatserver.network.rmi.InvitationServiceImpl;
import jets.chatserver.network.rmi.P2PChatServiceImpl;
import jets.chatserver.network.rmi.RegisteringServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
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


            System.out.println("Server Up And Running");

            //===================Testing AREA for all DAOS
            System.out.println("=================================== TESTING INVITAATIONS==================");
//            InvitationsDao invitationsDao = InvitationDaoImpl.getInvitationDaoInstance();
//
//
////            invitationsDao.addNewInvitation(inv);
//
//            List<DBInvitations> sender = invitationsDao.getAllUserReceivedInvitations("1");
//
//            for(DBInvitations userin : sender){
//                System.out.println(userin);
//            }

            FriendsDao f = FriendsDaoImpl.getFriendsDaoInstance();
            System.out.println(f.areFriends("1","2"));

            P2PChatDao p = P2PChatDaoImpl.getP2PChatDaoInstance();
            List<DBP2PChat> as = p.fetchAllChatsByUserId("1");
            for(DBP2PChat userin : as){
                System.out.println(userin);
                System.out.println(EntityDTOAdapter.convertEntityToDto(userin));
            }

            System.out.println(p.isUserHasChat("1"));

            System.out.println(p.addChat("1","7"));

        }catch (RemoteException | SQLException e){
            e.printStackTrace();
        }

    }
}
