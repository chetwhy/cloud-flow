package cn.yccoding.demo.designpattern.create.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 套装
 *
 * @author YC
 * @since 2020/11/29
 */
public class Suit {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public double getMoney() {
        return items.stream().map(Item::price).reduce(Double::sum).orElse(0.0);
    }

    public void showItems() {
        items.forEach(System.out::println);
    }
}
