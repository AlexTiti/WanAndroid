package com.example.library.helper.okhttp.cache;

import com.example.library.utils.AppUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.INSTANCE.getContext().getExternalCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
