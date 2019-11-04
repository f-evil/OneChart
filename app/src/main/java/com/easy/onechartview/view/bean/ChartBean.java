package com.easy.onechartview.view.bean;

import com.easy.onechartview.view.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8.bean
 * @fileNmae ChartBean
 * @date 2019/10/28 16:08
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class ChartBean implements Serializable {

    /**
     * 柱状图
     */
    public final static int CHART_SHOW_BARCHART = 0;
    /**
     * 线状图
     */
    public final static int CHART_SHOW_LINECHART = 1;

    /**
     * 数据
     */
    private List<PointBean> mValus;

    /**
     * 单位
     */
    private String unit;
    /**
     * Y轴名称
     */
    private String yAxisName;
    /**
     * 颜色
     */
    private int color;
    /**
     * 最大值
     */
    private Float maxValue;
    /**
     * 最小值
     */
    private Float minValue;
    /**
     * 展示图表类型
     */
    private int type = CHART_SHOW_BARCHART;

    public ChartBean(List<PointBean> valus, String unit, String yAxisName, int color, int type) {
        mValus = valus;
        this.unit = unit;
        this.yAxisName = yAxisName;
        this.color = color;
        this.type = type;
    }

    public List<PointBean> getValus() {
        return mValus;
    }

    public void setValus(List<PointBean> valus) {
        mValus = valus;
    }

    public String getUnit() {
        return StringUtils.removeEmpty(unit);
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getyAxisName() {
        return StringUtils.removeEmpty(yAxisName);
    }

    public void setyAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Float getMaxValue() {
        maxValue = null;

        if (mValus != null || mValus.size() > 0) {

            for (int i = 0; i < mValus.size(); i++) {
                float value = mValus.get(i).getValue();
                if (maxValue == null) {
                    maxValue = value;
                } else {
                    maxValue = Math.max(maxValue, value);
                }

            }

        }

        return maxValue;
    }

    public Float getMinValue() {
        minValue = null;

        if (mValus != null || mValus.size() > 0) {

            for (int i = 0; i < mValus.size(); i++) {
                float value = mValus.get(i).getValue();
                if (minValue == null) {
                    minValue = value;
                } else {
                    minValue = Math.min(minValue, value);
                }
            }
        }
        return minValue;
    }
}
