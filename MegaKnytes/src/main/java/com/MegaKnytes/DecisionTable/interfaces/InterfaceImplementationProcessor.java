package com.MegaKnytes.DecisionTable.interfaces;

import android.content.Context;

import com.MegaKnytes.DecisionTable.editor.DTPEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import dalvik.system.DexFile;

public class InterfaceImplementationProcessor {
    private static final Logger LOGGER = Logger.getLogger(InterfaceImplementationProcessor.class.getName());

    public static ArrayList<Class<?>> getClassesWithInterface(Context context) {
        ArrayList<Class<?>> driverClasses = new ArrayList<>();
        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());

            for (String className : Collections.list(dexFile.entries())) {
                if (!className.startsWith("org.firstinspires") && !className.startsWith("com.MegaKnytes")) {
                    continue;
                }
                try {
                    Class<?> configClass = Class.forName(className, false, DTPEditor.class.getClassLoader());
                    if (configClass.isAssignableFrom(Interface.class)) {
                        LOGGER.log(Level.INFO, "Found instance class: " + configClass.getName());
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

