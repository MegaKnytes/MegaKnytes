package com.MegaKnytes.DecisionTable.drivers;

import android.content.Context;

import com.MegaKnytes.DecisionTable.editor.DTPEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import dalvik.system.DexFile;

public class DriverAnnotationProcessor {

    private static final Logger LOGGER = Logger.getLogger(DriverAnnotationProcessor.class.getName());


    public static ArrayList<Class<?>> getClassesWithAnnotation(Context context) {
        ArrayList<Class<?>> driverClasses = new ArrayList<>();
        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());

            for (String className : Collections.list(dexFile.entries())) {
                if (!className.startsWith("org.firstinspires") && !className.startsWith("com.MegaKnytes")) {
                    continue;
                }
                try {
                    Class<?> configClass = Class.forName(className, false, DTPEditor.class.getClassLoader());
                    if (configClass.isInstance(Driver.class)) {
                        LOGGER.log(Level.INFO, "Found config class: " + configClass.getName());
                        driverClasses.add(configClass);
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
