package com.MegaKnytes.DecisionTable.editor;

import com.google.gson.JsonObject;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.robocol.RobocolParsable;

import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class EditorWebSocket extends NanoWSD.WebSocket{
    private static final Logger LOGGER = Logger.getLogger(EditorWebSocket.class.getName());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final FtcEventLoop eventLoop;

    public EditorWebSocket(NanoHTTPD.IHTTPSession handshakeRequest, FtcEventLoop eventLoop) {
        super(handshakeRequest);
        this.eventLoop = eventLoop;
        startPing();
    }

    private void startPing() {
        final Runnable ping = new Runnable() {
            VoltageSensor voltageSensor = eventLoop.getOpModeManager().getActiveOpMode().hardwareMap.voltageSensor.iterator().next();
            public void run() {
                JsonObject ping = new JsonObject();
                ping.addProperty("batteryLevel", voltageSensor.getVoltage());
                ping.addProperty("runningOpMode", eventLoop.getOpModeManager().getActiveOpModeName());
                if(isOpen()){
                    try {
                        ping(ping.toString().getBytes());
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Error sending ping", e);
                    }
                }
            }
        };
        scheduler.scheduleWithFixedDelay(ping, 0, 500, TimeUnit.MILLISECONDS);
    }



    @Override
    protected void onOpen() {
        try {
            this.send("Connection established");
            LOGGER.log(Level.INFO, "Connection established");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error sending message", e);
        }
    }

    @Override
    protected void onClose(NanoWSD.WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
        LOGGER.log(Level.INFO, "Connection closed: " + reason);
        scheduler.shutdown();
    }

    @Override
    protected void onMessage(NanoWSD.WebSocketFrame message) {
        LOGGER.log(Level.INFO, "Received message: " + Arrays.toString(message.getBinaryPayload()));
        try {
            this.send("Ping");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPong(NanoWSD.WebSocketFrame pong) {
        LOGGER.log(Level.INFO, "Received pong: " + Arrays.toString(pong.getBinaryPayload()));
        pong.getOpCode();
    }

    @Override
    protected void onException(IOException exception) {
        LOGGER.log(Level.SEVERE, "WebSocket exception", exception);
        if (exception instanceof SocketTimeoutException) {
            LOGGER.log(Level.INFO, "SocketTimeoutException occurred. Closing connection.");
            try {
                this.close(NanoWSD.WebSocketFrame.CloseCode.AbnormalClosure, "Timeout occurred", false);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error closing EditorWebSocket connection", e);
            }
        }
    }
}