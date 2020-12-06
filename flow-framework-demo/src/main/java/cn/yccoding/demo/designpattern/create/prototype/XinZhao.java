package cn.yccoding.demo.designpattern.create.prototype;

/**
 * 赵信
 *
 * @author YC
 * @since 2020/11/15
 */
public class XinZhao extends Legend {
    public XinZhao() {
        type = "xinzhao123";
    }

    @Override
    public String ult() {
        return "Crescent Sweep（新月横扫）";
    }
}
