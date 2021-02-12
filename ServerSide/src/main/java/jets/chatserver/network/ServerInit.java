package jets.chatserver.network;

import commons.remotes.client.ClientInterface;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.InvitationDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.rmi.AddFriendServiceImpl;
import jets.chatserver.network.rmi.InvitationServiceImpl;
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

            //=========This is Where you will bind all your services>
            reg.rebind("AddFriendService",new AddFriendServiceImpl(currentConnectedUsers));
            reg.rebind("InvitationService",new InvitationServiceImpl(currentConnectedUsers));
            reg.rebind("RegisteringService",new RegisteringServiceImpl(currentConnectedUsers));


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
            System.out.println(f.areFriends("1","7"));

        }catch (RemoteException | SQLException e){
            e.printStackTrace();
        }

    }
}
