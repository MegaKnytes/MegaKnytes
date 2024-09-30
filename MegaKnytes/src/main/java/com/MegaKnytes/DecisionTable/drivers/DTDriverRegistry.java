package com.MegaKnytes.DecisionTable.drivers;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import dalvik.system.DexFile;


public class DTDriverRegistry {

    private static final Logger LOGGER = Logger.getLogger(DTDriverRegistry.class.getName());

    public static HashMap<String, Class<? extends DTPDriver>> getClassesWithInstanceOf(Context context, Class<? extends DTPDriver> classObject) {
        HashMap<String, Class<? extends DTPDriver>> driverClasses = new HashMap<>();

        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            for (String className : Collections.list(dexFile.entries())) {
                try {
                    Class<?> configClass = Class.forName(className, false, DTDriverRegistry.class.getClassLoader());
                    if (Arrays.asList(configClass.getInterfaces()).contains(DTPDriver.class)) {
                        //TODO: Fix Unchecked Cast - Working for now
                        Class<? extends DTPDriver> driverClass = (Class<? extends DTPDriver>) configClass;
                        driverClasses.put(configClass.getSimpleName(), driverClass);
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Error while attempting to setup class", e);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while reading classes", e);
        }
        return driverClasses;
    }
}