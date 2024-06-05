package com.MegaKnytes.DecisionTable.editor;

import android.content.res.AssetManager;
import android.util.Log;

import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.MimeTypesUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import fi.iki.elonen.NanoHTTPD;

public class AssetHandler {

    public static WebHandler StaticAssetHandler(AssetManager assetManager, String fileName){
        return new WebHandler() {
            @Override
            public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
                if(session.getMethod() == NanoHTTPD.Method.GET){
                    String mimeType = MimeTypesUtil.determineMimeType(fileName);
                    return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, assetManager.open(fileName));
                } else {
                    return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND,
                            NanoHTTPD.MIME_PLAINTEXT, "");
                }
            }
        };
    }
}
