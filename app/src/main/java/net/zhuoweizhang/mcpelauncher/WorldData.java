package net.zhuoweizhang.mcpelauncher;

import android.util.Log;

import net.zhuoweizhang.mcpelauncher.pro.BuildConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.json.*;

public class WorldData {

    private static final boolean DEBUG = false;

    File mDir;
    File mFile;
    JSONObject mData;
    boolean dirty = false;

    public WorldData(File dir) throws IOException {
        mDir = dir;
        mFile = new File(dir, "blocklauncher_data.json");
        load();
    }

    protected void load() throws IOException {
        if (!mFile.exists() || mFile.length() == 0) {
            loadDefaults();
            return;
        }
        byte[] buf = new byte[(int) mFile.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mFile);
            fis.read(buf);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ie) {
                    // screw it
                    ie.printStackTrace();
                }
            }
        }
        try {
            mData = new JSONObject(new String(buf, StandardCharsets.UTF_8));
        } catch (JSONException je) {
            je.printStackTrace();
            loadDefaults();
        }
    }

    protected void loadDefaults() {
        try {
            mData = new JSONObject();
            mData.put("entities", new JSONObject());
        } catch (JSONException je) {
            throw new RuntimeException(je); // literally impossible
        }
    }

    public void setEntityData(long entityId, String key, String value) {
        if (!key.contains(".")) {
            throw new RuntimeException("Entity data keys must be in format of author.modname.keyname;" +
                    " for example, coolmcpemodder.sz.favoritecolor");
        }
        try {
            JSONObject obj = mData.getJSONObject("entities");
            JSONObject entityData = mData.optJSONObject(Long.toString(entityId));
            if (entityData == null) {
                entityData = new JSONObject();
                obj.put(Long.toString(entityId), entityData);
            }
            entityData.put(key, value);
            if (DEBUG)
                Log.i("WorldData", "world data: set entity data " + entityId + ":" + key + ":" + value);
            dirty = true;
        } catch (JSONException je) {
            throw new RuntimeException(je);
        }
    }

    public String getEntityData(long entityId, String key) {
        try {
            JSONObject obj = mData.getJSONObject("entities");
            JSONObject entityData = obj.optJSONObject(Long.toString(entityId));
            if (entityData == null) return null;
            return entityData.optString(key);
        } catch (JSONException je) {
            throw new RuntimeException(je);
        }
    }

    public void clearEntityData(long entityId) {
        try {
            JSONObject obj = mData.getJSONObject("entities");
            dirty = (null != obj.remove(Long.toString(entityId)));
            if (dirty && DEBUG)
                Log.i("WorldData", "world data: cleared entity data " + entityId);
        } catch (JSONException je) {
            throw new RuntimeException(je);
        }
    }

    public void save() throws IOException {
        if (DEBUG)
            Log.i("WorldData", dirty ? "Saving world data" : "Not saving world data");
        if (!dirty) return;
        FileOutputStream fis = null;
        try {
            fis = new FileOutputStream(mFile);
            fis.write(mData.toString().getBytes(StandardCharsets.UTF_8));
            dirty = false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ie) {
                    // screw it
                    ie.printStackTrace();
                }
            }
        }
    }
}
