package jets.chatclient.gui.helpers;

import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import commons.remotes.server.P2PChatServiceInt;
import commons.remotes.server.RegisteringClientInt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServicesFactory {

    private  static ServicesFactory servicesFactory = null;
    private Registry reg;

    private ServicesFactory() throws RemoteException {
        reg = LocateRegistry.getRegistry(3000);
    };

    public static ServicesFactory getInstance() throws RemoteException {
        if (servicesFactory == null){
            return  new ServicesFactory();
        }
        return servicesFactory;
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
}
