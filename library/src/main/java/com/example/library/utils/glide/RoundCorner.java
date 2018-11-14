package com.example.library.utils.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/06/28
 */
class RoundCorner extends BitmapTransformation {

    private static final String ID =
            "com.findtech.threePomelos.utils.glide.RoundCorner.";

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        //----------获取图片的尺寸大小
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
//----------- 从Bitmap池中拿去想用尺寸的bitmap资源
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config
                    .ARGB_8888);
        }
//------------- 创建画布
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
//-------------- 配置着色器----------
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader
                .TileMode.CLAMP));
        paint.setAntiAlias(true);

        float[] rids = new float[]{16, 16, 16f, 16f, 16f, 16f, 16f, 16f};
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, width, height), rids, Path.Direction.CW);
        canvas.drawPath(path,paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID.getBytes());
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RoundCorner;
    }
}
