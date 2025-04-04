package np.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NPCrashHandler implements Thread.UncaughtExceptionHandler {
    @SuppressLint("StaticFieldLeak")
    private static final NPCrashHandler INSTANCE = new NPCrashHandler();
    public static final String TAG = "NPCrashHandler";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Map<String, String> infos = new HashMap<>();
    @SuppressLint("SimpleDateFormat")
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    NPCrashHandler() {
    }

    public static NPCrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(t, e);
            return;
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException exception) {
            Log.e("CrashHandler", "error : ", exception);
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(NPCrashHandler.this.mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        };

        thread.start();

        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(throwable);
        return true;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                String string = info.versionName == null ? "null" : info.versionName;
                String string1 = info.versionCode + "";
                this.infos.put("versionName", string);
                this.infos.put("versionCode", string1);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CrashHandler", "an error occured when collect package info", e);
        }
        try {
            Class<?> forName = Class.forName("android.os.Build");
            Field[] declaredFields = forName.getDeclaredFields();
            for (Field field : declaredFields) {
                try {
                    field.setAccessible(true);
                    this.infos.put(field.getName(), "" + field.get(null));
                    Log.d("CrashHandler", field.getName() + " : " + field.get(null));
                } catch (Exception var10) {
                    Log.e("CrashHandler", "an error occurred when collect crash info", var10);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private void saveCrashInfo2File(Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key).append("=").append(value).append("\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        for (Throwable cause = throwable.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        String stringBuilder = stringWriter.toString();
        builder.append(stringBuilder);
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String format = this.formatter.format(new Date());
            String string = "np-crash-" + format + "-" + currentTimeMillis + ".log";
            if (Environment.getExternalStorageState().equals("mounted")) {
                @SuppressLint("SdCardPath") File file = new File("/sdcard/crash/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                @SuppressLint("SdCardPath") FileOutputStream write = new FileOutputStream("/sdcard/crash/" + string);
                write.write(builder.toString().getBytes());
                write.close();
            }
        } catch (Exception e) {
            Log.e("CrashHandler", "an error occurred while writing file...", e);
        }
    }


}
