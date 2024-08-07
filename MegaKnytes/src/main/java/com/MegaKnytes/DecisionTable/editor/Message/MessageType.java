package com.MegaKnytes.DecisionTable.editor.Message;

import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing.Error;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.outgoing.Heartbeat;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.incoming.Init_OpMode;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.incoming.Start_OpMode;
import com.MegaKnytes.DecisionTable.editor.Message.MessageTypes.incoming.Stop_OpMode;

public enum MessageType {
    HEARTBEAT(Heartbeat.class),
    INIT_OPMODE(Init_OpMode.class),
    START_OPMODE(Start_OpMode.class),
    STOP_OPMODE(Stop_OpMode.class);

    final Class<? extends Message> messageClass;

    MessageType(Class<? extends Message> messageClass) {
        this.messageClass = messageClass;
    }
}
