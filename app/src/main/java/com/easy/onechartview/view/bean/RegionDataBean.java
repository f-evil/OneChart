package com.easy.onechartview.view.bean;

import android.graphics.Region;

import java.io.Serializable;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8.bean
 * @fileNmae RegionDataBean
 * @date 2019/11/4 11:48
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class RegionDataBean implements Serializable {

    private int chartIndex = -1;
    private int dataIndex = -1;
    private Region mRegion;

    public RegionDataBean() {
    }

    public RegionDataBean(int chartIndex, int dataIndex, Region region) {
        this.chartIndex = chartIndex;
        this.dataIndex = dataIndex;
        mRegion = region;
    }

    public int getChartIndex() {
        return chartIndex;
    }

    public void setChartIndex(int chartIndex) {
        this.chartIndex = chartIndex;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }

    public Region getRegion() {
        return mRegion;
    }

    public void setRegion(Region region) {
        mRegion = region;
    }
}
