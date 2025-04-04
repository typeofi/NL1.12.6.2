package net.zhuoweizhang.mcpelauncher;

import android.util.Log;

import net.zhuoweizhang.mcpelauncher.pro.BuildConfig;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;

class Scrambler {
    static native void nativeScramble(ByteBuffer dataToScramble, MpepInfo info);

    static Reader scramble(byte[] input, MpepInfo info) {
        ByteBuffer dataBuffer = ByteBuffer.allocateDirect(input.length);
        dataBuffer.put(input);
        dataBuffer.rewind();
        nativeScramble(dataBuffer, info);
        byte[] output = new byte[input.length];
        dataBuffer.get(output);
        String mpepStr = new String(output, StandardCharsets.UTF_8);
        if (BuildConfig.DEBUG) Log.i("Scrambler", mpepStr);
        return new StringReader(mpepStr);
    }
}
