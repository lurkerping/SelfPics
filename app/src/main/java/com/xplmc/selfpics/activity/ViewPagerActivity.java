package com.xplmc.selfpics.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.common.CommonConstants;
import com.xplmc.selfpics.component.PictureHolder;
import com.xplmc.selfpics.component.ViewFragment;
import com.xplmc.selfpics.model.Picture;

import java.util.List;

/**
 * Created by xiaoping on 2015/8/23.
 */
public class ViewPagerActivity extends FragmentActivity {

    private ViewPager mViewPager = null;

    private List<Picture> pictureList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pictureList = PictureHolder.getInstance().getPictureList();

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.vp_viewer);
        setContentView(mViewPager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Picture picture = pictureList.get(position);
                ViewFragment viewFragment = ViewFragment.newInstance(picture.getFilePath());
                return viewFragment;
            }

            @Override
            public int getCount() {
                return pictureList.size();
            }
        });

        //选中当前图片
        String currentFilePath = getIntent().getStringExtra(CommonConstants.EXTRA_FILE_PATH);
        for(int i = 0; i < pictureList.size(); i++){
            Picture picture = pictureList.get(i);
            if(picture.getFilePath().equals(currentFilePath)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }



}
