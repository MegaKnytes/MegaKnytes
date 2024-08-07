package com.MegaKnytes.DecisionTable.editor;

import com.MegaKnytes.DecisionTable.editor.Message.Message;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing.Error;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing.Heartbeat;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.incoming.Init_OpMode;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing.Success;
import com.qualcomm.ftccommon.FtcEventLoop;

import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());

    public static String handleMessage(FtcEventLoop eventLoop, Message message) {
        String response;
        try{
            switch (message.getType()) {
                case STOP_OPMODE:
                    eventLoop.getOpModeManager().requestOpModeStop(eventLoop.getOpModeManager().getActiveOpMode());
                    response = Success.generateSuccess().toString();
                    break;
                case START_OPMODE:
                    eventLoop.getOpModeManager().startActiveOpMode();
                    response = Success.generateSuccess().toString();
                    break;
                case INIT_OPMODE:
                    try {
                        eventLoop.getOpModeManager().initOpMode(((Init_OpMode) message).getOpModeName());
                        response = Success.generateSuccess().toString();
                    } catch (NullPointerException e) {
                        LOGGER.log(Level.WARNING, "OpMode Name Not Provided", e);
                        response = Error.generateError("OpMode Name Not Provided", e.toString()).toString();
                    }
                    break;
                case HEARTBEAT:
                    try{
                        response = Heartbeat.getHeartbeat(
                                eventLoop.getOpModeManager().getActiveOpModeName(),
                                eventLoop.getOpModeManager().getRobotState(),
                                eventLoop.getOpModeManager().getActiveOpMode().hardwareMap.voltageSensor.iterator().next().getVoltage()
                        ).toString();
                    } catch (NullPointerException e) {
                        LOGGER.log(Level.WARNING, "Attempt to Generate Heartbeat while Robot Not Initialized", e);
                        response = Error.generateError("Attempt to Generate Heartbeat while Robot Not Initialized", e.toString()).toString();
                    }
                    break;
                default:
                    LOGGER.log(Level.WARNING, "Unknown message type: " + message.getType());
                    LOGGER.log(Level.WARNING, "Message: " + message);
                    response = Error.generateError("Unknown message type", "Unknown message type: " + message.getType()).toString();
                    break;
            }
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Error handling message", e);
            response = Error.generateError("Malformed Message Received", e.toString()).toString();
        }
        return response;
    }
}
