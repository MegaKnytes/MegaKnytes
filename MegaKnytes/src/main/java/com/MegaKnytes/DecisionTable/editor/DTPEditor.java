package com.MegaKnytes.DecisionTable.editor;

import android.content.Context;
import android.content.res.AssetManager;

import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;


public class DTPEditor {

    @WebHandlerRegistrar
    public void bindWebHandler(Context context, WebHandlerManager manager) {
        AssetManager assetManager = AppUtil.getInstance().getActivity().getAssets();
        manager.register("/editor", webAssetHandler.fileWebHandler(assetManager, "editor/index.html"));
        webAssetHandler.directoryWebHandler(assetManager, "editor");
    }
}

