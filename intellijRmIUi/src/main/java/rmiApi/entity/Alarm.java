package rmiApi.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import java.io.*;

/*Implement Alarm modal class by defining setters and getters
and constructor and the modal class has implemented the Externalizable interface
by extending the Serializable interface
 */

public class Alarm implements Externalizable {


    private final IntegerProperty id = new SimpleIntegerProperty();
    private  final IntegerProperty floorNum = new SimpleIntegerProperty();
    private  final IntegerProperty roomNum = new SimpleIntegerProperty();
    private final IntegerProperty smokeLevel = new SimpleIntegerProperty();
    private  final IntegerProperty co2level = new SimpleIntegerProperty();

    public Alarm() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getFloorNum() {
        return floorNum.get();
    }

    public IntegerProperty floorNumProperty() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum.set(floorNum);
    }

    public int getRoomNum() {
        return roomNum.get();
    }

    public IntegerProperty roomNumProperty() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum.set(roomNum);
    }

    public int getSmokeLevel() {
        return smokeLevel.get();
    }

    public IntegerProperty smokeLevelProperty() {
        return smokeLevel;
    }

    public void setSmokeLevel(int smokeLevel) {
        this.smokeLevel.set(smokeLevel);
    }

    public int getCo2level() {
        return co2level.get();
    }

    public IntegerProperty co2levelProperty() {
        return co2level;
    }

    public void setCo2level(int co2level) {
        this.co2level.set(co2level);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeInt(getFloorNum());
        out.writeInt(getRoomNum());
        out.writeInt(getSmokeLevel());
        out.writeInt(getCo2level());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readInt());
        setFloorNum(in.readInt());
        setRoomNum(in.readInt());
        setSmokeLevel(in.readInt());
        setCo2level(in.readInt());
    }
}
