package com.MegaKnytes.DecisionTable.drivers.common;

import com.qualcomm.robotcore.hardware.Gamepad;

//TODO: Implement GamepadDriver
public class GamepadDriver {
    int numGamePads;

    Gamepad gp1, gp2;

    public GamepadDriver(int numGamePads) {
        int i;
        this.numGamePads = numGamePads;
    }

    public double get(int channel) {
        Gamepad gamepad = null;
        // channel # range indicates which gamepad it is
        // gamepad1 is channels 0-18
        // gamepad2 is channels 20-38
        switch (channel) {
// Gamepad 1
            case 0:  // A button
                if (gp1.a) return (1.0);
                break;
            case 1: // X button
                if (gp1.x) return (1.0);
                break;
            case 2: // Y button
                if (gp1.y) return (1.0);
                break;
            case 3: // B button
                if (gp1.b) return (1.0);
                break;
            case 4: // dpad_left button
                if (gp1.dpad_left) return (1.0);
                break;
            case 5: // dpad_up button
                if (gp1.dpad_up) return (1.0);
                break;
            case 6: // dpad_down button
                if (gp1.dpad_down) return (1.0);
                break;
            case 7: // dpad_right button
                if (gp1.dpad_right) return (1.0);
                break;
            case 8: // left_bumper button
                if (gp1.left_bumper) return (1.0);
                break;
            case 9: // right_bumper button
                if (gp1.right_bumper) return (1.0);
                break;
            case 10: // right_stick_button button
                if (gp1.right_stick_button) return (1.0);
                break;
            case 11: // left_stick_button button
                if (gp1.left_stick_button) return (1.0);
                break;
            case 12: // back
                if (gp1.back) return (1.0);
                break;
            case 13: // left_stick_x
                return gp1.left_stick_x;
            case 14: // left_stick_y
                return gp1.left_stick_y;
            case 15: // right_stick_x
                return gp1.right_stick_x;
            case 16: // right_stick_y
                return gp1.right_stick_y;
            case 17: // left_trigger
                return gp1.left_trigger;
            case 18: // right_trigger
                return gp1.right_trigger;

// Gamepad 2
            case 20:  // A button
                if (gp2.a) return (1.0);
                break;
            case 21: // X button
                if (gp2.x) return (1.0);
                break;
            case 22: // Y button
                if (gp2.y) return (1.0);
                break;
            case 23: // B button
                if (gp2.b) return (1.0);
                break;
            case 24: // dpad_left button
                if (gp2.dpad_left) return (1.0);
                break;
            case 25: // dpad_up button
                if (gp2.dpad_up) return (1.0);
                break;
            case 26: // dpad_down button
                if (gp2.dpad_down) return (1.0);
                break;
            case 27: // dpad_right button
                if (gp2.dpad_right) return (1.0);
                break;
            case 28: // left_bumper button
                if (gp2.left_bumper) return (1.0);
                break;
            case 29: // right_bumper button
                if (gp2.right_bumper) return (1.0);
                break;
            case 30: // right_stick_button button
                if (gp2.right_stick_button) return (1.0);
                break;
            case 31: // left_stick_button button
                if (gp2.left_stick_button) return (1.0);
                break;
            case 32: // back
                if (gp2.back) return (1.0);
                break;
            case 33: // left_stick_x
                return gp2.left_stick_x;
            case 34: // left_stick_y
                return gp2.left_stick_y;
            case 35: // right_stick_x
                return gp2.right_stick_x;
            case 36: // right_stick_y
                return gp2.right_stick_y;
            case 37: // left_trigger
                return gp2.left_trigger;
            case 38: // right_trigger
                return gp2.right_trigger;
        }
        return (0.0);
    }

    public void set(int channel, double value) {
    }

    public void init(String IOName, int channel, double initVal, Gamepad gp1, Gamepad gp2) {
        this.gp1 = gp1;
        this.gp2 = gp2;
    }
}
