package com.mojang.minecraftpe.store;

import android.util.Log;

public class Store {
    private final StoreListener listener;

    public Store(StoreListener listener) {
        this.listener = listener;
    }

    public String getStoreId() {
        Log.i("BlockLauncher", "Store: Get store ID");
        return "Placeholder store ID";
    }

    public void queryProducts(String[] products) {
        Log.i("BlockLauncher", "Store: Query products");
    }

    public void purchase(String product) {
        Log.i("BlockLauncher", "Store: Purchase " + product);
    }

    public void queryPurchases() {
        Log.i("BlockLauncher", "Store: Query purchases");
    }

    public void destructor() {
        Log.i("BlockLauncher", "Store: Destructor");
    }
}
