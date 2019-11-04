package com.easy.onechartview.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8
 * @fileNmae XChatDataBean
 * @date 2019/10/28 13:47
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class XChatDataBean implements Serializable {

    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 图例
     */
    private List<LegendBean> legends;
    /**
     * 数据
     */
    private List<ChartBean> charts;

    public XChatDataBean(String title, String subTitle, List<ChartBean> charts) {
        this.title = title;
        this.subTitle = subTitle;
        this.charts = charts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<ChartBean> getCharts() {
        return charts;
    }

    public void setCharts(List<ChartBean> charts) {
        this.charts = charts;
    }

    public List<LegendBean> getLegends() {

        if (legends == null) {
            legends = new ArrayList<>();
        }

        legends.clear();

        if (charts == null || charts.size() == 0) {
            return legends;
        }

        for (ChartBean axisBean : charts) {
            legends.add(new LegendBean(axisBean.getColor(), axisBean.getyAxisName()));
        }

        return legends;
    }

}
