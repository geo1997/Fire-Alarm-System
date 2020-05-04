package rmiApi.entityService;

import rmiApi.entity.Alarm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*The alarmService interface is created by extending Remote interface
which will provide the descriptions of methods which can be invoked by
remote clients
 */
public interface alarmService extends Remote {

    public Alarm saveAlarm(Alarm alarm) throws RemoteException;
    public List<Alarm> getAlarms() throws RemoteException;
    public void deleteAlarm(int id)throws RemoteException;
    public Alarm updateAlarm(Alarm alarm)throws RemoteException;

}
