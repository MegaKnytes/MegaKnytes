package com.MegaKnytes.DecisionTable.editor;

import static com.MegaKnytes.DecisionTable.editor.WebFileHandler.handleUpload;
import static com.MegaKnytes.DecisionTable.editor.WebServerHandler.bindWebServer;

import android.content.Context;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.ftccommon.external.OnDestroy;
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class DTPEditor {
    private FtcEventLoop eventLoop;
    private final NanoWSD server;

    private static final Logger LOGGER = Logger.getLogger(DTPEditor.class.getName());

    public DTPEditor(Context context) {
        server = new NanoWSD(11093) {
            @Override
            protected WebSocket openWebSocket(NanoHTTPD.IHTTPSession handshake) {
                return new EditorWebSocket(handshake, eventLoop);
            }

            @Override
            public Response serveHttp(IHTTPSession session) {
                if (session.getMethod() == Method.POST && session.getUri().equals("/file/upload")) {
                    return handleUpload(session, context);
                } else if (session.getMethod() == Method.GET && session.getUri().equals("/file/list")) {
                    return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MIME_PLAINTEXT,
                            Arrays.toString(context.fileList()));
                } else {
                    return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Method not allowed");
                }
            }
        };
    }

    @WebHandlerRegistrar
    public static void attachWebUI(Context context, WebHandlerManager manager) {
        bindWebServer(context, manager);
    }

    @OnCreateEventLoop
    public static void start(Context context, FtcEventLoop eventLoop){
        DTPEditor instance = new DTPEditor(context);
        instance.eventLoop = eventLoop;
        try {
            instance.server.start(-1);
            LOGGER.log(Level.INFO, "Websocket handler started on port " + instance.server.getListeningPort());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting websocket handler", e);
        }
    }

    @OnDestroy
    public static void stop(){
        LOGGER.log(Level.INFO, "Websocket handler stopped");
    }
}