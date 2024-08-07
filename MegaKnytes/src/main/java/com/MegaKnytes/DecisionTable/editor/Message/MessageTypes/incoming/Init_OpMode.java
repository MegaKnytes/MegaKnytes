package com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.incoming;

import com.MegaKnytes.DecisionTable.editor.Message.Message;

public class Init_OpMode extends Message {
    private final String opModeName;

    public Init_OpMode(String opModeName) {
        this.opModeName = opModeName;
    }

    public String getOpModeName() {
        return opModeName;
    }
}
