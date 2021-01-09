package top.zhaoxi.travel.service;

import top.zhaoxi.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有方法
     * @return
     */
    List<Category> findAll();
}
