package com.easy.onechartview;

import android.os.Bundle;
import android.view.View;

import com.easy.onechartview.view.OneChartView;
import com.easy.onechartview.view.bean.ChartBean;
import com.easy.onechartview.view.bean.PointBean;
import com.easy.onechartview.view.bean.XChatDataBean;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private OneChartView mXchart;
    boolean is = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mXchart = (OneChartView) findViewById(R.id.xchart);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is) {
                    chart1();
                } else {
                    chart2();
                }

                is = !is;
            }
        });


    }

    private void chart2() {

        String[] name = new String[]{
                "1",
                "2",
                "3",
                "4",
                "5",
                "6"
        };

        int[] color = new int[]{
                R.color.color_1,
                R.color.color_2,
                R.color.color_3,
                R.color.color_4,
                R.color.color_5,
                R.color.color_6
        };

        List<ChartBean> mChartBean = new ArrayList<>();

        List<PointBean> pointBeans = new ArrayList<>();
        pointBeans.add(new PointBean(1, name[4]));
        pointBeans.add(new PointBean(5, name[5]));
        pointBeans.add(new PointBean(3, name[0]));
        pointBeans.add(new PointBean(4, name[1]));
        pointBeans.add(new PointBean(5, name[2]));

        ChartBean chartBean = new ChartBean(pointBeans, "ml", "水容量", 0xFFef5350, ChartBean.CHART_SHOW_BARCHART);
        mChartBean.add(chartBean);

        List<PointBean> pointBeans1 = new ArrayList<>();
        pointBeans1.add(new PointBean(3, name[0]));
        pointBeans1.add(new PointBean(4, name[1]));
        pointBeans1.add(new PointBean(5, name[2]));
        pointBeans1.add(new PointBean(2, name[3]));
        pointBeans1.add(new PointBean(1, name[4]));
        pointBeans1.add(new PointBean(5, name[5]));

        ChartBean chartBean1 = new ChartBean(pointBeans1, "", "转速", 0xFFAB47BC, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean1);

        List<PointBean> pointBeans2 = new ArrayList<>();
        pointBeans2.add(new PointBean(3, name[0]));
        pointBeans2.add(new PointBean(14, name[1]));
        pointBeans2.add(new PointBean(5, name[2]));
        pointBeans2.add(new PointBean(12, name[3]));
        pointBeans2.add(new PointBean(1, name[4]));
        pointBeans2.add(new PointBean(8, name[5]));

        ChartBean chartBean2 = new ChartBean(pointBeans2, " W", "耗电量", 0xFFFFCA28, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean2);

        List<PointBean> pointBeans3 = new ArrayList<>();
        pointBeans3.add(new PointBean(13, name[0]));
        pointBeans3.add(new PointBean(4, name[1]));
        pointBeans3.add(new PointBean(15, name[2]));
        pointBeans3.add(new PointBean(2, name[3]));
        pointBeans3.add(new PointBean(11, name[4]));
        pointBeans3.add(new PointBean(5, name[5]));

        ChartBean chartBean3 = new ChartBean(pointBeans3, "", "测试", 0xFF4CAF50, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean3);

        List<PointBean> pointBeans4 = new ArrayList<>();
        pointBeans4.add(new PointBean(23, name[0]));
        pointBeans4.add(new PointBean(45, name[1]));
        pointBeans4.add(new PointBean(5, name[2]));
        pointBeans4.add(new PointBean(2, name[3]));
        pointBeans4.add(new PointBean(50, name[4]));
        pointBeans4.add(new PointBean(5, name[5]));

        ChartBean chartBean4 = new ChartBean(pointBeans4, "", "排风量", 0xFF3D5AFE, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean4);


        mXchart.setXChatData(new XChatDataBean("", "", mChartBean));

    }

    private void chart1() {

        String[] name = new String[]{
                "1",
                "2",
                "3",
                "4",
                "5",
                "6"
        };

        int[] color = new int[]{
                R.color.color_1,
                R.color.color_2,
                R.color.color_3,
                R.color.color_4,
                R.color.color_5,
                R.color.color_6
        };

        List<ChartBean> mChartBean = new ArrayList<>();

        List<PointBean> pointBeans = new ArrayList<>();
        pointBeans.add(new PointBean(3, name[0]));
        pointBeans.add(new PointBean(4, name[1]));
        pointBeans.add(new PointBean(5, name[2]));
        pointBeans.add(new PointBean(2, name[3]));
        pointBeans.add(new PointBean(1, name[4]));
        pointBeans.add(new PointBean(5, name[5]));

        ChartBean chartBean = new ChartBean(pointBeans, "ml", "水容量", 0xFFef5350, ChartBean.CHART_SHOW_BARCHART);
        mChartBean.add(chartBean);

        List<PointBean> pointBeans1 = new ArrayList<>();
        pointBeans1.add(new PointBean(13, name[0]));
        pointBeans1.add(new PointBean(4, name[1]));
        pointBeans1.add(new PointBean(15, name[2]));
        pointBeans1.add(new PointBean(2, name[3]));
        pointBeans1.add(new PointBean(1, name[4]));
        pointBeans1.add(new PointBean(5, name[5]));

        ChartBean chartBean1 = new ChartBean(pointBeans1, "", "转速", 0xFFAB47BC, ChartBean.CHART_SHOW_LINECHART);
        mChartBean.add(chartBean1);

        List<PointBean> pointBeans2 = new ArrayList<>();
        pointBeans2.add(new PointBean(113, name[0]));
        pointBeans2.add(new PointBean(14, name[1]));
        pointBeans2.add(new PointBean(25, name[2]));
        pointBeans2.add(new PointBean(12, name[3]));
        pointBeans2.add(new PointBean(66, name[4]));
        pointBeans2.add(new PointBean(8, name[5]));

        ChartBean chartBean2 = new ChartBean(pointBeans2, "W", "耗电量", 0xFFFFCA28, ChartBean.CHART_SHOW_LINECHART);
        mChartBean.add(chartBean2);
//
//        List<PointBean> pointBeans3 = new ArrayList<>();
//        pointBeans3.add(new PointBean(13, name[0]));
//        pointBeans3.add(new PointBean(4, name[1]));
//        pointBeans3.add(new PointBean(15, name[2]));
//        pointBeans3.add(new PointBean(2, name[3]));
//        pointBeans3.add(new PointBean(11, name[4]));
//        pointBeans3.add(new PointBean(5, name[5]));
//
//        ChartBean chartBean3 = new ChartBean(pointBeans3, "", "测试", 0xFF4CAF50, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean3);
//
//        List<PointBean> pointBeans4 = new ArrayList<>();
//        pointBeans4.add(new PointBean(23, name[0]));
//        pointBeans4.add(new PointBean(45, name[1]));
//        pointBeans4.add(new PointBean(5, name[2]));
//        pointBeans4.add(new PointBean(2, name[3]));
//        pointBeans4.add(new PointBean(50, name[4]));
//        pointBeans4.add(new PointBean(5, name[5]));
//
//        ChartBean chartBean4 = new ChartBean(pointBeans4, "", "排风量", 0xFF3D5AFE, ChartBean.CHART_SHOW_LINECHART);
//        mChartBean.add(chartBean4);


        mXchart.setXChatData(new XChatDataBean("蚁群优化算法在求解最短路径", "2019.10.30", mChartBean));
//        mXchart.setXChatData(new XChatDataBean("", "2019.10.30", mChartBean));
//        mXchart.setXChatData(new XChatDataBean("蚁群优化算法在求解最短路径", "", mChartBean));

    }
}
