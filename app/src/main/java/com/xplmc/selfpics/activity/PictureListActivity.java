package com.xplmc.selfpics.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.component.PictureListFragment;

public class PictureListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.listContainer) == null){
            fm.beginTransaction().add(R.id.listContainer, new PictureListFragment()).commit();
        }
    }

}
