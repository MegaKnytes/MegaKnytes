package com.MegaKnytes.DecisionTable.drivers.common;

import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.MegaKnytes.DecisionTable.utils.ConfigurationException;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Driver for controlling a CRServo.
 */
public class CRServoDriver implements DTPDriver {
    private com.qualcomm.robotcore.hardware.CRServo crServo;
    private static final Logger LOGGER = Logger.getLogger(CRServoDriver.class.getName());

    @Override
    public void setup(OpMode opMode, String deviceName, Map<String, Object> deviceOptions) {
        crServo = opMode.hardwareMap.crservo.get(deviceName);

        try {
            setDirection(deviceOptions);
            setInitialValue(deviceOptions);
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "An exception has occurred while initializing device " + deviceName);
            LOGGER.log(Level.SEVERE, e.toString());
            throw new ConfigurationException("An exception has occurred while initializing device " + deviceName);
        }
    }

    private void setDirection(Map<String, Object> deviceOptions) {
        switch ((String) Objects.requireNonNull(deviceOptions.getOrDefault("DIRECTION", ""))) {
            case "REVERSED":
                crServo.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case "FORWARD":
            default:
                crServo.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
        }
    }

    private void setInitialValue(Map<String, Object> deviceOptions) {
        Double initialValue = (Double) deviceOptions.getOrDefault("INITIAL_VALUE", 0.0);
        crServo.setPower(Objects.requireNonNull(initialValue));
    }

    @Override
    public void set(Map<String, Object> values) {
        if (values.containsKey("POWER")) {
            Object powerValue = values.get("POWER");
            if (powerValue instanceof Double) {
                crServo.setPower((Double) powerValue);
            }
        }
    }

    @Override
    public Map<String, Object> get(List<String> values) {
        Map<String, Object> response = new HashMap<>();

        for (String value : values) {
            switch (value.toUpperCase()) {
                case "POWER":
                    response.put("POWER", crServo.getPower());
                    break;
                default:
                    // Handle unknown values if necessary
                    break;
            }
        }

        return response;
    }
}
