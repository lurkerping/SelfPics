package com.xplmc.selfpics.component;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.common.PictureUtils;

/**
 * Created by xiaoping on 2015/8/19.
 */
public class ViewFragment extends Fragment {

    public static final String TAG = "ViewFragment";

    public static final String KEY_FILE_PATH = "filePath";

    private ImageView mIvView = null;

    private String filePath = null;

    public ViewFragment(){}

    public static ViewFragment newInstance(String filePath){
        ViewFragment viewFragment = new ViewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FILE_PATH, filePath);
        viewFragment.setArguments(args);
        return viewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = getArguments() != null ? getArguments().getString(KEY_FILE_PATH) : null;
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
    }

    @Override
    public void onStart() {
        super.onStart();
        this.renderImages();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mIvView);
    }

    private void renderImages(){

        Drawable drawable = PictureUtils.getScaledDrawable(getActivity(), filePath);
        mIvView.setImageDrawable(drawable);
    }

}
