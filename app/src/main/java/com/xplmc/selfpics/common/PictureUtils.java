package com.xplmc.selfpics.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by xiaoping on 2015/8/22.
 */
public class PictureUtils {

    public static final String TAG = "PictureUtils";

    public static BitmapDrawable getScaledDrawable(Activity a, String fileName){
        return getScaledDrawable(a, fileName, ScaleSize.FULL);
    }

    /**
     * 根据设备屏幕大小返回图片
     * @param a
     * @param fileName
     * @return
     */
    public static BitmapDrawable getScaledDrawable(Activity a, String fileName, ScaleSize scaleSize){

        //获取设备屏幕大小
        Display display = a.getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);

        //获取图片原始大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);

        //获取缩略比例
        int sampleSize = 1;
        if(options.outWidth > outSize.x){
            sampleSize = Math.round(options.outWidth * scaleSize.getScale() / outSize.x);
        }else if(options.outHeight > outSize.y){
            sampleSize = Math.round(options.outHeight * scaleSize.getScale() / outSize.y);
        }else{
            sampleSize = scaleSize.getScale();
        }

        Log.d(TAG, fileName + "--" + String.valueOf(sampleSize));
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Bitmap b = BitmapFactory.decodeFile(fileName, options);
        return new BitmapDrawable(a.getResources(), b);
    }

    public static void cleanImageView(ImageView imageView){

        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null){
                bitmapDrawable.getBitmap().recycle();
            }
            imageView.setImageDrawable(null);
        }
    }

}
