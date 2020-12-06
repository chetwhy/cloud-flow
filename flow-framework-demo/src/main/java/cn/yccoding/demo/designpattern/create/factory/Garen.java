package cn.yccoding.demo.designpattern.create.factory;

/**
 * 盖伦
 *
 * @author YC
 * @since 2020/11/15
 */
public class Garen implements Legend{
    private String garenParam;

    @Override
    public String ult() {
        return "Demacian Justice（德玛西亚正义）";
    }

    public void setGarenMethod(String garenParam) {
        System.out.println("盖伦自有方法调用...");
        this.garenParam = garenParam;
    }
}
