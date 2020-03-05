package cn.yccoding.gzh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.yccoding.gzh.service.CustomMenuService;

/**
 * @Author YC
 * @create 2020/3/5
 * 公众号菜单栏
 */
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private CustomMenuService customMenuService;

    @GetMapping
    public String getMenu() {
        return customMenuService.getMenu();
    }

    @PostMapping
    public String createMenu(@RequestBody String menuJson) {
        return customMenuService.createMenu(menuJson);
    }

    @GetMapping("/delete")
    public String deleteMenu() {
        return customMenuService.deleteMenu();
    }

}
