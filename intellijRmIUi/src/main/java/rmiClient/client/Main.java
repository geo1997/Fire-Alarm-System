package rmiClient.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rmiApi.entity.Alarm;
import rmiApi.entityService.UserService;
import rmiApi.entityService.alarmService;
import rmiClient.alarmForm.AlarmForm;
import rmiClient.loginForm.Login;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {
    private alarmService as;
    private UserService userService;
    @Override

    public void start(Stage primaryStage) throws Exception {
        //Bind the service with registry
        Registry registry = LocateRegistry.getRegistry("localhost",8081);

        //getting the stub object by the lookup() method of the Naming class and invoking the method on this object.
        as = (alarmService) registry.lookup("alarmService");
        userService = (UserService) registry.lookup("userService") ;
        //setting the login scene
        Login l = new Login();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        Parent root = loader.load();
        Login form = loader.getController();
        form.setMain(this);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene) ;
        primaryStage.setTitle("Sensor Dashboard Login");
        primaryStage.show();

    }

    public alarmService getAlarmService(){
        return as;
    }

    public UserService getUserService() {
        return userService;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
