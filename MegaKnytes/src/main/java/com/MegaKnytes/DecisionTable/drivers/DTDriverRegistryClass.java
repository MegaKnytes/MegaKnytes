package com.MegaKnytes.DecisionTable.drivers;

import com.MegaKnytes.DecisionTable.drivers.common.CRServoDriver;
import com.MegaKnytes.DecisionTable.drivers.common.DistanceSensorDriver;
import com.MegaKnytes.DecisionTable.drivers.common.GamepadDriver;
import com.MegaKnytes.DecisionTable.drivers.common.InternalVariableDriver;
import com.MegaKnytes.DecisionTable.drivers.common.KnyteVisionDriver;
import com.MegaKnytes.DecisionTable.drivers.common.MecanumTeleopDriver;
import com.MegaKnytes.DecisionTable.drivers.common.MultiMotorDriver;
import com.MegaKnytes.DecisionTable.drivers.common.RTPMultiMotorDriver;
import com.MegaKnytes.DecisionTable.drivers.common.ServoDriver;
import com.MegaKnytes.DecisionTable.drivers.common.SingleDCMotorDriver;
import com.MegaKnytes.DecisionTable.drivers.common.TimerClass;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;


class IORegistryClass {
    String IOName;
    int driver;
    int channel;
    double initVal;
    String deviceName;
}

public class DTDriverRegistryClass {
    public int numIODefs;
    // TODO:  ADDING DRIVER:  Create a variable for your new driver (i.e. SingleDCMotorDriver singleDCMotorDriver)
    IORegistryClass[] IORegistry;
    InternalVariableDriver InternalVariableDriver;
    GamepadDriver gamePadDriver;
    TimerClass TimerDriver;
    SingleDCMotorDriver singleDCMotorDriver;
    MultiMotorDriver multiMotorDriver;
    CRServoDriver crServoDriver;
    //RTPMotorDriver rtpMotorDriver;
    RTPMultiMotorDriver rtpMotorDriver;
    ServoDriver servoDriver;
    MecanumTeleopDriver mecanumTeleopDriver;
    DistanceSensorDriver distanceSensorDriver;
    KnyteVisionDriver knyteVisionDriver;
    // TODO:  ADDING DRIVER: Add int to hold number of IO definitions for your device driver (i.e. numDCMotors)
    int numRegistered = 0;
    int numInternals;
    int numGamepads;
    int numTimers;
    int numDCMotors;
    int numMotors;
    int numCrServos;
    int numRtpMotors;
    int numServos;
    int numMecanum;
    int numDistanceSensor;
    int numKnyteVision;
    int numInputs;
    int numOutputs;
    int[] inputList;
    int[] outputList;

    // TODO:  ADDING DRIVER: add IO counter to parameter list for constructor (i.e. int numDCMotors)
    public DTDriverRegistryClass(int numInternals, int numGamepads, int numTimers, int numDCMotors, int numMotors,
                                 int numCrServos, int numRtpMotors, int numServos, int numMecanum, int numDistanceSensor,
                                 int numKnyteVision, int numInputs, int numOutputs) {
        int i, c;

        this.numInternals = numInternals;
        this.numGamepads = numGamepads;
        this.numTimers = numTimers;

        // TODO:  ADDING DRIVER: assign count to local variable (i.e. this.numDCMotors = numDCMotors)
        this.numDCMotors = numDCMotors;
        this.numMotors = numMotors;
        this.numCrServos = numCrServos;
        this.numRtpMotors = numRtpMotors;
        this.numServos = numServos;
        this.numMecanum = numMecanum;
        this.numDistanceSensor = numDistanceSensor;
        this.numKnyteVision = numKnyteVision;

        // TODO:  ADDING DRIVER: add number of IO definitions for new driver to the total count (see numDCMotors below)
        this.numIODefs = numInternals +
                numGamepads +
                numTimers +
                numDCMotors +
                numMotors +
                numCrServos +
                numRtpMotors +
                numServos +
                numMecanum +
                numDistanceSensor +
                numKnyteVision;

        this.numInputs = numInputs;
        this.numOutputs = numOutputs;

        gamePadDriver = new GamepadDriver(numGamepads);
        TimerDriver = new TimerClass(numTimers);
        InternalVariableDriver = new InternalVariableDriver(numInternals);

        // TODO:  ADDING DRIVER: Instantiate the new driver
        singleDCMotorDriver = new SingleDCMotorDriver(numDCMotors);
        multiMotorDriver = new MultiMotorDriver(numMotors);
        crServoDriver = new CRServoDriver(numCrServos);
        //rtpMotorDriver = new RTPMotorDriver(numRtpMotors);
        rtpMotorDriver = new RTPMultiMotorDriver(numRtpMotors);
        servoDriver = new ServoDriver(numServos);
        mecanumTeleopDriver = new MecanumTeleopDriver(numMecanum);
        distanceSensorDriver = new DistanceSensorDriver(numDistanceSensor);
        knyteVisionDriver = new KnyteVisionDriver();

        IORegistry = new IORegistryClass[numIODefs];
        inputList = new int[numInputs];
        Arrays.fill(inputList, -1);
        outputList = new int[numOutputs];
        Arrays.fill(outputList, -1);

        for (i = 0, c = 0; c < numInternals; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 0;
        }
        for (c = 0; c < numGamepads; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 1;
        }
        for (c = 0; c < numTimers; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 2;
        }

        // TODO:  ADDING DRIVER: Create the IORegistry for the number of IO definitions you specified
        //        The driver number is just incremented (i.e. the next driver added would be #4
        for (c = 0; c < numDCMotors; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 3;
        }

        // multi motor sequence test
        for (c = 0; c < numMotors; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 4;
        }

        // CR Servo
        for (c = 0; c < numCrServos; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 5;
        }

        // Run To Position Motors
        for (c = 0; c < numRtpMotors; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 6;
        }

        // Servo Motors
        for (c = 0; c < numServos; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 7;
        }

        // Mecanum
        for (c = 0; c < numMecanum; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 8;
        }

        // Distance Sensor
        for (c = 0; c < numDistanceSensor; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 9;
        }

        // KnyteVision
        for (c = 0; c < numKnyteVision; i++, c++) {
            IORegistry[i] = new IORegistryClass();
            IORegistry[i].driver = 10;
        }


    }

