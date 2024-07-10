package com.MegaKnytes.DecisionTable.interfaces.common;

import com.MegaKnytes.DecisionTable.interfaces.Command;
import com.MegaKnytes.DecisionTable.interfaces.Interface;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoInterface extends Interface {

    Servo servo;

    double position;
    Servo.Direction direction;

    public ServoInterface(String name, String deviceName) {
        super(name, deviceName, ServoInterface.class);
    }

    @Override
    public <T> void setCommand(Command<T> command, T data) {
        switch ((ServoCommands) command) {
            case SET_POSITION:
                servo.setPosition((Double) command.getData());
                position = (Double) command.getData();
                break;
            case SET_DIRECTION:
                servo.setDirection((Servo.Direction) command.getData());
                direction = (Servo.Direction) command.getData();
                break;
        }
    }

    @Override
    public <T> T getCommand(Command<T> command) {
        switch ((ServoCommands) command) {
            case GET_POSITION:
                return (T) (Double) position;
            case GET_DIRECTION:
                return (T) direction;
        }
        return null;
    }

    @Override
    public void init(String deviceName, HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, deviceName);
    }

    public enum ServoCommands implements Command<Double> {
        SET_POSITION,
        SET_DIRECTION,
        GET_POSITION,
        GET_DIRECTION;

        private Double data;

        @Override
        public Double getData() {
            return this.data;
        }

        @Override
        public void setData(Double data) {
            this.data = data;
        }
    }
}