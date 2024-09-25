package com.MegaKnytes.DecisionTable.drivers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.List;
import java.util.Map;

public interface DTDriver{
    void setup(OpMode opMode, String deviceName, Map<String, Object> deviceOptions);
    void set(Map<String, Object> values);
    Map<String, Object> get(List<String> values);
}