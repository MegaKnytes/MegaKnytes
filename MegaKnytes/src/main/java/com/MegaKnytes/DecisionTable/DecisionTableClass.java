package com.MegaKnytes.DecisionTable;


//import com.MegaKnytes.DecisionTable.drivers.DTDriverRegistryClass;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

class DecisionTableCellClass {
    int operator;    //		0 =		1 !=	2 >		3 >=	4 <		5 <=	6 +		7 -		8 *
    //     9 /    10 A    11 } MAX 12 { MIN
    char indirection;    // N=Internal		T=Timer		G=Gamepad	#=DirectValue
    double value;        // Actual value for #,  Channel number for all others
}

public class DecisionTableClass {

    public static Telemetry.Item INPUTS;
    public static Telemetry.Item INITIAL_OUTPUTS;
    public static Telemetry.Item OUTPUTS;
    public static Telemetry.Item SELECTED;
    static Telemetry telemetry;
    String[] operatorStrings = new String[]{"=", "!=", ">", ">=", "<", "<=", "+", "-", "*", "/", "A", "}", "{"};
    double[] inputBuffer, outputInitialBuffer, outputBuffer;
    int[] inputRegistryList, outputRegistryList, outputSet;
    int numInputs, numOutputs, numRows, maxInputOperators, maxOutputOperators;
    int[] dtDebugRow;
    DecisionTableCellClass[][][] decisionCells, outputCells;
    //DTDriverRegistryClass DTDriverRegistry;

    public int mapOperator(String operatorString) {
        int i;

        for (i = 0; i < operatorStrings.length; i++) {
            if (operatorString.equals(operatorStrings[i])) {
                return (i);
            }
        }
        return (-1);
    }

    public void readDT(String filename, HardwareMap hwMap, Gamepad gp1, Gamepad gp2) {
        String IOName, tempString;

        // TODO:  ADDING DRIVER: Add counter for number of driver IO entries (see dcMotorCount)
        int channel, ioRegistryIndex, internalCount, gamepadCount, timerCount, dcMotorCount, motorCount,
                crServoCount, rtpMotorCount, servoCount, mecanumCount, distanceSensorCount, knyteVisionCount;
        double initVal;
        String deviceName;

        // TODO:  Create your decision table input file in the FtcRobotController->assets folder
        //        This function will read in the file specified in filename
        BufferedReader reader = null;
        //Initialize the scanner
        Scanner scan = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(hwMap.appContext.getAssets().open(filename), StandardCharsets.UTF_8));
            scan = new Scanner(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read counts for Internal, Gamepad, Timer, SingleDCMotorDriver, Inputs, Outputs
        internalCount = scan.nextInt();
        gamepadCount = scan.nextInt();
        timerCount = scan.nextInt();
        // TODO: ADDING DRIVER: In the order they are specified in the first row of the decision table, add a read for the IO counter
        //       for the new driver.  In this sample, dcMotorCount was provided after the timer count
        dcMotorCount = scan.nextInt();
        motorCount = scan.nextInt();
        crServoCount = scan.nextInt();
        rtpMotorCount = scan.nextInt();
        servoCount = scan.nextInt();
        mecanumCount = scan.nextInt();
        distanceSensorCount = scan.nextInt();
        knyteVisionCount = scan.nextInt();
        numInputs = scan.nextInt();
        numOutputs = scan.nextInt();
        maxInputOperators = scan.nextInt();
        maxOutputOperators = scan.nextInt();

        // TODO:  ADDING DRIVER: add the new counter to the paramter list for the DTDriverRegistryClass constructor.
        //        Make sure the order matches what you add in the DTDRiverRegistryClass
        //DTDriverRegistry = new DTDriverRegistryClass(internalCount, gamepadCount, timerCount, dcMotorCount,
        //        motorCount, crServoCount, rtpMotorCount, servoCount, mecanumCount, distanceSensorCount,
        //        knyteVisionCount, numInputs, numOutputs);

        // Read IODefinitions
        tempString = scan.nextLine();        // Skip Abstract description

        //for (ioRegistryIndex = 0; ioRegistryIndex < DTDriverRegistry.numIODefs; ioRegistryIndex++) {
        //    IOName = scan.next();
        //    channel = scan.nextInt();
        //    initVal = scan.nextDouble();
        //    deviceName = scan.next();
        //    DTDriverRegistry.registerIODef(IOName, channel, initVal, deviceName, hwMap, gp1, gp2);
        //}
        // Read Number of Rows
        numRows = scan.nextInt();

        // Read Input Indexes to ioRegistry
        inputRegistryList = new int[numInputs];
        inputBuffer = new double[numInputs];
        for (int i = 0; i < numInputs; i++) {
            inputRegistryList[i] = scan.nextInt();
        }

        // Read Output Indexes to ioRegistry
        outputRegistryList = new int[numOutputs];
        outputBuffer = new double[numOutputs];
        outputInitialBuffer = new double[numOutputs];
        for (int i = 0; i < numOutputs; i++) {
            outputRegistryList[i] = scan.nextInt();
        }

        decisionCells = new DecisionTableCellClass[numRows][numInputs][maxInputOperators];
        outputCells = new DecisionTableCellClass[numRows][numOutputs][maxOutputOperators];
        dtDebugRow = new int[numRows];
        outputSet = new int[numOutputs];

        int numInputOperators;
        int numOutputOperators;
        for (int row = 0; row < numRows; row++) {
            dtDebugRow[row] = scan.nextInt();
            for (int i = 0; i < numInputs; i++) {
                numInputOperators = scan.nextInt();
                if (numInputOperators == 0) {
                    decisionCells[row][i][0] = new DecisionTableCellClass();
                    decisionCells[row][i][0].operator = -1;
                } else {
                    for (int j = 0; j < numInputOperators; j++) {
                        decisionCells[row][i][j] = new DecisionTableCellClass();
                        tempString = scan.next();
                        decisionCells[row][i][j].operator = mapOperator(tempString);
                        tempString = scan.next();
                        decisionCells[row][i][j].indirection = tempString.charAt(0);
                        decisionCells[row][i][j].value = scan.nextDouble();
                    }
                    if (numInputOperators < maxInputOperators) {
                        //System.out.println("numInputs = " + numInputs +
                        //                   "  num rows = " + numRows);
                        //System.out.println("Row = " + row + "  i = " + i + "  numInputOperators = " + numInputOperators
                        //+ "  maxInputOperators = " + maxInputOperators);
                        decisionCells[row][i][numInputOperators] = new DecisionTableCellClass();
                        decisionCells[row][i][numInputOperators].operator = -1;
                    }
                }
            }
            for (int o = 0; o < numOutputs; o++) {
                numOutputOperators = scan.nextInt();
                if (numOutputOperators == 0) {
                    outputCells[row][o][0] = new DecisionTableCellClass();
                    outputCells[row][o][0].operator = -1;
                } else {
                    for (int j = 0; j < numOutputOperators; j++) {
                        outputCells[row][o][j] = new DecisionTableCellClass();
                        tempString = scan.next();
                        outputCells[row][o][j].operator = mapOperator(tempString);
                        tempString = scan.next();
                        outputCells[row][o][j].indirection = tempString.charAt(0);
                        outputCells[row][o][j].value = scan.nextDouble();
                    }
                    if (numOutputOperators < maxOutputOperators) {
                        outputCells[row][o][numOutputOperators] = new DecisionTableCellClass();
                        outputCells[row][o][numOutputOperators].operator = -1;
                    }
                }
            }
        }
    }

