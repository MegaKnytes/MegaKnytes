package com.MegaKnytes.DecisionTable.editor;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

import android.content.Context;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import fi.iki.elonen.NanoFileUpload;
import fi.iki.elonen.NanoHTTPD;

public class WebFileHandler {
    private static final Logger LOGGER = Logger.getLogger(WebFileHandler.class.getName());

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
