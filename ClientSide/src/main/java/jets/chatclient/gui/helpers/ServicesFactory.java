package jets.chatclient.gui.helpers;

import commons.remotes.server.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServicesFactory {

    private  static ServicesFactory servicesFactory = null;
    private Registry reg;

    private ServicesFactory() throws RemoteException {
        reg = LocateRegistry.getRegistry("localhost",3000);
    };

    public static ServicesFactory getInstance() throws RemoteException {
        if (servicesFactory == null){
            return  new ServicesFactory();
        }
        return servicesFactory;
    }

    public  void reInitServiceFactory() throws RemoteException {
        reg = LocateRegistry.getRegistry("localhost",3000);
    }

    public RegisteringClientInt getRegisterClientService() throws RemoteException, NotBoundException {
        return (RegisteringClientInt) reg.lookup("RegisteringService");
    }
    public InvitationServiceInt getInvitationService() throws  RemoteException, NotBoundException{
        return (InvitationServiceInt) reg.lookup("InvitationService");
    }
    public AddFriendServiceInt getAddFriendService() throws  RemoteException, NotBoundException{
        return (AddFriendServiceInt) reg.lookup("AddFriendService");
    }
    public P2PChatServiceInt getP2PChatService() throws  RemoteException, NotBoundException{
        return (P2PChatServiceInt) reg.lookup("P2PChatService");
    }
    public GpChatServiceInt getGpChatService() throws RemoteException, NotBoundException{
        return (GpChatServiceInt) reg.lookup("GroupChatService");
    }
    public UpdateStatusServiceInt getUpdateService() throws  RemoteException , NotBoundException{
        return (UpdateStatusServiceInt) reg.lookup("UpdateStatusService");
    }
    public  ClientLivenessServiceInt getlivenessService() throws  RemoteException,NotBoundException{
        return (ClientLivenessServiceInt) reg.lookup("ClientLivenessService");
    }

    public  SignInServiceInt getSignInService() throws  RemoteException, NotBoundException{
        return (SignInServiceInt) reg.lookup("SignInService");
    }
    public FileDownloadServiceInt getFileDownloadService() throws  RemoteException, NotBoundException{
        return (FileDownloadServiceInt) reg.lookup("FileDownloadService");
    }


}
