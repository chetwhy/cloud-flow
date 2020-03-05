package cn.yccoding.gzh.controller;

import cn.yccoding.common.util.JasyptUtil;

/**
 * @Author YC
 * @create 2020/3/5
 * 公众号菜单栏
 */
public class MenuController {
    public static void main(String[] args) {
        System.out.println(JasyptUtil.encyptPwd("yuan0420", "yccoding2019"));
        System.out.println(JasyptUtil.decyptPwd("yuan0420", "QcmW6fG+sKsLxFa3k9AtAtcNs+QHsFXFuns+Xb2MR1gIv7kM+gwKQoftoMvO/S9o"));
    }
}
