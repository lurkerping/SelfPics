package com.xplmc.selfpics;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.xplmc.selfpics.component.ImageListAdapter;

public class ListActivity extends ActionBarActivity {

    private GridView mGridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mGridView = (GridView)findViewById(R.id.gv_list);
        mGridView.setAdapter(new ImageListAdapter(this));
    }

}
