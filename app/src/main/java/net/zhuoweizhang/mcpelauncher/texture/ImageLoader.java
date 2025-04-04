package net.zhuoweizhang.mcpelauncher.texture;

import java.io.*;

import android.graphics.*;

public interface ImageLoader {
    Bitmap load(InputStream is) throws IOException;

    void save(Bitmap bmp, OutputStream os) throws IOException;
}
