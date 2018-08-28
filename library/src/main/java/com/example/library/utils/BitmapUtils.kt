package com.example.library.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import java.io.*

/**
 * @author  : Alex
 * @date    : 2018/08/01
 * @version : V 2.0.0
 */
object BitmapUtils {

    /**
     * 将一个view转换成bitmap位图
     *
     * @param view 要转换的View
     * @return view转换的bitmap
     */
    fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight,
                Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        return bitmap
    }

    /**
     * 获取模糊虚化的bitmap
     *
     * @param context
     * @param bitmap  要模糊的图片
     * @param radius  模糊等级 >=0 && <=25
     * @return
     */
    fun getBlurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurBitmap(context, bitmap, radius)
        } else bitmap
    }

    /**
     * android系统的模糊方法
     *
     * @param bitmap 要模糊的图片
     * @param radius 模糊等级 >=0 && <=25
     */
    private fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap
                    .Config.ARGB_8888)
            val rs = RenderScript.create(context)
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

            val allIn = Allocation.createFromBitmap(rs, bitmap)
            val allOut = Allocation.createFromBitmap(rs, outBitmap)

            blurScript.setRadius(radius.toFloat())
            blurScript.setInput(allIn)
            blurScript.forEach(allOut)
            allOut.copyTo(outBitmap)
            bitmap.recycle()
            rs.destroy()
            return outBitmap
        } else {
            return bitmap
        }
    }

    /**
     * 根据资源获取Bitmap
     */
    open fun getFitSampleBitmap(resources: Resources, id:Int, width: Int, height: Int):Bitmap{
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,id,options)
        options.inSampleSize = getFitInSampleSize(height,width,options)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources,id)
    }

    /**
     * 按图片尺寸压缩 参数是bitmap
     * @param bitmap
     * @param pixelW
     * @param pixelH
     * @return
     */
    fun compressImageFromBitmap(bitmap: Bitmap, pixelW: Int, pixelH: Int): Bitmap {
        val os = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        if (os.toByteArray().size / 1024 > 512) {//判断如果图片大于0.5M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os)//这里压缩50%，把压缩后的数据存放到baos中
        }
        var `is` = ByteArrayInputStream(os.toByteArray())
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inPreferredConfig = Bitmap.Config.RGB_565
        BitmapFactory.decodeStream(`is`, null, options)
        options.inJustDecodeBounds = false
        options.inSampleSize = getFitInSampleSize(if (pixelH > pixelW) pixelW else pixelH, pixelW * pixelH,options)
        `is` = ByteArrayInputStream(os.toByteArray())
        return BitmapFactory.decodeStream(`is`, null, options)
    }

    /**
     * 根据文件路径获取Bitmap
     */
    private  fun getFitSampleBitmap(path: String, width: Int, height: Int):Bitmap{
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path,options)
        options.inSampleSize = getFitInSampleSize(height,width,options)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path,options)
    }

    /**
     * 根据字节流获取Bitmap
     */
    fun getFitSimpleBitmap(inputStream: InputStream, filePath:String, width: Int, height: Int):Bitmap{
        return getFitSampleBitmap(createStreamToFile(filePath,inputStream),width,height)
    }

    fun createStreamToFile(path: String,inputStream:InputStream):String{

        var  file = File(path)
        if (file.exists()){
            file.delete()
        }
        file.createNewFile()

        var outputStream = FileOutputStream(file)
        var byte = ByteArray(1024)
        var len = 0
        while ((inputStream.read(byte))!= -1){
            len = inputStream.read(byte)
            outputStream.write(byte,0,len)
        }

        inputStream.close()
        outputStream.close()
        return path

    }


    /**
     * 获取压缩比例
     */
    private fun getFitInSampleSize( height:Int,width:Int,options: BitmapFactory.Options):Int{
        var inSampleSize = 1
        if (options.outWidth > width || options.outHeight > height){
            var widthRadio : Int = Math.round(options.outWidth.toFloat() / width.toFloat())
            var heightRadio : Int = Math.round(options.outHeight.toFloat() / height.toFloat())
            inSampleSize = Math.min(widthRadio,heightRadio)
        }
        return inSampleSize
    }

    /**
     * Drawable To Bitmap
     */
    private fun drawableToBitamp(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 压缩图片质量
     */
    fun compressImage(bitmap: Bitmap): Bitmap {
        val baos = ByteArrayOutputStream()
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var options = 100
        //循环判断如果压缩后图片是否大于50kb,大于继续压缩
        while (baos.toByteArray().size / 1024 > 50) {
            baos.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
            options -= 10//每次都减少10
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        val isBm = ByteArrayInputStream(baos.toByteArray())
        //把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null)
    }
}