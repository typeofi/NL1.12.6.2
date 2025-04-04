package com.mojang.android.net;

import static com.mojang.android.net.HTTPRequest.debugNet;

import android.util.Log;

import org.apache.http.Header;

public class HTTPResponse {
    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;

    private final int status;
    private final int responseCode;
    private final String body;
    private final Header[] headers;

    public HTTPResponse(int status, int responseCode, String body, Header[] headers) {
        this.status = status;
        this.responseCode = responseCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatus() {
        if (debugNet) Log.i("BlockLauncher", "get status");
        return status;
    }

    public String getBody() {
        if (debugNet) Log.i("BlockLauncher", "get response " + body);
        return body;
    }

    public int getResponseCode() {
        if (debugNet) Log.i("BlockLauncher", "get response code");
        return responseCode;
    }

    public Header[] getHeaders() {
        if (debugNet) Log.i("BlockLauncher", "get headers");
        return headers;
    }
}
