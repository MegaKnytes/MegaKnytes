package com.MegaKnytes.DecisionTable.drivers.common;

import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DistanceSensorDriver implements DTPDriver {
    private DistanceSensor sensor;
    private DistanceUnit unit;

    @Override
    public void setup(OpMode opMode, String deviceName, Map<String, Object> deviceOptions) {
        sensor = opMode.hardwareMap.get(DistanceSensor.class, deviceName);
        switch ((DistanceUnit) Objects.requireNonNull(deviceOptions.getOrDefault("DISTANCE_UNIT", DistanceUnit.INCH))) {
            case MM:
                unit = DistanceUnit.MM;
                break;
            case METER:
                unit = DistanceUnit.METER;
                break;
            case CM:
                unit = DistanceUnit.CM;
                break;
            case INCH:
            default:
                unit = DistanceUnit.INCH;
                break;
        }
    }


    @Override
    public void set(Map<String, Object> values) {
        // Nothing to see here
    }

    @Override
    public Map<String, Object> get(List<String> values) {
        Map<String, Object> response = new HashMap<>();

        for (String value : values) {
            switch (value.toUpperCase()) {
                case "DISTANCE":
                    response.put("DISTANCE", sensor.getDistance(unit));
                    break;
                default:
                    // Handle unknown values if necessary
                    break;
            }
        }

        return response;
    }
}
