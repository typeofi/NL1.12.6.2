package net.zhuoweizhang.mcpelauncher;

import java.io.Closeable;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;

public interface TexturePack extends Closeable {

    InputStream getInputStream(String fileName) throws IOException;

    List<String> listFiles() throws IOException;

    long getSize(String fileName) throws IOException;

}
