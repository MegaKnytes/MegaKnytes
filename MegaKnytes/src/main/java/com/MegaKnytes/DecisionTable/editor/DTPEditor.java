package com.MegaKnytes.DecisionTable.editor;

import static com.MegaKnytes.DecisionTable.editor.WebHandler.bindWebServer;
import static com.MegaKnytes.DecisionTable.editor.WebHandler.handleUpload;

import android.content.Context;

import com.MegaKnytes.DecisionTable.drivers.DTDriver;
import com.MegaKnytes.DecisionTable.drivers.DTDriverRegistry;
import com.MegaKnytes.DecisionTable.editor.Message.Message;
import com.MegaKnytes.DecisionTable.editor.Message.MessageDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.eventloop.opmode.AnnotatedOpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.ftccommon.external.OnDestroy;
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoWSD;

public class DTPEditor {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Message.class, new MessageDeserializer())
            .serializeNulls()
            .create();
    private static final Logger LOGGER = Logger.getLogger(DTPEditor.class.getName());
    private static DTPEditor instance;
    private final NanoWSD server;
    private FtcEventLoop eventLoop;


    public DTPEditor(Context context) {
        server = new NanoWSD(11093) {
            @Override
            protected WebSocket openWebSocket(IHTTPSession handshake) {
                return new EditorWebSocket(handshake, eventLoop);
            }

            @Override
            public Response serveHttp(IHTTPSession session) {
                if (session.getMethod() == Method.POST && session.getUri().equals("/file/upload")) {
                    return handleUpload(session, context);
                } else if (session.getMethod() == Method.DELETE && session.getUri().equals("/file")) {
                    try {
                        List<String> files = session.getParameters().get("file");
                        assert files != null;
                        files.forEach(context::deleteFile);
                        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "File deleted");
                    } catch (Exception e) {
                        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Error deleting file");
                    }
                } else if (session.getMethod() == Method.GET && session.getUri().equals("/file")) {
                    try {
                        List<String> files = session.getParameters().get("file");
                        assert files != null;
                        FileInputStream fis = context.openFileInput(files.get(0));
                        assert fis != null;
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        StringBuilder fileContent = new StringBuilder();
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            fileContent.append(new String(buffer, 0, bytesRead));
                        }
                        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, fileContent.toString());
                    } catch (Exception e) {
                        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Error reading file");
                    }
                } else if (session.getMethod() == Method.GET && session.getUri().equals("/file/list")) {
                    return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT,
                            Arrays.toString(context.fileList()));
                } else if (session.getMethod() == Method.GET && session.getUri().equals("/drivers/list")) {
                    return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT,
                            String.valueOf(DTDriverRegistry.getClassesWithInstanceOf(context, DTDriver.class)));
                } else {
                    return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Method not allowed");
                }
            }
        };
    }

    @WebHandlerRegistrar
    public static void attachWebUI(Context context, WebHandlerManager manager) {
        bindWebServer(manager);
    }

    @OnCreateEventLoop
    public static void start(Context context, FtcEventLoop eventLoop) {
        if (instance == null) {
            instance = new DTPEditor(context);
            instance.eventLoop = eventLoop;
        } else {
            LOGGER.log(Level.WARNING, "DTPEditor already initialized");
        }
        try {
            instance.server.start(-1);
            LOGGER.log(Level.INFO, "Websocket handler started on port " + instance.server.getListeningPort());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting websocket handler", e);
        }
    }

    @OnDestroy
    public static void stop() {
        instance.server.stop();
        LOGGER.log(Level.INFO, "Websocket handler stopped");
    }
}