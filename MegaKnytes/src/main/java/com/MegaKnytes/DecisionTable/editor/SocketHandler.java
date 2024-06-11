package com.MegaKnytes.DecisionTable.editor;

public interface SocketHandler {

    void onOpen();

    void onClose();

    boolean onMessage(String message);
}
