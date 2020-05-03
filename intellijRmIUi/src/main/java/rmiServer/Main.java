package rmiServer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import rmiApi.email.MailRequest;
import rmiApi.entity.Alarm;
import rmiApi.entityService.alarmService;
import rmiServer.serviceImpl.AlarmServiceImpl;
import rmiServer.serviceImpl.EmailServiceImpl;
import rmiServer.serviceImpl.SmsServiceImpl;
import rmiServer.serviceImpl.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class CheckForUpdates extends TimerTask{

    @Override
    public void run() {
        try {
            AlarmServiceImpl alarmServiceImpl = new AlarmServiceImpl();
            List <Alarm> a =  alarmServiceImpl.getAlarms();

            for(Alarm al : a){
                if(al.getCo2level() > 5 || al.getSmokeLevel() >5){
                   EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
                    SmsServiceImpl smsServiceImpl = new SmsServiceImpl();
                   //emailServiceImpl.sendEmail(emailServiceImpl.mailRequest(al.getFloorNum(),al.getRoomNum()));
                   //smsServiceImpl.sendSms(smsServiceImpl.sendData(al.getFloorNum(),al.getRoomNum(),al.getCo2level(),al.getSmokeLevel()));

                }
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.createRegistry(8081);

        AlarmServiceImpl alarmServiceImpl = new AlarmServiceImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
        SmsServiceImpl smsServiceImpl = new SmsServiceImpl();

        //alarmService as = (alarmService) UnicastRemoteObject.exportObject(alarmServiceImpl,0);

        registry.rebind("userService",userServiceImpl);
        registry.rebind("alarmService",alarmServiceImpl);
        registry.rebind("emailService",emailServiceImpl);
        registry.rebind("smsService",smsServiceImpl);


        System.out.println("Server is running");
        Platform.exit();
    }


    public static void main(String[] args) {

        launch(args);
        Timer timer = new Timer();
        CheckForUpdates checkForUpdates = new CheckForUpdates();
        timer.schedule(checkForUpdates, 0, 15000);
    }
}
