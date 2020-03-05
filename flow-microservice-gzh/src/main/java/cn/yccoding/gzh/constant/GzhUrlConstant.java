package cn.yccoding.gzh.constant;

/**
 * @Author YC
 * @create 2020/3/6
 * 微信公众号url常量
 */
public class GzhUrlConstant {

    // 获取基本accessToken的接口
    public static final String BASE_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    // 页面使用jssdk的凭据
    public static final String BASE_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

    // 微信公众号创建菜单
    public static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";

    // 微信公众号查询菜单
    public static final String MENU_QUERY = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={0}";

    // 微信公众号删除菜单
    public static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token={0}";

}
