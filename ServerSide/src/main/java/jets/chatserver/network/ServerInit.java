package jets.chatserver.network;

import commons.remotes.client.ClientInterface;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.*;
import jets.chatserver.database.daoImpl.*;

import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.gui.controllers.ServerAnnouncementsController;
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

    public static Map<String, ClientInterface> currentConnectedUsers ;


    public void  serverInit(){

         currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();

        try{
            Registry reg = LocateRegistry.createRegistry(3000);

            //TODO Refactor Into Fact./ServiceCreator
            //=========This is Where you will bind all your services>
            reg.rebind("AddFriendService",new AddFriendServiceImpl());
            reg.rebind("InvitationService",new InvitationServiceImpl());
            reg.rebind("RegisteringService",new RegisteringServiceImpl());
            reg.rebind("P2PChatService",new P2PChatServiceImpl());
            reg.rebind("GroupChatService",new GpChatServiceImpl());
            reg.rebind("SignInService",new SignInServiceImpl());
            reg.rebind("SignUpService", new SignUpServiceImpl());
            reg.rebind("UserProfileService", new UserProfileServiceImpl());
            reg.rebind("UpdateStatusService", new UpdateStatusService());
            reg.rebind("ClientLivenessService", new ClientLivenessService());
            reg.rebind("FileDownloadService", new FileDownloadServiceImpl());
            System.out.println("Server Up And Running");
            System.out.println("=================================== TESTING AREA ==================");


            FriendsDao f = FriendsDaoImpl.getFriendsDaoInstance();

        }catch (RemoteException | SQLException e){
            e.printStackTrace();
        }

    }
}
