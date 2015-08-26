package com.xplmc.selfpics.component;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.activity.ViewPagerActivity;
import com.xplmc.selfpics.common.CommonConstants;
import com.xplmc.selfpics.model.Picture;

import java.util.List;

/**
 * Created by xiaoping on 2015/8/19.
 */
public class PictureListFragment extends Fragment {

    public static final String TAG = "ListFragment";

    private int mImageThumbSize;

    private int mImageThumbSpacing;

    private GridView mGridView = null;

    private ImageListAdapter mAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        mAdapter = new ImageListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mGridView = (GridView) v.findViewById(R.id.gv_list);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Picture> pictureList = PictureHolder.getInstance().getPictureList();
                Picture picture = pictureList.get(position);

                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                intent.putExtra(CommonConstants.EXTRA_FILE_PATH, picture.getFilePath());
                startActivity(intent);
            }
        });
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (mAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(
                                    mGridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                            if (numColumns > 0) {
                                final int columnWidth =
                                        (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                                mAdapter.setNumColumns(numColumns);
                                mAdapter.setItemHeight(columnWidth);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    mGridView.getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                } else {
                                    mGridView.getViewTreeObserver()
                                            .removeGlobalOnLayoutListener(this);
                                }
                            }
                        }
                    }
                });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
