package rmiApi.entityService;

import rmiApi.email.MailRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Emailservice extends Remote {

    public void sendEmail(MailRequest request) throws RemoteException;

    public MailRequest mailRequest(int floorLevel, int RoomNum) throws RemoteException;
}
