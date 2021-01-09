package cn.yccoding.demo.async.future;

/**
 * 账户服务
 *
 * @author YC
 * @since 2021/1/9
 */
public class AccountService implements RemoteLoader {

    /**
     * 获取账户金额
     */
    @Override
    public String load() {
        MockUtils.delay();
        return MockUtils.getData()+"";
    }
}
