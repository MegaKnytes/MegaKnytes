package com.MegaKnytes.DecisionTable.editor;

import android.content.Context;
import android.content.res.AssetManager;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.util.WebHandlerManager;

import org.MegaKnytes.DecisionTables.R;
import org.firstinspires.ftc.ftccommon.external.OnCreate;
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlOpMode;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.FtcWebSocket;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.FtcWebSocketMessage;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketManager;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketNamespaceHandler;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketListener;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class DTPEditor {

    @WebHandlerRegistrar
    public static void bindWebHandler(Context context, WebHandlerManager manager) throws InterruptedException {
        AssetManager assetManager = AppUtil.getInstance().getActivity().getAssets();
        manager.register("/editor", webAssetHandler.fileWebHandler(assetManager, "editor/index.html"));
        webAssetHandler.directoryWebHandler(assetManager, "editor");
        WebSocketNamespaceHandler webSocketNamespaceHandler = new WebSocketNamespaceHandler("editor") {
            @Override
            protected void registerMessageTypeHandlers(Map<String, WebSocketMessageTypeHandler> messageTypeHandlerMap) {
                super.registerMessageTypeHandlers(messageTypeHandlerMap);
            }

            @Override
            public boolean onMessage(FtcWebSocketMessage message, FtcWebSocket webSocket) {
                super.onMessage(message, webSocket);
                System.out.println("MESSAGE RECIEVED: " + message.toString());
                return true;
            }

            @Override
            public void onSubscribe(FtcWebSocket webSocket) {
                super.onSubscribe(webSocket);
            }

            @Override
            public void onUnsubscribe(FtcWebSocket webSocket) {
                super.onUnsubscribe(webSocket);
            }
        };
        manager.getWebServer().getWebSocketManager().registerNamespaceHandler(webSocketNamespaceHandler);
    }


}

