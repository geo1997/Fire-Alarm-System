package rmiServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import rmiApi.entity.Alarm;
import rmiApi.entityService.Emailservice;
import rmiApi.entityService.SmsService;
import rmiApi.entityService.alarmService;
import rmiServer.serviceImpl.AlarmServiceImpl;
import rmiServer.serviceImpl.EmailServiceImpl;
import rmiServer.serviceImpl.SmsServiceImpl;
import rmiServer.serviceImpl.UserServiceImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//class to retrieve new alarm details from the REST api periodically
class CheckForUpdates extends TimerTask{


    @Override
    public void run() {
        try {
            AlarmServiceImpl alarmService = new AlarmServiceImpl();
            //retrieve the  alarm list
            List <Alarm> a =  alarmService.getAlarms();

            /*
            iterate through the alarm list and see if the below conidition is true if so
            invokde sendEmail and sendSms methods
             */
            for(Alarm al : a){
                if(al.getCo2level() > 5 || al.getSmokeLevel() >5){
                    EmailServiceImpl emailService = new EmailServiceImpl();
                    SmsServiceImpl smsService = new SmsServiceImpl();

                   emailService.sendEmail(emailService.mailRequest(al.getFloorNum(),al.getRoomNum()));
                   smsService.sendSms(smsService.sendData(al.getFloorNum(),al.getRoomNum(),al.getCo2level(),al.getSmokeLevel()));

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
        //registry created to run the server on port 8081
        Registry registry = LocateRegistry.createRegistry(8081);

        AlarmServiceImpl alarmServiceImpl = new AlarmServiceImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
        SmsServiceImpl smsServiceImpl = new SmsServiceImpl();

        //binding the remote objects
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
        //timer to check for alarm status every 15 seconds
        timer.schedule(checkForUpdates, 0, 15000);
    }
}
