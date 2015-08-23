package com.xplmc.selfpics.component;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private GridView mGridView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mGridView = (GridView)v.findViewById(R.id.gv_list);
        mGridView.setAdapter(new ImageListAdapter(getActivity()));
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
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
