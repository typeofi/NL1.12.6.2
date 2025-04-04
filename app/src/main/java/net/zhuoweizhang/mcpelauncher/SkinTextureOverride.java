package net.zhuoweizhang.mcpelauncher;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class SkinTextureOverride implements TexturePack {

    public SkinTextureOverride() {
    }

    public InputStream getInputStream(String fileName) throws IOException {
        boolean loadSkin = Utils.Default().getBoolean("zz_skin_enable", false);
        if (!loadSkin || Utils.isSafeMode()) return null;
        if (fileName.equals("images/mob/char.png")) {
            String skinPath = Utils.MC().getString("player_skin", null);
            if (skinPath == null) return null;
            File file = new File(skinPath);
            if (!file.exists()) return null;
            return Files.newInputStream(file.toPath());
        }
        return null;
    }

    public void close() throws IOException {
        //do nothing
    }

    public List<String> listFiles() throws IOException {
        return new ArrayList<>();
    }

    public long getSize(String name) throws IOException {
        return 0;
    }

}
