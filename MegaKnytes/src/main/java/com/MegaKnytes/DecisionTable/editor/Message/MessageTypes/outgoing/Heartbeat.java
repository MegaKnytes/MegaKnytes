package com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing;

import com.MegaKnytes.DecisionTable.editor.Message.Message;
import com.google.gson.JsonObject;
import com.qualcomm.robotcore.robot.RobotState;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Heartbeat extends Message {
    public static JsonObject getHeartbeat(String runningOpMode, RobotState opModeState, double batteryVoltage){
        JsonObject json = new JsonObject();
        JsonObject heartbeat = new JsonObject();
        json.addProperty("type", "RESPONSE");
        heartbeat.addProperty("runningOpMode", runningOpMode);
        heartbeat.addProperty("opModeState", opModeState.toString());
        heartbeat.addProperty("batteryVoltage", batteryVoltage);
        json.add("message", heartbeat);
        return json;
    }
}
