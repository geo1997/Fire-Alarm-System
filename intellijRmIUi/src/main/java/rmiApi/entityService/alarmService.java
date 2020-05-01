package rmiApi.entityService;

import rmiApi.entity.Alarm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface alarmService extends Remote {

    public Alarm saveAlarm(Alarm alarm) throws RemoteException;
    public List<Alarm> getAlarms() throws RemoteException;
    public Alarm getAlarmById(int id)throws RemoteException;
    public void deleteAlarm(int id)throws RemoteException;
    public Alarm updateAlarm(Alarm alarm)throws RemoteException;

}
