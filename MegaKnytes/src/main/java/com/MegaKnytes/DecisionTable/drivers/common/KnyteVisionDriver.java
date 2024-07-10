package com.MegaKnytes.DecisionTable.drivers.common;

import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.MegaKnytes.DecisionTable.drivers.Driver;
import com.qualcomm.robotcore.hardware.HardwareMap;

@DTPDriver
public class KnyteVisionDriver extends Driver {

    @Override
    public double get(int channel) {
        return 0;
    }

    @Override
    public void set(int channel, double value) {

    }

    @Override
    public void init(String IOName, int channel, double initVal, String deviceName, HardwareMap hwMap) {

    }
}
