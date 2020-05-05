package rmiClient.alarmForm;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.util.Duration;
import rmiApi.entity.Alarm;
import rmiApi.entityService.alarmService;
import rmiClient.client.Main;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;


public class AlarmForm implements Initializable {


    public TextField txtFloorNum;
    public TextField txtRoomNum;
    public TextField txtSmokeLevel;
    public TextField txtCo2Level;
    public TextField txtId;
    public TableView<Alarm> tableView;
    public TableColumn<Alarm, Integer> colId;
    public TableColumn<Alarm, Integer> colFloorNum;
    public TableColumn<Alarm, Integer> colRoomNum;
    public TableColumn<Alarm, Integer> colSmokeLevel;
    public TableColumn<Alarm, Integer> colCo2level;
    public Label lblTimer;
    private Main main;
    private alarmService as;
    private static final Integer STARTTIME = 15;
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    //method to initilize the timer
    public void startTimer(){
        Platform.runLater(() ->{
            if (timeline != null) {
                timeline.stop();
            }
            timeSeconds.set(STARTTIME);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(STARTTIME),
                            new KeyValue(timeSeconds, 0)));
            timeline.playFromStart();
        });

    }

    //insert button event handler
    public void onInsert(ActionEvent actionEvent) throws RemoteException {
    if(isFieldValid()){
        if(isCorrectRange()){
            if(checkForDuplicates()){
                try {
                     /* An Alarm refernece is intialized and the values from the form fields
                are set to the alarm reference */
                    Alarm alarm = new Alarm();
                    alarm.setRoomNum(Integer.parseInt(txtRoomNum.getText()));
                    alarm.setFloorNum(Integer.parseInt(txtFloorNum.getText()));
                    alarm.setSmokeLevel(Integer.parseInt(txtSmokeLevel.getText()));
                    alarm.setCo2level(Integer.parseInt(txtCo2Level.getText()));

                    //The saveAlarm() method of AlarmServiceImpl is executed by passing the alarm reference
                    alarm = as.saveAlarm(alarm);
                    //The new alarm is displayed in the table using the getItems() method defined in the tableView
                    tableView.getItems().add(alarm);

                    tableView.getItems().setAll(as.getAlarms());
                    clearField();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
             }


            }


        }

    }

    //method to check for existing values of floor and room number in the database
    public boolean checkForDuplicates() throws RemoteException {

        List<Alarm> al = as.getAlarms();
        boolean dup = true;

        for (Alarm a : al) {

            if (a.getFloorNum() == Integer.parseInt(txtFloorNum.getText()) && a.getRoomNum() == Integer.parseInt(txtRoomNum.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Duplicate Values");
                alert.setHeaderText("Floor number and the Room number already exists");
                alert.setContentText("Please Change Room and Floor Numbers");
                alert.showAndWait();
                dup=false;

            }

        }
        return dup;
    }


    //checks if the form fields are null
    private boolean isFieldValid() {
        String errorMessage="";

        if(txtFloorNum.getText() == null || txtFloorNum.getText().isEmpty()){
            errorMessage += "Please enter a Floor Number !\n";
        }
        if(txtRoomNum.getText() == null || txtRoomNum.getText().isEmpty()){
            errorMessage += "Please enter a Room Number !\n";
        }
        if(txtSmokeLevel.getText() == null || txtSmokeLevel.getText().isEmpty()){
            errorMessage += "Please enter a Smoke level !\n";
        }
        if(txtCo2Level.getText() == null || txtCo2Level.getText().isEmpty()){
            errorMessage += "Please enter a C02 Number !\n";
        }


        if(errorMessage.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

    }

    //Checks if the smoke level and the CO2 levels are in range of 1 and 10
    private boolean isCorrectRange() {

        String errorMessage="";
        if(Integer.parseInt(txtSmokeLevel.getText()) > 10 || Integer.parseInt(txtSmokeLevel.getText()) < 0){
            errorMessage += "Please enter a smoke level between 1 and 10 !\n";
        }
        if(Integer.parseInt(txtCo2Level.getText()) > 10 || Integer.parseInt(txtCo2Level.getText()) < 0){
            errorMessage += "Please enter a c02 level between 1 and 10 !\n";
        }

        if(errorMessage.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    //Set form fields to null after submitting form
    private void clearField() {
        txtId.setText("");
        txtFloorNum.setText("");
        txtRoomNum.setText("");
        txtSmokeLevel.setText("");
        txtCo2Level.setText("");

    }

    // update button event handler
    public void onUpdate(ActionEvent actionEvent) throws RemoteException {
        //Pass the table row index selected to an index varaible
        int index = tableView.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        //Checks if an alarm is selected from the table
        if(index == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Update");
            alert.setHeaderText("No Alarm selected");
            alert.setContentText("Please select an alarm from the table");
            alert.showAndWait();
            return ;
        }

       if(isFieldValid()){
           if(isCorrectRange()){
               if(checkForDuplicates()){
                    /* An Alarm reference is initialized and the values from the form fields
                are set to the alarm reference */
                   Alarm a = new Alarm();
                   a.setId(Integer.valueOf(txtId.getText()));
                   a.setFloorNum(Integer.valueOf(txtFloorNum.getText()));
                   a.setRoomNum(Integer.valueOf(txtRoomNum.getText()));
                   a.setSmokeLevel(Integer.valueOf(txtSmokeLevel.getText()));
                   a.setCo2level(Integer.valueOf(txtCo2Level.getText()));

                   try {
                       //Execute updateAlarm() method in alarmService and pass the updated alarm data to the tableView
                       as.updateAlarm(a);
                       tableView.getItems().set(index,a);
                       tableView.getItems().setAll(as.getAlarms());
                       clearField();
                   } catch (RemoteException e) {
                       e.printStackTrace();
                   }
               }

           }
       }

    }


    //udelete button event handler
    public void onDelete(ActionEvent actionEvent) {
        //Retrieve the selected row index value of table to pass as the alarm id
        Alarm alarm = tableView.getSelectionModel().getSelectedItem();
        //Checks if an alard id is selected
        if(alarm == null){
            return;
        }

        try {
             /*The deleteAlarm() method of AlarmServiceImpl is executed by passing the
           alarm id and the respective alarm data is removed from the table
             */
            as.deleteAlarm(alarm.getId());
            tableView.getItems().remove(alarm);

            clearField();

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    //refresh button event handler
    public void onRefresh(ActionEvent actionEvent) {
        //Invoke the getAlarms() method on click of the refresh button and reset timer


        clearField();
    }

    //Fill the table colomns with the id, floor number, room number, smoke level and co2 level
    public void TableRefresh(){
        colId.setCellValueFactory(new PropertyValueFactory<Alarm,Integer>("id"));
        colFloorNum.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
        colRoomNum.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
        colSmokeLevel.setCellValueFactory(new PropertyValueFactory<>("smokeLevel"));
        colCo2level.setCellValueFactory(new PropertyValueFactory<>("co2level"));
    }

    //javafx method used to refresh gui components in the background
    ScheduledService<Integer> service = new ScheduledService<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    tableView.getItems().setAll(as.getAlarms());
                    startTimer();
                    //System.out.println("i run");
                    return 0;
                }
            };
        }
    };

    //when the AlarmForm is loaded first thing to be executed
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Execute the timer
        lblTimer.textProperty().bind(timeSeconds.asString());
        lblTimer.setTextFill(Color.RED);
        lblTimer.setStyle("-fx-font-size: 4em;");

        TableRefresh();
        startTimer();

        // add listners to every row in the table, when row clicked retrieve the values and set them in text fields
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alarm>() {
            @Override
            public void changed(ObservableValue<? extends Alarm> observable, Alarm oldValue, Alarm newValue) {
                if (newValue != null) {
                    txtId.setText(Integer.toString(newValue.getId()));
                    txtFloorNum.setText(Integer.toString(newValue.getFloorNum()));
                    txtRoomNum.setText(Integer.toString(newValue.getRoomNum()));
                    txtSmokeLevel.setText(Integer.toString(newValue.getSmokeLevel()));
                    txtCo2Level.setText(Integer.toString(newValue.getCo2level()));
                } else {
                    clearField();
                }
            }
        });

    }




    //Execute method when loading
    public void setMain(Main main){
        this.main = main;
        this.as = main.getAlarmService();
        //background tasks refreshes every 15 seconds
        service.setPeriod(Duration.seconds(15));
        service.start();

        try {
            tableView.getItems().setAll(as.getAlarms());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
