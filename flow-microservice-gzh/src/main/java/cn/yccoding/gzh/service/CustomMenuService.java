package cn.yccoding.gzh.service;

/**
 * @Author YC
 * @create 2020/3/6
 * 自定义微信菜单接口
 */
public interface CustomMenuService {

    String createMenu(String menuJson);

    String getMenu();

    String deleteMenu();
}
