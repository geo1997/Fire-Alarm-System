package rmiApi.entityService;
import rmiApi.entity.SmsRequest;
import java.rmi.Remote;
import java.rmi.RemoteException;
/*The SmsService interface is created by extending Remote interface
which will provide the descriptions of methods which can be invoked by
remote clients
 */
public interface SmsService extends Remote {

    public void sendSms( SmsRequest smsRequest)throws RemoteException;

    public SmsRequest sendData(int floorLevel, int RoomNum ,int co2 , int smoke)throws RemoteException;
}