    public void registerIODef(String IOName, int channel, double initVal, String deviceName, HardwareMap hwMap, Gamepad gp1, Gamepad gp2) {
        IORegistry[numRegistered].IOName = IOName;
        IORegistry[numRegistered].channel = channel;
        IORegistry[numRegistered].initVal = initVal;
        IORegistry[numRegistered].deviceName = deviceName;

        // TODO:  ADDING DRIVER: Add a new case (corresponds to the driver number assigned in constructor above) for your driver
        //        (see case 3 below for the SingleDCMotorDriver
        switch (IORegistry[numRegistered].driver) {
            case 0: // InternalVariable Driver
                InternalVariableDriver.init(IOName, channel, initVal);
                break;

            case 1: // GamePad Driver
                gamePadDriver.init(IOName, channel, initVal, gp1, gp2);
                break;

            case 2: // Timer Driver
                TimerDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 3: // SingleDCMotor Driver
                singleDCMotorDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 4: // Multi Driver
                multiMotorDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 5: // CR Servo
                crServoDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 6: // Run to Position Motors
                rtpMotorDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 7: // Servo Motors
                servoDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 8: // Servo Motors
                mecanumTeleopDriver.init(IOName, channel, initVal, deviceName, gp1, gp2, hwMap);
                break;

            case 9: // Distance Sensor
                distanceSensorDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;

            case 10: // KnyteVision
                knyteVisionDriver.init(IOName, channel, initVal, deviceName, hwMap);
                break;


            default:
                break; // Do nothing
        }

        numRegistered++;
    }

    public void IORegistryBrokerSet(int IORegistryIndex, double setVal) {
        // TODO:  ADDING DRIVER: add another case for your new driver (see SingleDCMotorDriver for example)
        switch (IORegistry[IORegistryIndex].driver) {
            case 0: // InternalVariable Driver
                InternalVariableDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break; // optional

            case 1: // Gamepad driver
                gamePadDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 2: // Timer Driver
                TimerDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break; // optional

            case 3: // SingleDCMotor Driver
                singleDCMotorDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 4: // Multi Motor Driver
                multiMotorDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 5: // CR Servo Driver
                crServoDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 6: // Run to Position Driver
                rtpMotorDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 7: // Servo Driver
                servoDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 8: // Servo Driver
                mecanumTeleopDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 9: // Distance Sensor
                distanceSensorDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            case 10: // KnyteVision
                knyteVisionDriver.set(IORegistry[IORegistryIndex].channel, setVal);
                break;

            default:
                break; // Do nothing
        }
    }

    public double IORegistryBrokerGet(int IORegistryIndex) {
        double getVal = 0.0;

        // TODO:  ADDING DRIVER: add another case for your new driver (see SingleDCMotorDriver for example)
        switch (IORegistry[IORegistryIndex].driver) {
            case 0:
                getVal = InternalVariableDriver.get(IORegistry[IORegistryIndex].channel);
                break; // optional

            case 1:
                getVal = gamePadDriver.get(IORegistry[IORegistryIndex].channel);
                break; // optional

            case 2:
                getVal = TimerDriver.get(IORegistry[IORegistryIndex].channel);
                break; // optional

            case 3:
                getVal = singleDCMotorDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 4:
                getVal = multiMotorDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 5:
                getVal = crServoDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 6:
                getVal = rtpMotorDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 7:
                getVal = servoDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 8:
                getVal = mecanumTeleopDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 9:
                getVal = distanceSensorDriver.get(IORegistry[IORegistryIndex].channel);
                break;

            case 10:
                getVal = knyteVisionDriver.get(IORegistry[IORegistryIndex].channel);
                break;


            default:
                break; // Do nothing
        }
        return (getVal);
    }

    public double IORegistryBrokerIndirection(char indirectionDriver, double indirectionValue) {
        double getVal = 0.0;
        int indirectionIndex = (int) indirectionValue;

        switch (indirectionDriver) {
            case 'N':
                getVal = InternalVariableDriver.get(indirectionIndex);
                break;

            case 'G':
                getVal = gamePadDriver.get(indirectionIndex);
                break;

            case 'T':
                getVal = TimerDriver.get(indirectionIndex);
                break;

            case '#':
                getVal = indirectionValue;
                break;

            default:
                break; // Do nothing
        }
        return (getVal);
    }
}