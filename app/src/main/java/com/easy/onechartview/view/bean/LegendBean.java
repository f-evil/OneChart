package com.easy.onechartview.view.bean;

import java.io.Serializable;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8.bean
 * @fileNmae LegendBean
 * @date 2019/10/28 13:52
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class LegendBean implements Serializable {
    /**
     * 颜色
     */
    private int color;
    /**
     * 名称
     */
    private String name;

    public LegendBean(int color, String name) {
        this.color = color;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
