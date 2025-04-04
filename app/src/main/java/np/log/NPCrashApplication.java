package np.log;

import android.content.Context;

import net.zhuoweizhang.mcpelauncher.AndroidPrintStream;
import net.zhuoweizhang.mcpelauncher.BlockLauncher;

import xcrash.XCrash;

public class NPCrashApplication extends BlockLauncher {

    public NPCrashApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        NPCrashHandler instance = NPCrashHandler.getInstance();
        instance.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        XCrash.InitParameters initParameters = new XCrash.InitParameters();
        xcrash.XCrash.init(this);
    }


}
