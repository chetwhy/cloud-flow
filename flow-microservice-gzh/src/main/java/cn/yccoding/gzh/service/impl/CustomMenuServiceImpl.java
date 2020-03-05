package cn.yccoding.gzh.service.impl;

import cn.yccoding.gzh.constant.GzhUrlConstant;
import cn.yccoding.gzh.service.CommonGzhService;
import cn.yccoding.gzh.service.CustomMenuService;
import cn.yccoding.gzh.util.RestHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Service
public class CustomMenuServiceImpl implements CustomMenuService {

    @Autowired
    private RestHttpClient restHttpClient;
    
    @Autowired
    private CommonGzhService commonGzhService;

    @Value("${local.active}")
    private Boolean localActive;

    @Override
    public String createMenu(String menuJson) {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_CREATE, getAccessToken());
        return restHttpClient.doPost(toUrl, menuJson);
    }

    @Override
    public String getMenu() {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_QUERY, getAccessToken());
        return restHttpClient.doGet(toUrl);
    }

    @Override
    public String deleteMenu() {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_DELETE, getAccessToken());
        return restHttpClient.doGet(toUrl);
    }

    private String getAccessToken() {
        if (localActive) {
            return commonGzhService.getLocalAccessToken();
        }
        return commonGzhService.getRedisAccessToken();
    }
}
