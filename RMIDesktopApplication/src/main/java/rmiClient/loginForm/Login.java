package rmiClient.loginForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import rmiClient.alarmForm.AlarmForm;
import rmiClient.client.Main;
import rmiClient.registerForm.Register;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class Login implements Initializable {


        public Button regBtn;
        @FXML
        private TextField emailID;

        @FXML
        private PasswordField password;

        @FXML
        private Button loginBtn;

        private Main main;
        private UserService userService;

        //login button event handler
        @FXML
        public void login() throws IOException {

                if(checkEmail()){
                        if(checkPassword()){
                                //scene is set
                                Stage stage = (Stage) loginBtn.getScene().getWindow();
                                stage.close();

                                Stage primaryStage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/alarmForm.fxml"));

                                Parent root = loader.load();
                                AlarmForm form = loader.getController();
                                form.setMain(this.main);
                                primaryStage.setScene(new Scene(root)) ;

                                primaryStage.setTitle("Sensor Dashboard ");
                                primaryStage.show();

                        }

                }

        }

        // method to check if the email exists in the DB
        public boolean checkEmail() throws RemoteException {
                String email = emailID.getText();

                User u =userService.getUser(email);
                //checks whether the user information is correct
                if(u == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Invalid Fields");
                        alert.setHeaderText("Please correct invalid fields");
                        alert.setContentText("Email Does not Exist");
                        alert.showAndWait();
                        return false;
                }else{
                        return true;
                }
        }

        //method to check if the password is correct
        public boolean checkPassword() throws RemoteException {
                //get the email and password from login form
                String email = emailID.getText();
                String pass = password.getText();

                User u = userService.getUser(email);
                //If email and password match, allow the user to login
                if(email.equals(u.getEmail()) && pass.equals(u.getPassword())){
                        return true;
                }//If email and password incorrect, show error alert
                else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Invalid Fields");
                        alert.setHeaderText("Please correct invalid fields");
                        alert.setContentText("Password Does not Exist");
                        alert.showAndWait();
                        return false;
                }

        }

        //Register view action event
        public void Register(ActionEvent actionEvent) throws IOException {
                //On button click show the register view
                Stage stage = (Stage) regBtn.getScene().getWindow();
                stage.close();

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));

                Parent root = loader.load();
                Register reg = loader.getController();
                reg.setMain(this.main);
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
                primaryStage.setScene(scene) ;

                primaryStage.setTitle("Sensor Dashboard Register ");
                primaryStage.show();
        }


        public void setMain(Main main) {
                this.main = main;
                this.userService = main.getUserService();
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }


}
