package com.MegaKnytes.DecisionTable;

import android.content.Context;

import com.MegaKnytes.DecisionTable.drivers.DTDriverRegistry;
import com.MegaKnytes.DecisionTable.drivers.DTPDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;


import java.io.File;
import java.util.HashMap;

public class DTProcessor {
    private HashMap<String, Class<? extends DTPDriver>> driverClassList = new HashMap<>();
    private final HardwareMap hwMap;

    public DTProcessor(HardwareMap hwMap){
        this.hwMap = hwMap;
        updateDriverList(hwMap.appContext);
    }

    public void updateDriverList(Context context){
        driverClassList = DTDriverRegistry.getClassesWithInstanceOf(context, DTPDriver.class);
    }

    public HashMap<String, Class<? extends DTPDriver>> getDriverList(){
        return driverClassList;
    }

    public HashMap<String, Class<? extends DTPDriver>> getDriverClassList(){
        return driverClassList;
    }

    public File getFileFromApplicationContext(String fileName){
        return hwMap.appContext.getFileStreamPath(fileName);
    }
}
