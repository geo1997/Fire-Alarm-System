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
        //On button click show the login view
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
                     /* A user reference is initialized and the values from the form fields
                    are set to the user reference */
                    User u = new User();
                    u.setEmail(emailID.getText());
                    u.setPassword(password.getText());
                    User newU = userService.addUser(u);
                    //If there is no user details added to the form, an error alert is shown
                    if (newU == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Unable to Add");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please Try Again");
                        alert.showAndWait();
                    } //If correct user details added to the form, an alert is shown indicating the success
                    else {
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


    //Checks if the email and password are correct
    public boolean checkEmail() {
        String email = emailID.getText();
        String pw = password.getText();

        //Create an error alert
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

    //Checks the email syntax
    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(regex)) {
            //Return true if the syntax is correct
            return true;
        }else{
            //If the syntax is incorrect, create an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Email");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Please Enter a Valid Email");
            alert.showAndWait();
            return false;
        }

    }
    //checks whether if the email is already registered
    public boolean EmailCheck(String email) throws RemoteException {
       User u =  userService.getUser(email);

       if(u == null){
           return true;
       }else{
           //Show error alert if the user already exist
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
