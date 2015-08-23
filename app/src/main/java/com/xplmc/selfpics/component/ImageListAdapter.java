package com.xplmc.selfpics.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xplmc.selfpics.common.PictureUtils;
import com.xplmc.selfpics.common.ScaleSize;
import com.xplmc.selfpics.model.Picture;

import java.io.File;
import java.util.List;

/**
 * Created by xiaoping on 2015/8/16.
 */
public class ImageListAdapter extends BaseAdapter {

    private Context mContext;

    private List<Picture> pictureList = null;

    public ImageListAdapter(Context c) {
        mContext = c;
        pictureList = PictureHolder.getInstance().getPictureList();
    }

    @Override
    public int getCount() {
        return pictureList.size();
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
        final String filePath = pictureList.get(position).getFilePath();
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final BitmapDrawable drawable = PictureUtils.getScaledDrawable((Activity)mContext, filePath, ScaleSize.THIRD);
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageDrawable(drawable);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            imageView = (ImageView)convertView;
        }
        return imageView;
    }

}
