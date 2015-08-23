package com.xplmc.selfpics.model;

import java.io.Serializable;

/**
 * Created by xiaoping on 2015/8/23.
 */
public class Picture implements Serializable{

    private String filePath = null;

    public Picture(){}

    public Picture(String filePath ){
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
