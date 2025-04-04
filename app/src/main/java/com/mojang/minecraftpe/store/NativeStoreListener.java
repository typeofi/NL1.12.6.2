package com.mojang.minecraftpe.store;

public class NativeStoreListener implements StoreListener {
    private final long callback;

    public NativeStoreListener(long callback) {
        this.callback = callback;
    }
}
