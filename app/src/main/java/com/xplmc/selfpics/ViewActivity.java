package com.xplmc.selfpics;

import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.xplmc.selfpics.component.MainFragment;
import com.xplmc.selfpics.component.ViewFragment;
import com.xplmc.selfpics.component.common.CommonConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.xml.validation.Schema;


public class ViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        FragmentManager fm = getFragmentManager();
        ViewFragment fragment = (ViewFragment) fm.findFragmentById(R.id.viewContainer);
        if (fragment == null) {
            ViewFragment viewFragment = new ViewFragment();
            viewFragment.setUri(getIntent().getData());
            fm.beginTransaction().add(R.id.viewContainer, viewFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Uri uri = getIntent().getData();
            ContentResolver cr = this.getContentResolver();
            String fileName = UUID.randomUUID().toString() + CommonConstants.PHOTO_SUFFIX;
            OutputStream os = null;
            InputStream is = null;
            try{
                os = new BufferedOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE));
                is = new BufferedInputStream(cr.openInputStream(uri));
                byte[] buffer = new byte[1024 * 8];
                int len = 0;
                while((len = is.read(buffer)) != -1){
                    os.write(buffer, 0, len);
                }
                os.flush();
            } catch(Exception e) {
                Toast.makeText(this, "文件保存失败！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }finally {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
