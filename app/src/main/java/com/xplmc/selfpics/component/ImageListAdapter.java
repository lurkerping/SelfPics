package com.xplmc.selfpics.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by xiaoping on 2015/8/16.
 */
public class ImageListAdapter extends BaseAdapter {

    public static final String TAG = "ImageListAdapter";

    private int numColumns = 0;
    private int itemHeight = 0;
    private LayoutInflater mInflater;
    private Context mContext;
    private Bitmap mLoadingBitmap;
    private GridView.LayoutParams mImageViewLayoutParams;

    private List<Picture> pictureList = null;

    public ImageListAdapter(Context c) {
        mContext = c;
        mImageViewLayoutParams = new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mInflater = LayoutInflater.from(mContext);
        pictureList = PictureHolder.getInstance().getPictureList();
        setLoadingImage();
    }

    public void setLoadingImage() {
        mLoadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.handsome);
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
        mImageViewLayoutParams = new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
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
        final ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(mImageViewLayoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        if (imageView.getLayoutParams().height != itemHeight) {
            imageView.setLayoutParams(mImageViewLayoutParams);
        }
        this.loadBitmap(filePath, imageView);
        return imageView;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getTask();
            }
        }
        return null;
    }

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String filePath = bitmapWorkerTask.filePath;
            // If bitmapData is not yet set or it differs from the new data
            if (filePath != null || filePath != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    public void loadBitmap(String filePath, ImageView imageView) {
        if (cancelPotentialWork(filePath, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), mLoadingBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(filePath);
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {
        private final WeakReference<ImageView> imageViewReference;
        private String filePath = null;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            filePath = params[0];
            return PictureUtils.getScaledDrawable((Activity) mContext, filePath, ScaleSize.THIRD);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            if (isCancelled()) {
                bitmapDrawable = null;
            }

            if (imageViewReference != null && bitmapDrawable != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageDrawable(bitmapDrawable);
                }
            }
        }
    }

    class AsyncDrawable extends BitmapDrawable {

        private WeakReference<BitmapWorkerTask> task = null;

        public AsyncDrawable(Resources rs, Bitmap bitmap, BitmapWorkerTask task) {
            super(rs, bitmap);
            this.task = new WeakReference<BitmapWorkerTask>(task);
        }

        public BitmapWorkerTask getTask() {
            return this.task.get();
        }

    }

}
