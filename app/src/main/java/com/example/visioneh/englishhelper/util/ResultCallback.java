package com.example.visioneh.englishhelper.util;

import okhttp3.Response;

/**
 * Created by 62588 on 2018/5/1.
 */

public interface ResultCallback {
    public void onsuccess(Response response);
    public void onfail(Exception e);
}
