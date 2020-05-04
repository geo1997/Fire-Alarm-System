package rmiClient.alarmForm;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import rmiServer.serviceImpl.AlarmServiceImpl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;




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


    public void startTimer(){
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();


    }


    public void onInsert(ActionEvent actionEvent) throws RemoteException {
    if(isFieldValid()){
        if(isCorrectRange()){
            if(checkForDuplicates()){
                try {
                    Alarm alarm = new Alarm();
                    alarm.setRoomNum(Integer.parseInt(txtRoomNum.getText()));
                    alarm.setFloorNum(Integer.parseInt(txtFloorNum.getText()));
                    alarm.setSmokeLevel(Integer.parseInt(txtSmokeLevel.getText()));
                    alarm.setCo2level(Integer.parseInt(txtCo2Level.getText()));

                    alarm = as.saveAlarm(alarm);
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

    private void clearField() {
        txtId.setText("");
        txtFloorNum.setText("");
        txtRoomNum.setText("");
        txtSmokeLevel.setText("");
        txtCo2Level.setText("");

    }

    public void onUpdate(ActionEvent actionEvent) {
        int index = tableView.getSelectionModel().getSelectedIndex();
        System.out.println(index);

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
               Alarm a = new Alarm();
               a.setId(Integer.valueOf(txtId.getText()));
               a.setFloorNum(Integer.valueOf(txtFloorNum.getText()));
               a.setRoomNum(Integer.valueOf(txtRoomNum.getText()));
               a.setSmokeLevel(Integer.valueOf(txtSmokeLevel.getText()));
               a.setCo2level(Integer.valueOf(txtCo2Level.getText()));

               try {
                   //index=Integer.valueOf(txtId.getText());
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



    public void onDelete(ActionEvent actionEvent) {
        Alarm alarm = tableView.getSelectionModel().getSelectedItem();

        if(alarm == null){
            return;
        }

        try {
            as.deleteAlarm(alarm.getId());
            tableView.getItems().remove(alarm);

            clearField();

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public void onRefresh(ActionEvent actionEvent) {

        try {
            tableView.getItems().setAll(as.getAlarms());
            startTimer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        clearField();
    }

    public void TableRefresh(){
        colId.setCellValueFactory(new PropertyValueFactory<Alarm,Integer>("id"));
        colFloorNum.setCellValueFactory(new PropertyValueFactory<>("floorNum"));
        colRoomNum.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
        colSmokeLevel.setCellValueFactory(new PropertyValueFactory<>("smokeLevel"));
        colCo2level.setCellValueFactory(new PropertyValueFactory<>("co2level"));
    }

    ScheduledService<Integer> service = new ScheduledService<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    tableView.getItems().setAll(as.getAlarms());
                    startTimer();
                    System.out.println("i run");
                    return 0;
                }
            };
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTimer.textProperty().bind(timeSeconds.asString());
        lblTimer.setTextFill(Color.RED);
        lblTimer.setStyle("-fx-font-size: 4em;");

        TableRefresh();
        startTimer();

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





    public void setMain(Main main){
        this.main = main;
        this.as = main.getAlarmService();
        service.setPeriod(Duration.seconds(15));
        service.start();




        try {
            tableView.getItems().setAll(as.getAlarms());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
