package com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing;

import com.google.gson.JsonObject;

public class Success {
    public static JsonObject generateSuccess(){
        JsonObject json = new JsonObject();
        json.addProperty("type", "RESPONSE");
        json.addProperty("message", "SUCCESS");
        return json;
    }
}
