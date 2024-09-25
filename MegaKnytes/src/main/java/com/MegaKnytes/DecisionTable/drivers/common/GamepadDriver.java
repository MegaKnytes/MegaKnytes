package com.MegaKnytes.DecisionTable.drivers.common;

import com.MegaKnytes.DecisionTable.drivers.DTDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GamepadDriver implements DTDriver {
    private Gamepad gamepad;

    @Override
    public void setup(OpMode opMode, String deviceName, Map<String, Object> deviceOptions) {
        if(Objects.equals(deviceName, "Gamepad1")){
            gamepad = opMode.gamepad1;
        } else if (Objects.equals(deviceName, "Gamepad2")){
            gamepad = opMode.gamepad2;
        } else {
            throw new RuntimeException("deviceName must be one of Gamepad1/Gamepad2");
        }
    }

    @Override
    public void set(Map<String, Object> values) {
        // Nothing to see here...
    }

    @Override
    public Map<String, Object> get(List<String> values) {
        Map<String, Object> response = new HashMap<>();

        for (String value : values) {
            switch (value.toUpperCase()) {
                case "A":
                    response.put("A", gamepad.a);
                    break;
                case "B":
                    response.put("B", gamepad.b);
                    break;
                case "X":
                    response.put("X", gamepad.x);
                    break;
                case "Y":
                    response.put("Y", gamepad.y);
                    break;
                    //TODO: Finish this...
                default:
                    // Handle unknown values if necessary
                    break;
            }
        }

        return response;
    }
}
