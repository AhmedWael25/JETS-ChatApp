package jets.chatserver.network;

import commons.remotes.client.ClientInterface;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.dao.*;
import jets.chatserver.database.daoImpl.*;

import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.gui.controllers.ServerAnnouncementsController;
import jets.chatserver.network.rmi.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerInit   {

    public static Map<String, ClientInterface> currentConnectedUsers ;
    private Registry reg;

   private static AddFriendServiceImpl addFriendService;
   private static InvitationServiceImpl invitationService;
   private static RegisteringServiceImpl registeringService;
   private static P2PChatServiceImpl p2PChatService;
   private static GpChatServiceImpl gpChatService;
   private static SignInServiceImpl signInService;
   private static SignUpServiceImpl signUpService;
   private static UserProfileServiceImpl userProfileService;
   private static UpdateStatusService updateStatusService;
   private static ClientLivenessService clientLivenessService;
   private static FileDownloadServiceImpl fileDownloadService;




    public void  serverInit(){

         currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();
        try{
            reg = ModelsFactory.getInstance().getRegistry();
            bindServices(reg);

            System.out.println("=================================== Server Up And Running ==================");
            initUserStatus();
        }catch (RemoteException   e){
            e.printStackTrace();
        }
    }


    public   static void bindServices(Registry reg) throws RemoteException {


        addFriendService = new AddFriendServiceImpl();
        invitationService = new InvitationServiceImpl();
        registeringService = new RegisteringServiceImpl();
        gpChatService = new GpChatServiceImpl();
        signInService = new SignInServiceImpl();
        p2PChatService = new P2PChatServiceImpl();
        signUpService = new SignUpServiceImpl();
        userProfileService = new UserProfileServiceImpl();
        updateStatusService =  new UpdateStatusService();
        clientLivenessService = new ClientLivenessService();
        fileDownloadService = new FileDownloadServiceImpl();

        reg.rebind("AddFriendService",addFriendService);
        reg.rebind("InvitationService",invitationService);
        reg.rebind("RegisteringService",registeringService);
        reg.rebind("P2PChatService",p2PChatService);
        reg.rebind("GroupChatService",gpChatService);
        reg.rebind("SignInService",signInService);
        reg.rebind("SignUpService",signUpService);
        reg.rebind("UserProfileService",userProfileService);
        reg.rebind("UpdateStatusService",updateStatusService);
        reg.rebind("ClientLivenessService", clientLivenessService);
        reg.rebind("FileDownloadService", fileDownloadService);
    }

    public static void unbindServices() throws RemoteException, NotBoundException {
        Registry reg = ModelsFactory.getInstance().getRegistry();
        String[] services = reg.list();

        for(String service : services){
            reg.unbind(service);
        }
        UnicastRemoteObject.unexportObject(addFriendService,true);
        UnicastRemoteObject.unexportObject(invitationService,true);
        UnicastRemoteObject.unexportObject(registeringService,true);
        UnicastRemoteObject.unexportObject(p2PChatService,true);
        UnicastRemoteObject.unexportObject(gpChatService,true);
        UnicastRemoteObject.unexportObject(signInService,true);
        UnicastRemoteObject.unexportObject(signUpService,true);
        UnicastRemoteObject.unexportObject(userProfileService,true);
        UnicastRemoteObject.unexportObject(updateStatusService,true);
        UnicastRemoteObject.unexportObject(clientLivenessService,true);
        UnicastRemoteObject.unexportObject(fileDownloadService,true);
    }


    private static void initUserStatus(){

        UserDao userDao = null;
        try {
            userDao = UserDaoImpl.getUserDaoInstance();
            userDao.initAllUsersStatus();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
