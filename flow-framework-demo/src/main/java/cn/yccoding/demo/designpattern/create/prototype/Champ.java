package cn.yccoding.demo.designpattern.create.prototype;

import lombok.Data;

/**
 * 英雄类
 *
 * @author YC
 * @since 2020/12/6
 */
@Data
public class Champ implements Cloneable{

    private String name;

    @Override
    protected Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public static void main(String[] args) {
        Champ champ = new Champ();
        Champ clone = (Champ) champ.clone();
    }
}
