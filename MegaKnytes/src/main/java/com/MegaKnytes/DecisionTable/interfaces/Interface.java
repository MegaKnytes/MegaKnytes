package com.MegaKnytes.DecisionTable.interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Interface {
    private final String name;
    private final String deviceName;
    private final Class<? extends Interface> type;

    public Interface(String name, String deviceName, Class<? extends Interface> type) {
        this.name = name;
        this.deviceName = deviceName;
        this.type = type;
    }

    public abstract <T> void setCommand(Command<T> command, T data);

    public abstract <T> T getCommand(Command<T> command);

    public abstract void init(String deviceName, HardwareMap hardwareMap);
}