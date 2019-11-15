## OneChart项目介绍

### 0. 系列文章
- [1. 自定义View入门学习-OneChart](http://www.nooocat.com/index.php/2019/11/04/283/)
- [2. 自定义View入门学习-WheelView](http://www.nooocat.com/index.php/2019/11/15/296/)

### 1. 原始效果图
![原始效果图](http://www.nooocat.com/wp-content/uploads/2019/11/TIM截图20191028133344.jpg)

### 2. Demo效果图
![Demo效果图](http://www.nooocat.com/wp-content/uploads/2019/11/b568df4be026857d78b1113050b661a.jpg)

### 3. 解决问题
- 一图一表
- 一图多表数据对比

### 4. 由来
- 组内学习自定义View的基础Demo

### 5. 包含内容
- 基础 Paint 的使用
- 基础 Canvas 的 drawText，drawLine ，drawPath 等 的使用
- 基础 Canvas 的 translate，sava，restore 的使用
- 基础 Path 的使用
- 基础 Matrix 坐标转换的使用
- 基础 Region 特殊区域触摸事件处理
- 基础 AttributeSet 使用
- 基础 数据结构设计

### 6. 使用

#### 6.1 属性介绍

``` xml
<declare-styleable name="OneChartView">
        <!--    标题名    -->
        <attr name="one_title_name" format="string" />
        <!--    标题颜色    -->
        <attr name="one_title_color" format="color" />
        <!--    标题字号    -->
        <attr name="one_title_size" format="dimension" />
        <!--    副标题名称    -->
        <attr name="one_subtitle_name" format="string" />
        <!--    副标题颜色   -->
        <attr name="one_subtitle_color" format="color" />
        <!--    副标题字号    -->
        <attr name="one_subtitle_size" format="dimension" />
        <!--    x轴线颜色    -->
        <attr name="one_x_axis_color" format="color" />
        <!--    x轴线宽度    -->
        <attr name="one_x_axis_size" format="dimension" />
        <!--    x轴文字颜色    -->
        <attr name="one_x_axis_text_color" format="color" />
        <!--    x轴文字字号    -->
        <attr name="one_x_axis_text_size" format="dimension" />
    </declare-styleable>
```
#### 6.2 使用办法

``` xml
<com.easy.onechartview.view.OneChartView
        android:id="@+id/xchart"
        android:layout_width="match_parent"
        android:layout_height="300dp" />
```

### 7. 项目解析

#### 7.1 组织数据结构

##### 7.1.1 表的组成部分
- 图表类型
- 数据集
- 单位
- Y轴名称
- 颜色

``` java
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
}
```

##### 7.1.2 多表的组合
- 表名
- 副表名
- 图例（暂时未加）
- 表集

``` java
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
}
```

##### 7.1.3 数据结构（图）
![enter description here](http://www.nooocat.com/wp-content/uploads/2019/11/XChartDataBean.png)

#### 7.2 难点分析

##### 7.2.1 图像区域分割

- 纵向上
1. 分割出标题高度
2. 分割出副标题高度
3. 分割Y轴名称高度
4. 分割数据部分高度
5. 分割X轴文字部分高度

- 横向上
1. 分割左侧Y轴宽度
2. 分割中间数据区 宽度
3. 分割右侧多个Y轴的 宽度

##### 7.2.2 数据间隔与点计算

1. 数据区域部分宽度一定，按照数据个数分割区域
2. 计算各个区域 x轴中心点
3. 计算柱状图，折线图点数据

##### 7.2.3 坐标转换
- 使用 Matrix 矩阵方式转换

##### 7.2.3 点击区域判定
- 使用 Region 区域判定

#### 7.3 绘制顺序梳理
1. 绘制标题
2. 绘制背景(包含x轴，y轴背景线)
3. 绘制左侧坐标轴
4. 绘制中间数据
5. 绘制右侧坐标轴

### 8. 博客地址
[OneChart](http://www.nooocat.com/index.php/2019/11/04/283/)


