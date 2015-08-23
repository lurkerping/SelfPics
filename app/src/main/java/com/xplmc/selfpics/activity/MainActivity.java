package com.xplmc.selfpics.activity;

import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.common.CommonConstants;
import com.xplmc.selfpics.component.MainFragment;
import com.xplmc.selfpics.common.Side;
import com.xplmc.selfpics.component.PictureHolder;
import com.xplmc.selfpics.model.Picture;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity implements MainFragment.OnLeftRightClickListener {

    public static final String TAG = "MainActivity";

    public static final int RESULT_CODE_LEFT = 1;
    public static final int RESULT_CODE_RIGHT = 2;

    private String mCurrentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        MainFragment fragment = (MainFragment) fm.findFragmentById(R.id.mainContainer);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.mainContainer, new MainFragment()).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            Intent intent = new Intent(this, PictureListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CODE_LEFT:
                    break;
                case RESULT_CODE_RIGHT:
                    Uri uri = data.getData();
                    copyImage(uri);
                    break;
                default:
                    break;
            }
            if(mCurrentPhotoPath != null){
                Picture picture = new Picture(mCurrentPhotoPath);
                List<Picture> pictureList = PictureHolder.getInstance().getPictureList();
                pictureList.add(picture);

                Intent intent = new Intent(this, ViewPagerActivity.class);
                intent.putExtra(CommonConstants.EXTRA_FILE_PATH, mCurrentPhotoPath);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "失败了！", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLeftRightClick(Side side) {
        switch (side) {
            case LEFT:
                onLeftClick();
                break;
            case RIGHT:
                onRightClick();
                break;
            default:
                break;
        }

    }

    public void onLeftClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, ex.getMessage(), ex);
                Toast.makeText(MainActivity.this, "创建文件失败！", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, RESULT_CODE_LEFT);
            }
        }
    }

    public void onRightClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_CODE_RIGHT);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(CommonConstants.SECRET_PATH);
        if (storageDir.exists() == false) {
            storageDir.mkdir();
        }
        File image = new File(storageDir, imageFileName);
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, mCurrentPhotoPath);
        return image;
    }

    private void copyImage(Uri uri){

        ContentResolver cr = this.getContentResolver();
        OutputStream os = null;
        InputStream is = null;
        try{
            os = new FileOutputStream(createImageFile());
            is = new BufferedInputStream(cr.openInputStream(uri));
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch(Exception e) {
            Toast.makeText(this, "文件保存失败！", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

}
