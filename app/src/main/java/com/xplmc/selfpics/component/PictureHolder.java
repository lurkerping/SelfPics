package com.xplmc.selfpics.component;

import android.content.Context;

import com.xplmc.selfpics.common.CommonConstants;
import com.xplmc.selfpics.model.Picture;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoping on 2015/8/23.
 */
public class PictureHolder {

    private List<Picture> pictureList = null;

    private static final PictureHolder instance = new PictureHolder();

    private PictureHolder() {
        pictureList = new ArrayList<>();
    }

    public void initPictureList(Context context) {

        String[] fileNameList3 = context.fileList();
        if (fileNameList3 != null && fileNameList3.length > 0) {
            for (String fileName : fileNameList3) {
                File file = new File(context.getFilesDir(), fileName);
                Picture picture = new Picture();
                picture.setFilePath(file.getAbsolutePath());
                pictureList.add(picture);
            }
        }

        File[] fileList2 = context.getExternalFilesDir(CommonConstants.SECRET_PATH).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() &&
                        (pathname.getName().endsWith(CommonConstants.PHOTO_SUFFIX)
                                || pathname.getName().endsWith(CommonConstants.PHOTO_SUFFIX.toUpperCase()));
            }
        });
        if (fileList2 != null && fileList2.length > 0) {
            for (File file : fileList2) {
                Picture picture = new Picture();
                picture.setFilePath(file.getAbsolutePath());
                pictureList.add(picture);
            }
        }

    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public static PictureHolder getInstance() {
        return instance;
    }

}
