package com.mojang.minecraftpe.store;

import android.util.Log;

import net.zhuoweizhang.mcpelauncher.pro.BuildConfig;

public class StoreFactory {
    public static Store createGooglePlayStore(String something, StoreListener listener) {
        if (BuildConfig.DEBUG) Log.i("BlockLauncher", "Store: factory: " + something + ":" + listener);
        return new Store(listener);
    }

    public static Store createAmazonAppStore(StoreListener listener) {
        if (BuildConfig.DEBUG) Log.i("BlockLauncher", "Amazon Store: " + listener);
        return new Store(listener);
    }

    public static Store createSamsungAppStore(StoreListener listener) {
        if (BuildConfig.DEBUG) Log.i("BlockLauncher", "Amazon Store: " + listener);
        return new Store(listener);
    }
}
