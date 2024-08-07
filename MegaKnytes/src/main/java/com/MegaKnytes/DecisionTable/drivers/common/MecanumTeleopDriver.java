package com.MegaKnytes.DecisionTable.drivers.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumTeleopDriver {
    static int NUM_MOTORS = 4;
    int numGamePads;
    Gamepad gp1, gp2;
    DcMotorEx leftFront;
    DcMotorEx leftRear;
    DcMotorEx rightFront;
    DcMotorEx rightRear;

    public MecanumTeleopDriver(int numGamePads) {
        int i;
        this.numGamePads = numGamePads;
    }


    /**
     * The channel will be used to determine which wheel power to calculate
     * 0 = leftFront
     * 1 = leftRear
     * 2 = rightFront
     * 3 = rightRear
     */
    public double get(int channel) {

        double Turbo = .6;
        double power = 0.0;

        if (gp1.right_trigger > .1) {
            Turbo = Math.abs(gp1.right_trigger);
        }

        if (gp1.right_bumper) {
            Turbo = .3;
        }

        double max;
        double FLP, FRP, BLP, BRP = 0.0;

        switch (channel) {
            case 0:  // leftFront
                FLP = (gp1.left_stick_y - gp1.left_stick_x - gp1.right_stick_x);
                FRP = (gp1.left_stick_y + gp1.left_stick_x + gp1.right_stick_x);
                BLP = (gp1.left_stick_y + gp1.left_stick_x - gp1.right_stick_x);
                BRP = (gp1.left_stick_y - gp1.left_stick_x + gp1.right_stick_x);

                // these formulas, mainly the + and - were decided by this google sheet https://docs.google.com/spreadsheets/d/1BPMyjsu-4DkF7KgXNrFWA1kWHJWOQpCgCkCnJslcFJA/edit#gid=0
                max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
                // scales power if any motor power is greater than 1
                if (max > 1) {
                    FLP /= max;
                    FRP /= max;
                    BLP /= max;
                    BRP /= max;
                }
                // TODO remove the *0.5 reduction once driver is better
                power = (FLP * Turbo);
                break;
            case 1: // leftRear
                FLP = (gp1.left_stick_y - gp1.left_stick_x - gp1.right_stick_x);
                FRP = (gp1.left_stick_y + gp1.left_stick_x + gp1.right_stick_x);
                BLP = (gp1.left_stick_y + gp1.left_stick_x - gp1.right_stick_x);
                BRP = (gp1.left_stick_y - gp1.left_stick_x + gp1.right_stick_x);

                // these formulas, mainly the + and - were decided by this google sheet https://docs.google.com/spreadsheets/d/1BPMyjsu-4DkF7KgXNrFWA1kWHJWOQpCgCkCnJslcFJA/edit#gid=0
                max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
                // scales power if any motor power is greater than 1
                if (max > 1) {
                    FLP /= max;
                    FRP /= max;
                    BLP /= max;
                    BRP /= max;
                }
                // TODO remove the *0.5 reduction once driver is better
                power = (BLP * Turbo);
                break;
            case 2: // rightFront
                FLP = (gp1.left_stick_y - gp1.left_stick_x - gp1.right_stick_x);
                FRP = (gp1.left_stick_y + gp1.left_stick_x + gp1.right_stick_x);
                BLP = (gp1.left_stick_y + gp1.left_stick_x - gp1.right_stick_x);
                BRP = (gp1.left_stick_y - gp1.left_stick_x + gp1.right_stick_x);

                // these formulas, mainly the + and - were decided by this google sheet https://docs.google.com/spreadsheets/d/1BPMyjsu-4DkF7KgXNrFWA1kWHJWOQpCgCkCnJslcFJA/edit#gid=0
                max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
                // scales power if any motor power is greater than 1
                if (max > 1) {
                    FLP /= max;
                    FRP /= max;
                    BLP /= max;
                    BRP /= max;
                }
                // TODO remove the *0.5 reduction once driver is better
                power = (FRP * Turbo);
                break;
            case 3: // rightRear
                FLP = (gp1.left_stick_y - gp1.left_stick_x - gp1.right_stick_x);
                FRP = (gp1.left_stick_y + gp1.left_stick_x + gp1.right_stick_x);
                BLP = (gp1.left_stick_y + gp1.left_stick_x - gp1.right_stick_x);
                BRP = (gp1.left_stick_y - gp1.left_stick_x + gp1.right_stick_x);

                // these formulas, mainly the + and - were decided by this google sheet https://docs.google.com/spreadsheets/d/1BPMyjsu-4DkF7KgXNrFWA1kWHJWOQpCgCkCnJslcFJA/edit#gid=0
                max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
                // scales power if any motor power is greater than 1
                if (max > 1) {
                    FLP /= max;
                    FRP /= max;
                    BLP /= max;
                    BRP /= max;
                }
                // TODO remove the *0.5 reduction once driver is better
                power = (BRP * Turbo);
                break;
        }
        return (power);
    }

    public void set(int channel, double value) {

        //lf, lb, rf, rb
        leftFront.setPower(0.3 * (gp1.left_stick_y - gp1.left_stick_x - gp1.right_stick_x));
        leftRear.setPower(0.3 * (gp1.left_stick_y + gp1.left_stick_x - gp1.right_stick_x));
        rightFront.setPower(0.3 * (gp1.left_stick_y + gp1.left_stick_x + gp1.right_stick_x));
        rightRear.setPower(0.3 * (gp1.left_stick_y - gp1.left_stick_x + gp1.right_stick_x));


        /*switch (channel) {
            case 0:
                leftFront.setPower(value);
                break;
            case 1:
                leftRear.setPower(value);
                break;
            case 2:
                rightFront.setPower(value);
                break;
            case 3:
                rightRear.setPower(value);
                break;
        }*/
    }

    public void init(String IOName, int channel, double initVal, String deviceName, Gamepad gp1, Gamepad gp2, HardwareMap hwMap) {
        this.gp1 = gp1;
        this.gp2 = gp2;

        switch (channel) {
            case 0:
                leftFront = hwMap.get(DcMotorEx.class, deviceName);
                leftFront.setDirection(DcMotor.Direction.REVERSE);
                leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case 1:
                leftRear = hwMap.get(DcMotorEx.class, deviceName);
                leftRear.setDirection(DcMotor.Direction.REVERSE);
                leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case 2:
                rightFront = hwMap.get(DcMotorEx.class, deviceName);
                rightFront.setDirection(DcMotor.Direction.FORWARD);
                rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case 3:
                rightRear = hwMap.get(DcMotorEx.class, deviceName);
                rightRear.setDirection(DcMotor.Direction.FORWARD);
                rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
        }
    }
}
