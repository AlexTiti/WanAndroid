package com.example.library.helper.okhttp;

import com.example.library.utils.AppUtils;
import com.example.library.utils.HttpUtils;
import com.example.library.utils.NetworkConnectionUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 * 无网络时的缓存拦截器
 */

public class NoNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 无网络时，设置超时为4周
        int maxStale = 60 * 60 * 24 * 28;
        Request request = chain.request();

        if (!NetworkConnectionUtils.INSTANCE.isNetworkConnected(AppUtils.INSTANCE.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .removeHeader("User-Agent")
                    .header("User-Agent", HttpUtils.INSTANCE.getUserAgent())
                    //                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1;
                    // WOW64) AppleWebKit/537.36" +
                    //                            " (KHTML, like Gecko) Chrome/50.0.2661.102
                    // Safari/537.36")
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    }
}
