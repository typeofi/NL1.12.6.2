package net.zhuoweizhang.mcpelauncher.pro;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.patch.PatchUtils;
import net.zhuoweizhang.mcpelauncher.ui.LauncherActivity;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class LauncherProActivity extends LauncherActivity implements SensorEventListener {
    private Sensor rotationVectorSensor;
    private SensorManager sensorManager;
    private boolean sensorEnabled = false;
    private final float[] lastEvent = new float[3];
    private final float[] zeroVector = new float[3];
    private final float[] tempMatrix = new float[9];

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                // Requesting permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);

            }
        }

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.rotationVectorSensor = this.sensorManager.getDefaultSensor(11);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.sensorEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("zz_sensorcontrol", false);
        ScriptManager.sensorEnabled = this.sensorEnabled;
        if (this.sensorEnabled) {
            this.sensorManager.registerListener(this, this.rotationVectorSensor, 1);
        }
        setRequestedOrientation(this.sensorEnabled ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.sensorEnabled) {
            this.sensorManager.unregisterListener(this);
        }
    }

    protected boolean hasIpPatch() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("zz_patch_ip_enable", false);
    }

    protected void applyIpPatch(String ipToPatch) throws Exception {
        if (ipToPatch != null && !ipToPatch.isEmpty()) {
            Log.i("BlockLauncher", "patching IP " + ipToPatch);
            ByteBuffer buffer = minecraftLibBuffer;
            buffer.position(this.minecraftVersion.ipAddressOffset);
            buffer.put(ipToPatch.getBytes(StandardCharsets.US_ASCII));
            buffer.put((byte) 0);
        }
    }

    protected void applyPortPatch(int portToPatch) {
        ByteBuffer buffer = minecraftLibBuffer;
        buffer.position(this.minecraftVersion.portOffset);
        buffer.put(PatchUtils.createMovwInstr(1, portToPatch));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 11) {
            SensorManager.getRotationMatrixFromVector(this.tempMatrix, event.values);
            SensorManager.getOrientation(this.tempMatrix, this.lastEvent);
            ScriptManager.newPlayerYaw = (float) (((this.lastEvent[0] - this.zeroVector[0]) / 3.141592653589793d) * 180.0d);
            ScriptManager.newPlayerPitch = (float) (((this.lastEvent[2] - this.zeroVector[2]) / 3.141592653589793d) * 180.0d);
        }
    }

    @Override
    protected void resetOrientation() {
        if (this.sensorEnabled) {
            this.zeroVector[0] = this.lastEvent[0];
            this.zeroVector[1] = this.lastEvent[1];
            this.zeroVector[2] = this.lastEvent[2];
        }
    }


}