    public void readIO() {
        for (int i = 0; i < numInputs; i++) {
            //inputBuffer[i] = DTDriverRegistry.IORegistryBrokerGet(inputRegistryList[i]);
        }

        for (int o = 0; o < numOutputs; o++) {
            //outputInitialBuffer[o] = DTDriverRegistry.IORegistryBrokerGet(outputRegistryList[o]);
        }
    }

    public void writeOutputs() {

        for (int o = 0; o < numOutputs; o++) {
            if (outputSet[o] == 1) {
                //DTDriverRegistry.IORegistryBrokerSet(outputRegistryList[o], outputBuffer[o]);
            }
        }
    }

    public boolean evaluateDecisionTable() {
        int row, inputCol, outputCol, rowTrue, cellComplete, cellOperator;
        double targetValue;
        boolean traceFlag = false;

        for (row = 0; row < numRows; row++) {
            for (rowTrue = 1, inputCol = 0; (rowTrue == 1) && (inputCol < numInputs); inputCol++) {
                for (cellComplete = 0, cellOperator = 0; (cellComplete == 0) && (rowTrue == 1); ) {
                    if (decisionCells[row][inputCol][cellOperator].operator == -1) {
                        cellComplete = 1;
                    } else {
                        targetValue = 0.0;//DTDriverRegistry.IORegistryBrokerIndirection(decisionCells[row][inputCol][cellOperator].indirection, decisionCells[row][inputCol][cellOperator].value);
                        switch (decisionCells[row][inputCol][cellOperator].operator) {
                            case 0: // =
                                if (inputBuffer[inputCol] != targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                            case 1: // !=
                                if (inputBuffer[inputCol] == targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                            case 2: // >
                                if (inputBuffer[inputCol] <= targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                            case 3: // >=
                                if (inputBuffer[inputCol] < targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                            case 4: // <
                                if (inputBuffer[inputCol] >= targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                            case 5: // <=
                                if (inputBuffer[inputCol] > targetValue) {
                                    rowTrue = 0;
                                }
                                break;
                        }
                    }
                    cellOperator++;
                    if (cellOperator == maxInputOperators) {
                        cellComplete = 1;
                    }
                }
            }
            if (rowTrue == 1) {
                if (dtDebugRow[row] == 1) {
                    if (!traceFlag) {
                        System.out.print("\tSelected: ");
                    }
                    traceFlag = true;
                    System.out.print(row + " ");
                    SELECTED.setValue(row + " ");
                }
                for (outputCol = 0; outputCol < numOutputs; outputCol++) {
                    for (cellOperator = 0, cellComplete = 0; cellComplete == 0; ) {
                        if (outputCells[row][outputCol][cellOperator].operator == -1) {
                            cellComplete = 1;
                        } else {
                            targetValue = 0.0;//DTDriverRegistry.IORegistryBrokerIndirection(outputCells[row][outputCol][cellOperator].indirection, outputCells[row][outputCol][cellOperator].value);
                            switch (outputCells[row][outputCol][cellOperator].operator) {
                                case 0: // =
                                    outputBuffer[outputCol] = targetValue;
                                    outputSet[outputCol] = 1;
                                    break;
                                case 6: // +
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = outputInitialBuffer[outputCol] + targetValue;
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = outputBuffer[outputCol] + targetValue;
                                    }
                                    break;
                                case 7: // -
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = outputInitialBuffer[outputCol] - targetValue;
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = outputBuffer[outputCol] - targetValue;
                                    }
                                    break;
                                case 8: // *
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = outputInitialBuffer[outputCol] * targetValue;
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = outputBuffer[outputCol] * targetValue;
                                    }
                                    break;
                                case 9: // /
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = outputInitialBuffer[outputCol] / targetValue;
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = outputBuffer[outputCol] / targetValue;
                                    }
                                    break;
                                case 10: // A
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = Math.abs(outputInitialBuffer[outputCol]);
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = Math.abs(outputBuffer[outputCol]);
                                    }
                                    break;
                                case 11: // } maximum
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = Math.max(outputInitialBuffer[outputCol], targetValue);
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = Math.max(outputBuffer[outputCol], targetValue);
                                    }
                                    break;
                                case 12: // } minimum
                                    if (outputSet[outputCol] == 0) {
                                        outputBuffer[outputCol] = Math.min(outputInitialBuffer[outputCol], targetValue);
                                        outputSet[outputCol] = 1;
                                    } else {
                                        outputBuffer[outputCol] = Math.min(outputBuffer[outputCol], targetValue);
                                    }
                                    break;
                            }
                        }
                        cellOperator++;
                        if (cellOperator == maxOutputOperators) {
                            cellComplete = 1;
                        }
                    }
                }
            }
        }
        return traceFlag;
    }

    public void executeDT(Telemetry t) {
        telemetry = t;
        INPUTS = telemetry.addData("Inputs         : ", "");
        INITIAL_OUTPUTS = telemetry.addData("Initial Outputs: ", "");
        OUTPUTS = telemetry.addData("Outputs       : ", "");
        SELECTED = telemetry.addData("Selected     : ", "");
        telemetry.addData("Say", "Hello MegaKnytes");
        telemetry.update();

        //System.out.println("======> Start executeDT");

        Arrays.fill(outputSet, 0);    // Mark outputs as not set in this round
        readIO();
        //System.out.println("======> after readIO");

        boolean traceFlag = evaluateDecisionTable();
        //System.out.println("======> after evaluateDecisionTable");

        writeOutputs();
        //System.out.println("======> writeOutputs");

        if (traceFlag) {
            System.out.print("Inputs: ");

            for (int i = 0; i < numInputs; i++) {
                System.out.print(inputBuffer[i] + " ");
                INPUTS.setValue(inputBuffer[i] + " ");
            }

            System.out.print("\tInitial Outputs: ");

            for (int o = 0; o < numOutputs; o++) {
                System.out.print(outputInitialBuffer[o] + " ");
                INITIAL_OUTPUTS.setValue(outputInitialBuffer[o] + " ");
            }
            System.out.print("\tOutputs: ");

            for (int o = 0; o < numOutputs; o++) {
                System.out.print("\t" + outputBuffer[o] + "(" + outputSet[o] + ")");
                OUTPUTS.setValue(outputBuffer[o] + "(" + outputSet[o] + ")");
            }
            System.out.println();

        }

    }
}

