package com.easy.onechartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.easy.onechartview.R;
import com.easy.onechartview.view.bean.ChartBean;
import com.easy.onechartview.view.bean.PointBean;
import com.easy.onechartview.view.bean.RegionDataBean;
import com.easy.onechartview.view.bean.XChatDataBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8
 * @fileNmae XChartView
 * @date 2019/10/28 9:52
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class OneChartView extends View {

    private static final String TAG = "OneChartView";

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 表数据
     */
    private XChatDataBean mXChatData;
    /**
     * View宽度
     */
    private int mWidth;
    /**
     * 坐标系之间间距
     */
    private int mAxisPaddingWidth = 0;
    /**
     * 自定义柱状图宽
     */
    private float mCusBarWidth = 0;
    /**
     * 右侧坐标系总宽度
     */
    private int mRightYWidth = 0;
    /**
     * 各坐标系Y轴宽度
     */
    private List<Integer> mAxisWidths;
    /**
     * 左侧横坐标系宽度（y轴+x轴）
     */
    private int mLeftChartWidth = 0;
    /**
     * View高度
     */
    private int mHeight;
    /**
     * 图表区域高度
     */
    private int mChartHeight;
    /**
     * X轴小短线长度
     */
    private int mXAxisSmallLineWidth;
    /**
     * 折线图点半径
     */
    private int mPointReadius;

    /**
     * 单格高度
     */
    private int mBaseValueBlockHeight;
    /**
     * 标题
     */
    private String mTitle;
    /**
     * 标题
     */
    private String mTitleFromAttr;
    /**
     * 副标题
     */
    private String mSubTitle;
    /**
     * 副标题
     */
    private String mSubTitleFromAttr;
    /**
     * 背景线画笔
     */
    private Paint mTitlePaint;
    /**
     * 背景线画笔
     */
    private Paint mSubTitlePaint;
    /**
     * 背景线画笔
     */
    private Paint mChartLinePaint;
    /**
     * x轴文字画笔
     */
    private Paint mXAxisTextPaint;
    /**
     * 内容画笔
     */
    private List<Paint> mValuePaints;
    /**
     * 轴文字大小
     */
    private int mAxisTextSize;
    /**
     * 轴文字大小
     */
    private int mTitleTextSize;
    /**
     * 轴文字大小
     */
    private int mSubTitleTextSize;
    /**
     * x轴文字高度
     */
    private int mXAxisTextHeight;
    /**
     * 表格横线部分颜色
     */
    private int mChartLineColor = 0xFFABB8C5;
    /**
     * 表格横线部分线粗
     */
    private float mChartLineStrokeWidth = 3;
    /**
     * x轴文字颜色
     */
    private int mXAxisTextColor = 0xFF606972;
    /**
     * title文字颜色
     */
    private int mTitleTextColor = Color.BLACK;
    /**
     * subtitle文字颜色
     */
    private int mSubTitleTextColor = Color.GRAY;
    /**
     * 全局
     */
    private Region globalRegion;
    /**
     * 点击触控
     */
    private List<RegionDataBean> mRegions;
    /**
     * 坐标转换
     */
    private Matrix mMapMatrix = null;

    public OneChartView(Context context) {
        this(context, null);
    }

    public OneChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initAttrs(context, attrs);
        initDefaultPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (attrs == null) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OneChartView);
        if (ta == null) {
            return;
        }

        mTitleFromAttr = ta.getString(R.styleable.OneChartView_one_title_name);
        mTitleTextSize = (int) ta.getDimension(R.styleable.OneChartView_one_title_size, mTitleTextSize);
        mTitleTextColor = ta.getColor(R.styleable.OneChartView_one_title_color, mTitleTextColor);

        mSubTitleFromAttr = ta.getString(R.styleable.OneChartView_one_subtitle_name);
        mSubTitleTextSize = (int) ta.getDimension(R.styleable.OneChartView_one_subtitle_size, mSubTitleTextSize);
        mSubTitleTextColor = ta.getColor(R.styleable.OneChartView_one_subtitle_color, mSubTitleTextColor);

        mChartLineColor = ta.getColor(R.styleable.OneChartView_one_x_axis_color, mChartLineColor);
        mChartLineStrokeWidth = ta.getDimension(R.styleable.OneChartView_one_x_axis_size, mChartLineStrokeWidth);

        mXAxisTextColor = ta.getColor(R.styleable.OneChartView_one_x_axis_text_color, mXAxisTextColor);
        mAxisTextSize = (int) ta.getDimension(R.styleable.OneChartView_one_x_axis_text_size, mAxisTextSize);

        ta.recycle();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - getPaddingStart() - getPaddingEnd();
        mHeight = h - getPaddingTop() - getPaddingBottom();

        mChartHeight = mHeight;

        mBaseValueBlockHeight = (int) (mChartHeight / 5.5);

        mMapMatrix.reset();

        globalRegion.set(-w, -h, w, h);

    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mXChatData == null) {
            return;
        }

        if (mXChatData.getCharts() == null) {
            return;
        }

        if (mXChatData.getCharts().size() == 0) {
            return;
        }

        canvas.translate(getPaddingStart(), getPaddingTop());

        //绘制标题/副标题
        drawTitle(canvas);

        //绘制背景
        drawChartBackground(canvas);

        //绘制左侧Y轴
        drawLeftAxis(canvas, 0, mXChatData.getCharts().get(0));

        //绘制X轴
        List<Float> tPoints = drawXAxis(canvas, 0, mXChatData.getCharts().get(0));

        //绘制图表
        for (int chartIndex = 0; chartIndex < mXChatData.getCharts().size(); chartIndex++) {
            drawLeftChart(canvas, tPoints, chartIndex, mXChatData.getCharts().get(chartIndex));
        }

        //绘制右侧坐标轴
        if (mXChatData.getCharts().size() > 1) {
            for (int i = 1; i < mXChatData.getCharts().size(); i++) {
                drawRightAxis(canvas, i, mXChatData.getCharts().get(i));
            }
        }

        //绘制图例
        drawLegends(canvas);

    }

    /**
     * 绘制图例,下一期新增
     */
    private void drawLegends(Canvas canvas) {

    }

    /**
     * 初始化各类长宽
     */
    private void init() {
        mAxisTextSize = SizeUtils.sp2px(mContext, 12);
        mTitleTextSize = SizeUtils.sp2px(mContext, 16);
        mSubTitleTextSize = SizeUtils.sp2px(mContext, 14);
        mXAxisSmallLineWidth = SizeUtils.dp2px(mContext, 6);
        mPointReadius = SizeUtils.dp2px(mContext, 4);
        mAxisPaddingWidth = SizeUtils.dp2px(mContext, 6);

        mRegions = new ArrayList<>();
        globalRegion = new Region();

        mMapMatrix = new Matrix();

    }

    /**
     * 初始化画笔
     */
    private void initDefaultPaint() {
        mValuePaints = new ArrayList<>();
        mAxisWidths = new ArrayList<>();

        mChartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mChartLinePaint.setColor(mChartLineColor);
        mChartLinePaint.setStrokeWidth(mChartLineStrokeWidth);
        mChartLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mXAxisTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXAxisTextPaint.setColor(mXAxisTextColor);
        mXAxisTextPaint.setStyle(Paint.Style.FILL);
        mXAxisTextPaint.setTextSize(mAxisTextSize);

        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(mTitleTextColor);
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setTextSize(mTitleTextSize);

        mSubTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSubTitlePaint.setColor(mSubTitleTextColor);
        mSubTitlePaint.setStyle(Paint.Style.FILL);
        mSubTitlePaint.setTextSize(mSubTitleTextSize);

        Rect rect = new Rect();
        mXAxisTextPaint.getTextBounds("1", 0, "1".length(), rect);
        mXAxisTextHeight = rect.height();
    }

    /**
     * 初始化数据，每次set之后都需要进行
     */
    private void initData() {

        mRegions = new ArrayList<>();

        mMapMatrix.reset();

        mValuePaints.clear();
        mAxisWidths.clear();

        mRightYWidth = 0;
        mChartHeight = mHeight;

        if (mXChatData == null) {
            return;
        }

        mTitle = mXChatData.getTitle();
        mSubTitle = mXChatData.getSubTitle();

        if (!StringUtils.isNoEmpey(mTitle)) {
            mTitle = mTitleFromAttr;
        }
        if (!StringUtils.isNoEmpey(mSubTitle)) {
            mSubTitle = mSubTitleFromAttr;
        }

        if (StringUtils.isNoEmpey(mSubTitle)) {
            mChartHeight = (int) (mChartHeight - getTextWidthAndHeight(mSubTitlePaint, mSubTitle).height() * 3);
        }

        if (StringUtils.isNoEmpey(mTitle)) {
            mChartHeight = (int) (mChartHeight - getTextWidthAndHeight(mTitlePaint, mTitle).height() * 1.5);
        }

        //减去Y轴名称高度
        mChartHeight = mChartHeight - mXAxisTextHeight * 2;

        mBaseValueBlockHeight = (int) (mChartHeight / 5.5);

        List<ChartBean> charts = mXChatData.getCharts();
        for (int i = 0; i < charts.size(); i++) {

            ChartBean chartBean = charts.get(i);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(chartBean.getColor());
            paint.setTextSize(mAxisTextSize);
            paint.setStrokeWidth(3);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.FILL);
            mValuePaints.add(paint);

            if (i == 0) {
                int number = getJudgeYAxisValue(chartBean.getMaxValue());

                String text = number + chartBean.getUnit();

                Rect rect = new Rect();
                paint.getTextBounds(text, 0, text.length(), rect);

                mAxisWidths.add((int) ((rect.width() + mXAxisSmallLineWidth) * 1.2));

                continue;

            }

            if (1 == charts.size()) {
                mRightYWidth = 0;
                continue;
            }

            int textAndDot = 0;
            int number = getJudgeYAxisValue(chartBean.getMaxValue());
            Rect rect = new Rect();
            paint.getTextBounds(String.valueOf(number), 0, String.valueOf(number).length(), rect);
            textAndDot = mXAxisSmallLineWidth + mAxisPaddingWidth + rect.width() + mAxisPaddingWidth;


            int axisAndDot = 0;
            String axisName = chartBean.getyAxisName();
            Rect axisRect = new Rect();
            paint.getTextBounds(axisName, 0, axisName.length(), axisRect);
            axisAndDot = axisRect.width() + mAxisPaddingWidth;

            int max = Math.max(textAndDot, axisAndDot);

            mRightYWidth = mRightYWidth + max;
            mAxisWidths.add(max);

        }

    }


    /**
     * 绘制标题/副标题
     */
    private void drawTitle(Canvas canvas) {

        canvas.save();

        canvas.translate(mWidth / 2, 0);

        Rect mTitleRect = null;
        if (StringUtils.isNoEmpey(mTitle)) {
            mTitleRect = getTextWidthAndHeight(mTitlePaint, mTitle);
            canvas.drawText(mTitle, -mTitleRect.width() / 2, (float) (mTitleRect.height()), mTitlePaint);
        }

        if (StringUtils.isNoEmpey(mSubTitle)) {

            Rect rect = getTextWidthAndHeight(mSubTitlePaint, mSubTitle);
            if (mTitleRect != null) {
                canvas.translate(0, (float) (mTitleRect.height()));
            }
            canvas.drawText(mSubTitle, -rect.width() / 2, (float) (rect.height() * 2), mSubTitlePaint);
        }


        canvas.restore();
    }

    /**
     * 绘制X轴文字
     */
    private List<Float> drawXAxis(Canvas canvas, int pos, ChartBean chartBean) {

        List<Float> tXisPoints = getXAXisPoints(chartBean);

        canvas.save();

        int mLeftYAxisWidth = mAxisWidths.get(0);

        canvas.translate(mLeftYAxisWidth, mHeight - mXAxisTextHeight * 2);

        List<PointBean> values = chartBean.getValus();

        for (int i = 0; i < values.size(); i++) {
            PointBean pointBean = values.get(i);
            String valueName = pointBean.getValueName();
            Rect textWidthAndHeight = getTextWidthAndHeight(mXAxisTextPaint, valueName);
            canvas.drawText(
                    valueName,
                    tXisPoints.get(i) - textWidthAndHeight.width() / 2, textWidthAndHeight.height() + mAxisPaddingWidth,
                    mXAxisTextPaint
            );
        }

        canvas.restore();

        return tXisPoints;

    }

    /**
     * 计算各个点坐标
     */
    private List<Float> getXAXisPoints(ChartBean chartBean) {

        List<Float> xAxiss = new ArrayList<>();

        List<PointBean> valus = chartBean.getValus();

        int xAxisWidth = mLeftChartWidth - mAxisWidths.get(0);
        int effectiveXAxisWidth = (int) (xAxisWidth * 0.99);

        float tBlockWidth = (float) effectiveXAxisWidth / (float) (valus.size());

        mCusBarWidth = (float) (tBlockWidth * 0.8);
        float mCusBarPadding = (tBlockWidth - mCusBarWidth);

        boolean oddNumber = valus.size() % 2 != 0;

        if (oddNumber) {

            int total = (valus.size() - 1) / 2;

            float i2 = xAxisWidth / 2;

            for (int i1 = total; i1 > 0; i1--) {
                xAxiss.add(i2 - (mCusBarWidth + mCusBarPadding) * i1);
            }

            xAxiss.add(i2);

            for (int i1 = 0; i1 < total; i1++) {
                xAxiss.add(i2 + (mCusBarWidth + mCusBarPadding) * (i1 + 1));
            }

        } else {

            int total = valus.size() / 2;

            float i2 = xAxisWidth / 2;

            for (int i1 = total; i1 > 0; i1--) {
                xAxiss.add(i2 - ((mCusBarWidth / 2) * (i1 * 2 - 1) + mCusBarPadding * i1));
            }

            for (int i1 = 0; i1 < total; i1++) {
                xAxiss.add(i2 + ((mCusBarWidth / 2) * (i1 * 2 + 1) + mCusBarPadding * i1));
            }

        }

        return xAxiss;

    }

    /**
     * 绘制左侧图表（柱状图与折线图）
     * 不包括Y轴
     */
    private void drawLeftChart(Canvas canvas, List<Float> tXisPoints, int chartIndex, ChartBean chartBean) {

        canvas.save();

        int mLeftYAxisWidth = mAxisWidths.get(0);

        canvas.translate(mLeftYAxisWidth, mHeight - mXAxisTextHeight * 2);

        // 获取测量矩阵(逆矩阵)
        if (mMapMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMapMatrix);
        }

        Float maxValue = chartBean.getMaxValue();
        List<Integer> axisValue = getAxisValue(getJudgeYAxisValue(maxValue));

        int maxYValue = axisValue.get(axisValue.size() - 1);

        List<PointBean> valus = chartBean.getValus();

        for (int dataIndex = 0; dataIndex < valus.size(); dataIndex++) {
            PointBean pointBean = valus.get(dataIndex);

            float pointBeanValue = pointBean.getValue();

            float centerX = tXisPoints.get(dataIndex);

            float perCenterX = 0;

            if (dataIndex > 0) {
                perCenterX = tXisPoints.get(dataIndex - 1);
            }

            if (chartBean.getType() == ChartBean.CHART_SHOW_BARCHART) {
                drawBar(canvas, centerX, pointBean, maxYValue, chartIndex, dataIndex);
            } else if (chartBean.getType() == ChartBean.CHART_SHOW_LINECHART) {
                drawLineChart(canvas, chartIndex, maxYValue, valus, dataIndex, pointBean, centerX, perCenterX);
            } else {
                drawBar(canvas, centerX, pointBean, maxYValue, chartIndex, dataIndex);
            }
        }

        canvas.restore();

    }

    /**
     * 绘制折线图
     */
    private void drawLineChart(Canvas canvas, int chartIndex, int maxYValue, List<PointBean> valus, int dataIndex, PointBean pointBeanValue, float centerX, float perCenterX) {
        drawLineChartLine(canvas, chartIndex, maxYValue, valus, dataIndex, pointBeanValue, centerX, perCenterX);
        drawLineChartPoint(canvas, chartIndex, maxYValue, valus, dataIndex, pointBeanValue, centerX, perCenterX);

    }

    /**
     * 绘制折线图
     */
    private void drawLineChartPoint(Canvas canvas, int chartIndex, int maxYValue, List<PointBean> valus, int dataIndex, PointBean pointBeanValue, float centerX, float perCenterX) {
        Paint paint = mValuePaints.get(chartIndex);

        int nowTall = (int) ((pointBeanValue.getValue() / maxYValue) * (mBaseValueBlockHeight * 5));


        Path path = new Path();
        path.addCircle(centerX, -nowTall, mPointReadius, Path.Direction.CW);

        int color = paint.getColor();

        if (pointBeanValue.isClicked()) {
            int pressColor = getPressColor(color);
            paint.setColor(pressColor);
            paint.setStyle(Paint.Style.FILL);

            drawClickedText(canvas, pointBeanValue.getValue(), nowTall, centerX, paint);

        }

        canvas.drawPath(path, paint);

        paint.setColor(color);

        RectF rect = new RectF(
                (float) (centerX - mCusBarWidth / 3.5), (float) (-nowTall ),
                (float) (centerX + mCusBarWidth / 3.5), (float) (-nowTall + mCusBarWidth / 3.5)
        );

        Path tPath = new Path();
        tPath.addRect(rect, Path.Direction.CW);


        addRegion(chartIndex, dataIndex, tPath);

    }

    /**
     * 绘制点击数字
     */
    private void drawClickedText(Canvas canvas, float value, float tall, float centerX, Paint paint) {
        Rect textWidthAndHeight = getTextWidthAndHeight(paint, value + "");
        canvas.drawText(value + "", centerX - (textWidthAndHeight.width() >> 1), -tall - ((textWidthAndHeight.height() >> 1)), paint);
    }

    /**
     * 绘制折线图
     */
    private void drawLineChartLine(Canvas canvas, int chartIndex, int maxYValue, List<PointBean> values, int dataIndex, PointBean pointBeanValue, float centerX, float perCenterX) {
        Paint paint = mValuePaints.get(chartIndex);

        int nowTall = (int) ((pointBeanValue.getValue() / maxYValue) * (mBaseValueBlockHeight * 5));

        if (dataIndex != 0) {

            int perTall = (int) ((values.get(dataIndex - 1).getValue() / maxYValue) * (mBaseValueBlockHeight * 5));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawLine(
                    perCenterX, -perTall,
                    centerX, -nowTall, paint
            );
        }

    }

    /**
     * 绘制柱状图
     */
    private void drawBar(Canvas canvas, float centerX, PointBean pointBeanValue, int maxValue, int chartIndex, int dataIndex) {

        int nowTall = (int) ((pointBeanValue.getValue() / maxValue) * (mBaseValueBlockHeight * 5));


        RectF rect = new RectF(
                centerX - mCusBarWidth / 2, -nowTall,
                centerX + mCusBarWidth / 2, 0
        );

        Path path = new Path();
        path.addRect(rect, Path.Direction.CW);

        Paint paint = mValuePaints.get(chartIndex);
        int color = paint.getColor();

        if (pointBeanValue.isClicked()) {
            int pressColor = getPressColor(color);
            paint.setColor(pressColor);

            drawClickedText(canvas, pointBeanValue.getValue(), nowTall, centerX, paint);
        }

        canvas.drawPath(path, paint);

        paint.setColor(color);

        addRegion(chartIndex, dataIndex, path);
    }

    /**
     * 绘制左侧坐标系
     */
    private void drawLeftAxis(Canvas canvas, int pos, ChartBean chartBean) {
        canvas.save();

        int mLeftYWidth = mAxisWidths.get(0);

        canvas.translate(mLeftYWidth, mHeight - mXAxisTextHeight * 2);

        int height = 0;

        Float maxValue = chartBean.getMaxValue();
        List<Integer> axisValue = getAxisValue(getJudgeYAxisValue(maxValue));

        String unit = chartBean.getUnit();
        String yAxisName = chartBean.getyAxisName();

        for (int i = 0; i < 6; i++) {

            height = mBaseValueBlockHeight * i;

            canvas.drawLine(
                    0, -height,
                    -mXAxisSmallLineWidth, -height,
                    mValuePaints.get(pos));

            String text = axisValue.get(i) + unit;
            Rect textWidthAndHeight = getTextWidthAndHeight(mValuePaints.get(0), text);
            int textWidth = textWidthAndHeight.width();
            int textHeight = textWidthAndHeight.height();

            canvas.drawText(
                    text,
                    -mLeftYWidth, -height + textHeight / 2,
                    mValuePaints.get(pos)
            );

        }

        canvas.drawLine(
                0, 0,
                0, -mBaseValueBlockHeight * 5,
                mValuePaints.get(pos));

        if (StringUtils.isNoEmpey(yAxisName)) {

            Rect textWidthAndHeight = getTextWidthAndHeight(mValuePaints.get(0), yAxisName);
            int textWidth = textWidthAndHeight.width();
            int textHeight = textWidthAndHeight.height();

            canvas.drawText(
                    yAxisName,
                    -mLeftYWidth, -mBaseValueBlockHeight * 5 - textHeight,
                    mValuePaints.get(pos));

        }

        canvas.restore();
    }

    /**
     * 绘制右侧坐标系（s）
     */
    private void drawRightAxis(Canvas canvas, int pos, ChartBean chartBean) {

        canvas.save();

        if (pos == 1) {
            canvas.translate(mLeftChartWidth, mHeight - mXAxisTextHeight * 2);
        } else {

            int translateWidthwidth = 0;

            for (int i = 1; i < pos; i++) {
                translateWidthwidth = translateWidthwidth + mAxisWidths.get(i);
            }

            canvas.translate(mLeftChartWidth + translateWidthwidth, mHeight - mXAxisTextHeight * 2);
        }

        int height = 0;

        Float maxValue = chartBean.getMaxValue();
        List<Integer> axisValue = getAxisValue(getJudgeYAxisValue(maxValue));

        String unit = chartBean.getUnit();
        String yAxisName = chartBean.getyAxisName();

        Paint paint = mValuePaints.get(pos);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < 6; i++) {

            height = mBaseValueBlockHeight * i;

            canvas.drawLine(
                    0, -height,
                    mXAxisSmallLineWidth, -height,
                    paint);

            String text = axisValue.get(i) + unit;
            Rect textWidthAndHeight = getTextWidthAndHeight(paint, text);
            int textWidth = textWidthAndHeight.width();
            int textHeight = textWidthAndHeight.height();

            canvas.drawText(
                    text,
                    mAxisPaddingWidth + mXAxisSmallLineWidth, -height + textHeight / 2,
                    paint
            );

        }

        canvas.drawLine(
                0, 0,
                0, -mBaseValueBlockHeight * 5,
                paint);

        if (StringUtils.isNoEmpey(yAxisName)) {

            String text = yAxisName + unit;
            Rect textWidthAndHeight = getTextWidthAndHeight(paint, text);
            int textWidth = textWidthAndHeight.width();
            int textHeight = textWidthAndHeight.height();

            canvas.drawText(
                    yAxisName,
                    0, -mBaseValueBlockHeight * 5 - textHeight,
                    paint);

        }

        canvas.restore();
    }

    /**
     * 绘制图表背景
     * 横线，竖线
     */
    private void drawChartBackground(Canvas canvas) {

        mLeftChartWidth = mWidth - mRightYWidth;

        int mLeftYWidth = mAxisWidths.get(0);

        canvas.save();
        canvas.translate(mLeftYWidth, mHeight - mXAxisTextHeight * 2);
        canvas.scale(1, -1);

        int height = 0;

        for (int i = 0; i < 6; i++) {

            height = mBaseValueBlockHeight * i;

            canvas.drawLine(
                    0, height,
                    mLeftChartWidth - mLeftYWidth, height,
                    mChartLinePaint
            );
        }
        canvas.drawLine(
                0, 0,
                0, mBaseValueBlockHeight * 5,
                mChartLinePaint);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float[] pts = new float[2];
        pts[0] = event.getRawX();
        pts[1] = event.getRawY();
        mMapMatrix.mapPoints(pts);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) pts[0];
                int y = (int) pts[1];
                judgeClick(x, y);
                break;
        }
        return true;
    }

    /**
     * 判断数能否被5整除
     */
    private int getJudgeYAxisValue(float value) {
        int max = (int) (value + 0.5);
        int i1 = max % 5;
        if (i1 != 0) {
            max = max + 5;
        }

        return max;
    }

    /**
     * 获取Y轴坐标系list
     * [0,5,10,15,20]
     */
    private List<Integer> getAxisValue(int max) {
        List<Integer> axis = new ArrayList<>();
        int i = max / 5;

        if (i == 1) {
            for (int i1 = 0; i1 < 6; i1++) {
                axis.add(i1);
            }
        } else {
            for (int i1 = 0; i1 < 6; i1++) {
                axis.add(i * i1);
            }
        }


        return axis;
    }

    /**
     * 获取文字长宽
     */
    private Rect getTextWidthAndHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * 设置表
     */
    public XChatDataBean getXChatData() {
        return mXChatData;
    }

    /**
     * @param XChatData 图表数据
     */
    public void setXChatData(XChatDataBean XChatData) {
        mXChatData = XChatData;
        initData();
        invalidate();
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTitle = title;
        invalidate();
    }

    /**
     * 设置标题字体大小
     */
    public void setTitleTextSize(int titleTextSize) {
        mTitleTextSize = titleTextSize;
        invalidate();
    }

    /**
     * 设置标题颜色
     */
    public void setTitleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
        invalidate();
    }

    /**
     * 设置副标题
     */
    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
        invalidate();
    }

    /**
     * 设置副标题字体大小
     */
    public void setSubTitleTextSize(int subTitleTextSize) {
        mSubTitleTextSize = subTitleTextSize;
        invalidate();
    }

    /**
     * 设置副标题颜色
     */
    public void setSubTitleTextColor(int subTitleTextColor) {
        mSubTitleTextColor = subTitleTextColor;
        invalidate();
    }

    /**
     * 设置XY轴字体大小
     */
    public void setAxisTextSize(int axisTextSize) {
        mAxisTextSize = axisTextSize;
        invalidate();
    }

    /**
     * 设置背景XY轴颜色
     */
    public void setChartLineColor(int chartLineColor) {
        mChartLineColor = chartLineColor;
        invalidate();
    }

    /**
     * 设置背景XY轴粗细
     */
    public void setChartLineStrokeWidth(float chartLineStrokeWidth) {
        mChartLineStrokeWidth = chartLineStrokeWidth;
        invalidate();
    }

    /**
     * 设置X轴字体颜色
     */
    public void setXAxisTextColor(int XAxisTextColor) {
        mXAxisTextColor = XAxisTextColor;
        invalidate();
    }

    /**
     * 添加至区域判断
     */
    private void addRegion(int chartIndex, int dataIndex, Path path) {

        boolean exist = false;

        for (RegionDataBean region : mRegions) {
            if (region.getChartIndex() == chartIndex
                    && region.getDataIndex() == dataIndex) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            Region region = new Region();
            region.setPath(path, globalRegion);

            mRegions.add(new RegionDataBean(chartIndex, dataIndex, region));
        }

    }

    /**
     * 判断点击
     */
    private void judgeClick(int x, int y) {

        boolean isValueClicked = false;

        List<ChartBean> charts = getXChatData()
                .getCharts();
        for (int i1 = 0; i1 < charts.size(); i1++) {
            ChartBean chartBean = charts.get(i1);
            List<PointBean> valus = chartBean.getValus();
            for (int i2 = 0; i2 < valus.size(); i2++) {
                PointBean pointBean = valus.get(i2);
                isValueClicked = isValueClicked || pointBean.isClicked();
                pointBean.setClicked(false);
            }
        }

        for (int i = mRegions.size() - 1; i >= 0; i--) {
            RegionDataBean regionDataBean = mRegions.get(i);
            if (regionDataBean.getRegion().contains(x, y)) {
                charts.get(regionDataBean.getChartIndex())
                        .getValus()
                        .get(regionDataBean.getDataIndex())
                        .setClicked(true);
                isValueClicked = true;
                break;
            }
        }

        if (isValueClicked) {
            invalidate();
        }
    }

    /**
     * 获取深色
     */
    private int getPressColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] -= 0.5;
        hsv[1] += 0.5;
        hsv[2] -= 0.5;
        return Color.HSVToColor(hsv);
    }

}
