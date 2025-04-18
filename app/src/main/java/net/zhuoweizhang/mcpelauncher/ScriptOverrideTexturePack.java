package net.zhuoweizhang.mcpelauncher;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import android.content.Context;

public class ScriptOverrideTexturePack implements TexturePack {

    public ScriptOverrideTexturePack(Context ctx) {
    }

    public InputStream getInputStream(String fileName) throws IOException {
        if (ScriptManager.androidContext == null) return null;
        File file = ScriptManager.getTextureOverrideFile(fileName);
        if (!file.exists()) return null;
        return Files.newInputStream(file.toPath());
    }

    public long getSize(String fileName) throws IOException {
        if (ScriptManager.androidContext == null) return -1;
        File file = ScriptManager.getTextureOverrideFile(fileName);
        if (!file.exists()) return -1;
        return file.length();
    }

    public void close() throws IOException {
        //do nothing
    }

    public List<String> listFiles() {
        // empty list for now
        return new ArrayList<>();
    }

}
