package rmiApi.entityService;
import rmiApi.entity.MailRequest;
import java.rmi.Remote;
import java.rmi.RemoteException;
/*The EmailService interface is created by extending Remote interface
which will provide the descriptions of methods which can be invoked by
remote clients
 */
public interface Emailservice extends Remote {

    public void sendEmail(MailRequest request) throws RemoteException;

    public MailRequest mailRequest(int floorLevel, int RoomNum) throws RemoteException;
}
