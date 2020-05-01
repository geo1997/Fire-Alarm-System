package rmiApi.entityService;

import rmiApi.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    public User getUser(String email)throws RemoteException;
}
