package com.xplmc.selfpics.component;

import android.app.Fragment;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xplmc.selfpics.R;

import java.io.FileNotFoundException;

/**
 * Created by xiaoping on 2015/8/19.
 */
public class ViewFragment extends Fragment {

    private ImageView mIvView = null;

    private Uri uri = null;

    public ViewFragment(){}

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        mIvView = (ImageView) v.findViewById(R.id.iv_view);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.renderImages(uri);
    }

    private void renderImages(Uri uri){

        Bitmap bitmap = null;
        if("content".equals(uri.getScheme())){
            ContentResolver cr = getActivity().getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }else if("file".equals(uri.getScheme())){
            bitmap = BitmapFactory.decodeFile(uri.getPath());
        }
        if(bitmap != null){
            mIvView.setImageBitmap(bitmap);
        }
    }

}
