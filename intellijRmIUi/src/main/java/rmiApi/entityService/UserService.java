package rmiApi.entityService;

import rmiApi.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*The UserService interface is created by extending Remote interface
which will provide the descriptions of methods which can be invoked by
remote clients
 */
public interface UserService extends Remote {

    public User getUser(String email)throws RemoteException;
    public User addUser(User newUser) throws RemoteException;
}
