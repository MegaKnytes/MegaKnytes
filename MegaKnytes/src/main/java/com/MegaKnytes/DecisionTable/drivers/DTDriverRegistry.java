package com.MegaKnytes.DecisionTable.drivers;

import android.content.Context;

import com.MegaKnytes.DecisionTable.editor.DTPEditor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dalvik.system.DexFile;

@TeleOp
public class DTDriverRegistry {

    private static final Logger LOGGER = Logger.getLogger(DTDriverRegistry.class.getName());


    public static HashMap<String, Class<?>> getClassesWithInstanceOf(Context context, Class<?> classObject) {
        HashMap<String, Class<?>> driverClasses = new HashMap<>();
        try {
            List<String> classNames = new ArrayList<>(Collections.list(new DexFile(context.getPackageCodePath()).entries()));

            for (String className : classNames) {
                try {
                    Class<?> configClass = Class.forName(className, false, DTPEditor.class.getClassLoader());
                    if (configClass.isInstance(classObject)) {
                        LOGGER.log(Level.INFO, "Found Driver: " + configClass.getName());
                        driverClasses.put(configClass.getSimpleName(), configClass);
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading classes", e);
        }
        return driverClasses;
    }
}
