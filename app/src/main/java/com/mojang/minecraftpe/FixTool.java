package com.mojang.minecraftpe;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import net.zhuoweizhang.mcpelauncher.ScriptManager;

import java.lang.reflect.Method;

public class FixTool {
    private static int storedSdkTarget = -1;
    public static Class<?> classVMRuntime;
    public static Method methodVMRuntimeGetRuntime;
    private static Method methodVMRuntimeGetTargetSdkVersion;
    private static Method methodVMRuntimeSetTargetSdkVersion;


    public static void lowerSdkTarget() {
        try {
            // I'm so, so sorry.
            classVMRuntime = Class.forName("dalvik.system.VMRuntime");

            methodVMRuntimeGetRuntime = classVMRuntime.getMethod("getRuntime");
            Object runtime = methodVMRuntimeGetRuntime.invoke(null);
            methodVMRuntimeGetTargetSdkVersion = classVMRuntime.getMethod("getTargetSdkVersion");
            if (Build.VERSION.SDK_INT > 28) { // higher than Pie
                methodVMRuntimeSetTargetSdkVersion = ScriptManager.nativeGrabMethod(classVMRuntime,
                        "setTargetSdkVersion", "(I)V", false);
            } else {
                methodVMRuntimeSetTargetSdkVersion = classVMRuntime.getMethod("setTargetSdkVersion", Integer.TYPE);
            }
            storedSdkTarget = (Integer) methodVMRuntimeGetTargetSdkVersion.invoke(runtime);
            methodVMRuntimeSetTargetSdkVersion.invoke(runtime, Build.VERSION_CODES.LOLLIPOP);
            int newSdkTarget = (Integer) methodVMRuntimeGetTargetSdkVersion.invoke(runtime);
            Log.i("BlockLauncher", "Temporarily setting SDK target to: " + newSdkTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restoreSdkTarget() {
        if (storedSdkTarget == -1) return;
        try {
            Object runtime = methodVMRuntimeGetRuntime.invoke(null);
            methodVMRuntimeSetTargetSdkVersion.invoke(runtime, storedSdkTarget);
            int newSdkTarget = (Integer) methodVMRuntimeGetTargetSdkVersion.invoke(runtime);
            Log.i("BlockLauncher", "Restored SDK target to: " + newSdkTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
