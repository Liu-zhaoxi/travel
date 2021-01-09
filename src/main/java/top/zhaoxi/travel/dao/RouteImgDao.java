package top.zhaoxi.travel.dao;

import top.zhaoxi.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据route的rid查询图片
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
