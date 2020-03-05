package cn.yccoding.gzh.service;

/**
 * @Author YC
 * @create 2020/3/6
 * 微信公号通用服务类
 */
public interface CommonGzhService {
    /**
     * 获取redis中access token
     * @return
     */
    String getRedisAccessToken();

    /**
     * java内存保存redis
     * @return
     */
    String getLocalAccessToken();
}
