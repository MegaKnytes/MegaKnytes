package com.MegaKnytes.DecisionTable.drivers.common;


import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.MegaKnytes.DecisionTable.drivers.Driver;
import com.qualcomm.robotcore.hardware.HardwareMap;

@DTPDriver
public class TimerClass extends Driver {
    int numTimers;
    long[] Timers;

    public TimerClass(int numTimers) {
        int i;

        this.numTimers = numTimers;
        Timers = new long[numTimers];

        for (i = 0; i < numTimers; i++) {
            Timers[i] = 0;
        }
    }

    @Override
    public double get(int channel) {
        if ((channel >= 0) && (channel < numTimers)) {
            if (System.currentTimeMillis() < Timers[channel]) {
                return (1.0);
            } else {
                return (0.0);
            }
        }
        return (-1.0);
    }

    @Override
    public void set(int channel, double value) {
        if ((channel >= 0) && (channel < numTimers)) {
            Timers[channel] = System.currentTimeMillis() + (long) (value);
        }
    }

    @Override
    public void init(String IOName, int channel, double initVal, String deviceName, HardwareMap hwMap) {

    }

}
