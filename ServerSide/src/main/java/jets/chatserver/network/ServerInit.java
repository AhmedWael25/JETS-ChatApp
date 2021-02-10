package jets.chatserver.network;

import jets.chatserver.network.rmi.AddFriendImpl;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.DBModels.DBUser;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;

public class ServerInit   {

    public void  serverInit(){

        try{
            Registry reg = LocateRegistry.createRegistry(3000);

            //Bind Services
            reg.rebind("AddFriendService",new AddFriendImpl());


            System.out.println("Server Up And Running");

            //===================Testing AREA for all DAOS

            FriendsDao f= FriendsDaoImpl.getFriendsDaoInstance();

            var frnds = f.getAllFriendsIds("1");
            for (String s :frnds){
                System.out.println(s);
            }
            List<DBUser> userFriends = f.getAllFriends("1");

            System.out.println("This user has friends : ");
            for(DBUser user : userFriends){
                System.out.println(user);
            }


        }catch (RemoteException | SQLException e){

        }

    }
}
