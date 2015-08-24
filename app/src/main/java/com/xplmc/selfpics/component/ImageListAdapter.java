package com.xplmc.selfpics.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.common.PictureUtils;
import com.xplmc.selfpics.common.ScaleSize;
import com.xplmc.selfpics.model.Picture;

import java.io.File;
import java.util.List;

/**
 * Created by xiaoping on 2015/8/16.
 */
public class ImageListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    private List<Picture> pictureList = null;

    public ImageListAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
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
        final String filePath = pictureList.get(position).getFilePath();
        final viewHolder mHolder;
        if(convertView == null){
            mHolder=new viewHolder();
            convertView = mInflater.inflate(R.layout.gv_item, null);
            mHolder.imageView=(ImageView)convertView.findViewById(R.id.iv);
            convertView.setTag(mHolder);

        }else {
            mHolder = (viewHolder) convertView.getTag();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final BitmapDrawable drawable = PictureUtils.getScaledDrawable((Activity)mContext, filePath, ScaleSize.THIRD);
                    mHolder.imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            mHolder.imageView.setImageDrawable(drawable);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return convertView;
    }
    class viewHolder{
        ImageView imageView;
    }
}
