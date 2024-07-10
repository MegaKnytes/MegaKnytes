package com.MegaKnytes.DecisionTable.editor;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

import android.content.Context;
import android.content.res.AssetManager;

import com.qualcomm.robotcore.util.WebHandlerManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotserver.internal.webserver.MimeTypesUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import fi.iki.elonen.NanoFileUpload;
import fi.iki.elonen.NanoHTTPD;

public class WebHandler {

    private static final Logger LOGGER = Logger.getLogger(WebHandler.class.getName());

    public static void bindWebServer(WebHandlerManager manager) {
        AssetManager assetManager = Objects.requireNonNull(AppUtil.getInstance().getActivity()).getAssets();
        manager.register("/editor", WebHandler.fileWebHandler(assetManager, "editor/index.html"));
        WebHandler.directoryWebHandler(assetManager, "editor");
    }

    private static org.firstinspires.ftc.robotcore.internal.webserver.WebHandler fileWebHandler(AssetManager assetManager, String fileName) {
        return session -> {
            if (session.getMethod() == NanoHTTPD.Method.GET) {
                return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, MimeTypesUtil.determineMimeType(fileName), assetManager.open(fileName));
            } else {
                return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                        NanoHTTPD.MIME_PLAINTEXT, "");
            }
        };
    }

    private static void directoryWebHandler(AssetManager assetManager, String folderName) {
        try {
            String[] list = assetManager.list(folderName);
            if (list != null && list.length > 0) {
                for (String file : list) {
                    fileWebHandler(assetManager, folderName + "/" + file);
                }
            } else {
                fileWebHandler(assetManager, '/' + folderName);
            }
        } catch (IOException ignored) {
        }
    }

    public static NanoHTTPD.Response handleUpload(NanoHTTPD.IHTTPSession session, Context context) {
        try {
            List<FileItem> files = new NanoFileUpload(new DiskFileItemFactory()).parseRequest(session);
            for (FileItem file : files) {
                FileOutputStream fos;
                try {
                    fos = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
                    fos.write(file.get());
                    fos.close();
                } catch (Exception e) {
                    LOGGER.log(java.util.logging.Level.SEVERE, "Error saving file", e);
                    return newFixedLengthResponse(
                            NanoHTTPD.Response.Status.BAD_REQUEST, MIME_PLAINTEXT, e.toString());
                }
            }
            return newFixedLengthResponse(NanoHTTPD.Response.Status.OK, MIME_PLAINTEXT,
                    Arrays.toString(context.fileList()));
        } catch (FileUploadException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Error parsing file upload", e);
            return newFixedLengthResponse(
                    NanoHTTPD.Response.Status.BAD_REQUEST, MIME_PLAINTEXT, e.toString());
        }
    }
}
