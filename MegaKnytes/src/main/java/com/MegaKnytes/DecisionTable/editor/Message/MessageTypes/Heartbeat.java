package com.MegaKnytes.DecisionTable.editor.Message.MessageTypes;

import com.MegaKnytes.DecisionTable.editor.Message.Message;

public class Heartbeat extends Message {
    public String runningOpMode = null;
    public robotState opModeState = null;
    public Double batteryVoltage = null;

    public enum robotState {
        INIT,
        STOPPED,
        RUNNING
    }
}
