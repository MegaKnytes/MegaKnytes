package com.MegaKnytes.DecisionTable.editor;

import android.content.Context;
import android.content.res.AssetManager;

import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.MimeTypesUtil;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class WebServerHandler {

    public static void bindWebServer(Context context, WebHandlerManager manager) {
        AssetManager assetManager = AppUtil.getInstance().getActivity().getAssets();
        manager.register("/editor", WebServerHandler.fileWebHandler(assetManager, "editor/index.html"));
        WebServerHandler.directoryWebHandler(assetManager, "editor");
    }

    private static WebHandler fileWebHandler(AssetManager assetManager, String fileName){
        return new WebHandler() {
            @Override
            public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException{
                if(session.getMethod() == NanoHTTPD.Method.GET){
                    return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, MimeTypesUtil.determineMimeType(fileName), assetManager.open(fileName));
                } else {
                    return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                            NanoHTTPD.MIME_PLAINTEXT, "");
                }
            }
        };
    }
    private static void directoryWebHandler(AssetManager assetManager, String folderName){
        try {
            String[] list = assetManager.list(folderName);
            if (list != null && list.length > 0) {
                for (String file : list) {
                    fileWebHandler(assetManager, folderName + "/" + file);
                }
            } else {
                fileWebHandler(assetManager, '/'+ folderName);
            }
        } catch (IOException ignored) {}
    }
}
