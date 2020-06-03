package cn.yccoding.gzh.service.impl;

import cn.yccoding.gzh.constant.GzhUrlConstant;
import cn.yccoding.gzh.service.CommonGzhService;
import cn.yccoding.gzh.service.CustomMenuService;
import cn.yccoding.gzh.util.RestHttpClientUtils;
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
    private RestHttpClientUtils restHttpClientUtils;
    
    @Autowired
    private CommonGzhService commonGzhService;

    @Value("${local.active}")
    private Boolean localActive;

    @Override
    public String createMenu(String menuJson) {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_CREATE, getAccessToken());
        return restHttpClientUtils.doPost(toUrl, menuJson);
    }

    @Override
    public String getMenu() {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_QUERY, getAccessToken());
        return restHttpClientUtils.doGet(toUrl);
    }

    @Override
    public String deleteMenu() {
        String toUrl = MessageFormat.format(GzhUrlConstant.MENU_DELETE, getAccessToken());
        return restHttpClientUtils.doGet(toUrl);
    }

    private String getAccessToken() {
        if (localActive) {
            return commonGzhService.getLocalAccessToken();
        }
        return commonGzhService.getRedisAccessToken();
    }
}
