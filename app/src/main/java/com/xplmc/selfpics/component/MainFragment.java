package com.xplmc.selfpics.component;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.activity.PictureListActivity;
import com.xplmc.selfpics.common.Side;

/**
 * Created by xiaoping on 2015/8/19.
 */
public class MainFragment extends Fragment {

    private TextView mTvLeft = null;

    private TextView mTvRight = null;

    private OnLeftRightClickListener mContext = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (OnLeftRightClickListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mTvLeft = (TextView) v.findViewById(R.id.tv_left);
        mTvRight = (TextView) v.findViewById(R.id.tv_right);
        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onLeftRightClick(Side.LEFT);
            }
        });

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onLeftRightClick(Side.RIGHT);
            }
        });
        return v;
    }

    public static interface OnLeftRightClickListener {

        void onLeftRightClick(Side side);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            Intent intent = new Intent(getActivity(), PictureListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
