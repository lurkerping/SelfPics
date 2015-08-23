package com.xplmc.selfpics.common;

/**
 * 图片缩略图大小
 */
public enum ScaleSize {

    FULL(1), HALF(2), THIRD(3), QUARTER(4);

    private int scale = 1;

    private ScaleSize(){}

    private ScaleSize(int scale){
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
