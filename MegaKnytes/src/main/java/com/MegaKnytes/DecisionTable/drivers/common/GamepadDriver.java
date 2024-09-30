package com.MegaKnytes.DecisionTable.drivers.common;

import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.MegaKnytes.DecisionTable.utils.ConfigurationException;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GamepadDriver implements DTPDriver {
    private Gamepad gamepad;

    @Override
    public void setup(OpMode opMode, String deviceName, Map<String, Object> deviceOptions) {
        Object hardwareMap = deviceOptions.getOrDefault("HardwareMap", "");
        assert hardwareMap != null;
        if (hardwareMap.equals("Gamepad1")) {
            gamepad = opMode.gamepad1;
        } else if (hardwareMap.equals("Gamepad2")) {
            gamepad = opMode.gamepad2;
        } else {
            throw new ConfigurationException("Gamepads should have a HardwareMap value of Gamepad1 or Gamepad2. Please check your configuration");
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
