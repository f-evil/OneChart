package com.easy.onechartview.view.bean;

import java.io.Serializable;

/**
 * @author fuyujie
 * @package: com.easy.customview.test8.bean
 * @fileNmae PointBean
 * @date 2019/10/28 16:00
 * @describe
 * @org easylinking
 * @email f279259625@gmail.com
 */
public class PointBean implements Serializable {

    /**
     * 数据集
     */
    private float value;
    /**
     * 数据集
     */
    private String valueName;

    private boolean clicked = false;

    public PointBean(float value, String valueName) {
        this.value = value;
        this.valueName = valueName;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}
