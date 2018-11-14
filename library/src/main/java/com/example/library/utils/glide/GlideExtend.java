package com.example.library.utils.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.example.library.R;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/06/01
 */

@GlideExtension
public final class GlideExtend extends AppGlideModule {

    private GlideExtend() {
    }

    @GlideOption
    public static void simple(RequestOptions options) {
        options.centerCrop().placeholder(R.drawable.ic_vector_empty);
    }


}
