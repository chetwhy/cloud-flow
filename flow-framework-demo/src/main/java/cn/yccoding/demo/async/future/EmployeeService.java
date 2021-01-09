package cn.yccoding.demo.async.future;

/**
 * 人员服务
 *
 * @author YC
 * @since 2021/1/9
 */
public class EmployeeService implements RemoteLoader {

    /**
     * 获取入职时间信息
     *
     * @return
     */
    @Override
    public String load() {
        MockUtils.delay();
        return MockUtils.getInfo();
    }
}

