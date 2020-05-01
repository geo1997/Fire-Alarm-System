package rmiApi.entityService;

import rmiApi.sms.SmsRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SmsService extends Remote {

    public void sendSms( SmsRequest smsRequest)throws RemoteException;

    public SmsRequest sendData(int floorLevel, int RoomNum ,int co2 , int smoke)throws RemoteException;
}
