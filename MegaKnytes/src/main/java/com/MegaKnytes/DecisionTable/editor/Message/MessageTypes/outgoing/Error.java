package com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing;

import com.MegaKnytes.DecisionTable.editor.Message.Message;
import com.google.gson.JsonObject;
import com.qualcomm.robotcore.robot.RobotState;

public class Error {
    public static JsonObject generateError(String friendlyErrorReason, String errorTrace){
        JsonObject json = new JsonObject();
        JsonObject error = new JsonObject();

        error.addProperty("friendlyErrorCause", friendlyErrorReason);
        error.addProperty("errorDebugTrace", errorTrace);

        json.addProperty("type", "ERROR");
        json.add("message", error);
        return json;
    }
}
