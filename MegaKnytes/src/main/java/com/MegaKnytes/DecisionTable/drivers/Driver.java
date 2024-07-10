package com.MegaKnytes.DecisionTable.drivers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Driver {
    public abstract double get(int channel);

    public abstract void set(int channel, double value);

    public abstract void init(String IOName, int channel, double initVal, String deviceName, HardwareMap hwMap);
}
