package rmiClient.registerForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rmiApi.entity.User;
import rmiApi.entityService.UserService;
import rmiClient.client.Main;
import rmiClient.loginForm.Login;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class Register implements Initializable {
    public TextField emailID;
    public PasswordField password;
    public Button loginBtn;
    public Button regBtn;

    private Main main;
    private UserService userService;

    public void login(ActionEvent actionEvent) throws IOException {
//        Login l = new Login();
//
//        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/alarmForm.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
//
//        Parent root = loader.load();
//
//        //AlarmForm form = loader.getController();
//        Login form = loader.getController();
//        form.setMain(this);
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
//        primaryStage.setScene(scene) ;
//
//        primaryStage.setTitle("Sensor Dashboard Login");
//        primaryStage.show();


        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));

        Parent root = loader.load();
        Login login = loader.getController();
        login.setMain(this.main);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene) ;

        primaryStage.setTitle("Sensor Dashboard Login ");
        primaryStage.show();
    }

    public void regUSer(ActionEvent actionEvent) throws RemoteException {
        String email = emailID.getText();
        if(checkEmail()){
            if(isValid(email)){
                if(EmailCheck(email)) {
                    User u = new User();
                    u.setEmail(emailID.getText());
                    u.setPassword(password.getText());
                    User newU = userService.addUser(u);
                    if (newU == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Unable to Add");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please Try Again");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Successfully Added ");
                        alert.setHeaderText("User Details Registered Successfully");
                        alert.setContentText("Please Login");
                        alert.showAndWait();
                        emailID.setText("");
                        password.setText("");
                    }
                }
            }
        }

    }



    public boolean checkEmail() {
        String email = emailID.getText();
        String pw = password.getText();


        if(emailID.getText() == null || emailID.getText().isEmpty() || password.getText()== null|| password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Field Empty");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please Type Email or Password");
            alert.showAndWait();
            return false;
        }else{
            return true;
        }
    }


    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(regex)) {
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Email");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please Enter a Valid Email");
            alert.showAndWait();
            return false;
        }

    }

    public boolean EmailCheck(String email) throws RemoteException {
       User u =  userService.getUser(email);

       if(u == null){
           return true;
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.initModality(Modality.APPLICATION_MODAL);
           alert.setTitle("Email Exist");
           alert.setHeaderText("A User already Exist");
           alert.setContentText("Please Enter a Different Email");
           alert.showAndWait();
           return false;
       }
    }


    public void setMain(Main main) {
        this.main = main;
        this.userService = main.getUserService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Register(ActionEvent actionEvent) {
    }

}
