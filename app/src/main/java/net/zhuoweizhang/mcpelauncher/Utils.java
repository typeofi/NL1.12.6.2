package net.zhuoweizhang.mcpelauncher;

import java.io.*;
import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.mojang.minecraftpe.MainActivity;

import net.zhuoweizhang.mcpelauncher.pro.R;

public class Utils {
    @SuppressLint("StaticFieldLeak")
    protected static Context mContext = null;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void clearDirectory(File dir) {
        File[] fileList = dir.listFiles();
        if (fileList == null) return;
        for (File f : fileList) {
            if (f.isDirectory()) {
                clearDirectory(f);
            }
            f.delete();
        }
    }

    public static Field getDeclaredFieldRecursive(Class<?> clazz, String name) {
        if (clazz == null)
            return null;
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getDeclaredFieldRecursive(clazz.getSuperclass(), name);
        }
    }

    public static void setLanguageOverride() {
        requireInit();
        String override = Default().getString("zz_language_override", "");
        if (override != null) {
            if (override.isEmpty()) return;
            String[] overrideSplit = override.split("_");
            String langName = overrideSplit[0];
            String countryName = overrideSplit.length > 1 ? overrideSplit[1] : "";
            Resources rez = mContext.getResources();
            Configuration config = new Configuration(rez.getConfiguration());
            DisplayMetrics metrics = rez.getDisplayMetrics();
            config.locale = new Locale(langName, countryName);
            rez.updateConfiguration(config, metrics);
        }
    }

    public static String join(Collection<?> list, String replacement) {
        StringBuilder b = new StringBuilder();
        for (Object item : list) {
            b.append(replacement).append(item.toString());
        }
        String r = b.toString();
        if (r.length() >= replacement.length())
            r = r.substring(replacement.length());
        return r;
    }

    public static String joinArray(Object[] arr, String sep) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) b.append(sep);
            b.append(arr[i] == null ? "null" : arr[i].toString());
        }
        return b.toString();
    }

    public static boolean hasTooManyPatches() {
        int maxPatchCount = getMaxPatches();
        return maxPatchCount >= 0 && getEnabledPatches().size() >= maxPatchCount;
    }

    public static boolean hasTooManyScripts() {
        int maxPatchCount = getMaxScripts();
        return maxPatchCount >= 0 && getEnabledScripts().size() >= maxPatchCount;
    }

    public static int getMaxPatches() {
        return mContext.getResources().getInteger(R.integer.max_num_patches);
    }

    public static int getMaxScripts() {
        return mContext.getResources().getInteger(R.integer.max_num_scripts);
    }

    public static Set<String> getEnabledPatches() {
        String theStr = MC().getString("enabledPatches", "");
        if (theStr.equals("")) return new HashSet<>();
        return new HashSet<String>(Arrays.asList(theStr.split(";")));
    }

    public static Set<String> getEnabledScripts() {
        String theStr = MC().getString("enabledScripts", "");
        if (theStr.equals("")) return new HashSet<>();
        return new HashSet<String>(Arrays.asList(theStr.split(";")));
    }

    public static boolean isSafeMode() {
        return (MainActivity.libLoaded && MainActivity.tempSafeMode) || Default().getBoolean("zz_safe_mode", false);
    }

    public static boolean isPro() {
        return mContext.getPackageName().equals("net.zhuoweizhang.mcpelauncher.pro");
    }


    public static SharedPreferences Default() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static SharedPreferences MC() {
        return mContext.getSharedPreferences("mcpelauncherprefs", 0);
    }

    public static SharedPreferences Counter() {
        return mContext.getSharedPreferences("safe_mode_counter", 0);
    }


    public static boolean hasExtrasPackage(Context context) {
        return context.getPackageName().equals("net.zhuoweizhang.mcpelauncher.pro");
    }

    public static long parseMemInfo() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/meminfo"))) {
            String string;
            while ((string = reader.readLine()) != null) {
                if (!string.contains(":")) continue;
                String[] parts = string.split(":");
                String partName = parts[0].trim();
                String[] result = parts[1].trim().split(" ");
                if (partName.equals("MemTotal")) {
                    return Long.parseLong(result[0]) * 1024;
                }
            }
        }
        return 0x400000000L; // 16 GB
    }

    /**
     * Gets the architecture of an ELF library.
     */
    public static int getElfArch(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] header = new byte[2];
        fis.skip(18);
        fis.read(header, 0, 2);
        int arch = header[0] | (header[1] << 8);
        fis.close();
        if (arch == 0x28) {
            return ScriptManager.ARCH_ARM;
        } else if (arch == 0x3) {
            return ScriptManager.ARCH_I386;
        } else {
            // I have no clue
            System.err.println(file + " has unknown architecture 0x" + Integer.toString(arch, 16));
            return ScriptManager.ARCH_ARM;
        }
    }

    public static String getArchName(int arch) {
        switch (arch) {
            case ScriptManager.ARCH_ARM:
                return "ARM";
            case ScriptManager.ARCH_I386:
                return "Intel";
            default:
                return "Unknown";
        }
    }

    public static void setupTheme(Context context, boolean fullscreen) {
        boolean darkTheme = Default().getBoolean("zz_theme_dark", false);
        if (darkTheme) {
            context.setTheme(fullscreen ? R.style.FullscreenDarkTheme : R.style.BlockLauncherDarkTheme);
        }
    }

    /**
     * Throws an exception when you try to call methods, calling this class to
     * prevent NPE, without Utils initialization.
     */
    protected static void requireInit() {
        if (mContext == null)
            throw new RuntimeException("Tried to work with Utils class without context");
    }


}
