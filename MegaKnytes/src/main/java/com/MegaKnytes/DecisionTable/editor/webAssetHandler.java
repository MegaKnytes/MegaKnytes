package com.MegaKnytes.DecisionTable.editor;

import android.content.res.AssetManager;
import android.util.Log;

import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.MimeTypesUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import fi.iki.elonen.NanoHTTPD;

public class webAssetHandler {

    public static WebHandler fileWebHandler(AssetManager assetManager, String fileName){
        return session -> {
            if(session.getMethod() == NanoHTTPD.Method.GET){
                return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, MimeTypesUtil.determineMimeType(fileName), assetManager.open(fileName));
            } else {
                return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                        NanoHTTPD.MIME_PLAINTEXT, "");
            }
        };
    }

    public static void directoryWebHandler(AssetManager assetManager, String folderName){
        try {
            String[] list = assetManager.list(folderName);
            System.out.println(list);
            if (list == null) {
                return;
            }

            if (list.length > 0) {
                for (String file : list) {
                    fileWebHandler(assetManager, folderName + "/" + file);
                    System.out.println(folderName + "/" + file);
                }
            } else {
                fileWebHandler(assetManager, '/'+ folderName);
                System.out.println('/'+ folderName);
            }
        } catch (IOException e) {
        }
    }
}
