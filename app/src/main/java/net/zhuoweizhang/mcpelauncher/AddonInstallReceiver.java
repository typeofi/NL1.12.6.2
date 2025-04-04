package net.zhuoweizhang.mcpelauncher;

import net.zhuoweizhang.mcpelauncher.ui.MainMenuOptionsActivity;

import android.content.*;
import android.content.pm.*;
import android.util.Log;

import com.mojang.minecraftpe.MainActivity;

/**
 * Handles package install events.
 * When an addon is installed or removed, restart the Dalvik VM so the app can load/unload the native library.
 * When Minecraft PE is updated, schedule a prepatching so that the new libminecraftpe can be patched.
 */
public class AddonInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i("BlockLauncher", "MCPELauncher: " + intent.toString());
        Log.i("BlockLauncher", "Is lib loaded? " + MainActivity.libLoaded);
        String packageName = intent.getData().toString().substring(8);
        boolean isMinecraftUpdate = packageName.equals("net.zhuoweizhang.mcpelauncher.pro"
        );
        if (!MainActivity.libLoaded && !isMinecraftUpdate) return;
        if (MainMenuOptionsActivity.isManagingAddons) return;
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED) && intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
            return;
        }
        try {
            PackageManager pm = context.getPackageManager();
            Log.i("BlockLauncher", "MCPELauncher: " + packageName);
            if (isMinecraftUpdate) {
                Log.i("BlockLauncher", "Detected Minecraft PE upgrade");
                Utils.MC().edit().putBoolean("force_prepatch", true).apply();
            } else if (!MainActivity.loadedAddons.contains(packageName)) { //is not currently loaded
                ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                //appInfo.dump(new LogPrinter(4, "MCPELauncher"), "");
                if (appInfo.metaData == null) return;
                String nativeLibName = appInfo.metaData.getString("net.zhuoweizhang.mcpelauncher.api.nativelibname");
                if (nativeLibName == null) return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Log.i("BlockLauncher", "Scheduling MCPELauncher restart");
        if (MainActivity.currentMainActivity != null && MainActivity.currentMainActivity.get() != null) {
            try {
                MainActivity.currentMainActivity.get().finish();
            } catch (Exception e) {
                e.printStackTrace(); //whatever, the VM's about to die anyways
            }
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (Exception ignored) {
                }
                System.exit(0);
            }
        }).start();
    }
}

