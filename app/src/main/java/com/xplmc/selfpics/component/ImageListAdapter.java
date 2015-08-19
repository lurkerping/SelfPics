package com.xplmc.selfpics.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xplmc.selfpics.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by xiaoping on 2015/8/16.
 */
public class ImageListAdapter extends BaseAdapter {

    private Context mContext;

    private String[] fileNameList = null;

    public ImageListAdapter(Context c) {
        mContext = c;
        fileNameList = mContext.fileList();
    }

    @Override
    public int getCount() {
        return fileNameList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ImageView imageView;
        final String fileName = fileNameList[position];
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }else {
            imageView = (ImageView)convertView;
        }
        imageView.setImageResource(R.drawable.handsome);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    File file = new File(mContext.getFilesDir(), fileName);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 4;
                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), bmOptions);
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return imageView;
    }

}
