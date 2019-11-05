### 1. 原始效果图
<a href="img/demo.jpg"><img src="http://www.nooocat.com/wp-content/uploads/2019/11/2.jpg" width="80%"/></a>

### 2. Demo效果图
<a href="img/1.jpg"><img src="http://www.nooocat.com/wp-content/uploads/2019/11/b568df4be026857d78b1113050b661a.jpg" width="80%"/></a>

### 3. 解决问题
- 一图一表
- 一图多表数据对比

### 4. OneChart的由来
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

##### 6.1 属性介绍

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
##### 6.2 使用办法

``` xml
<com.easy.onechartview.view.OneChartView
        android:id="@+id/xchart"
        android:layout_width="match_parent"
        android:layout_height="300dp" />
```

### 7. 项目解析

