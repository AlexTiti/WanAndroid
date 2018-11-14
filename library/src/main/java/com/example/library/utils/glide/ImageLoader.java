package com.example.library.utils.glide;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/05
 */
public class ImageLoader {

    public static void loadImageFromUrlThumb(Context context, String url, Float thumbnail, ImageView imageView) {
        GlideApp.with(context).load(url).simple().thumbnail(thumbnail).into(imageView);
    }
}
