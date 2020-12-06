package cn.yccoding.demo.designpattern.create.prototype;

import lombok.Data;

/**
 * 英雄
 *
 * @author YC
 * @since 2020/11/15
 */
@Data
public abstract class Legend implements Cloneable{
    private String name;

    protected String type;

    /**
     * 大招
     */
    abstract String ult();

    @Override
    protected Object clone(){
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

