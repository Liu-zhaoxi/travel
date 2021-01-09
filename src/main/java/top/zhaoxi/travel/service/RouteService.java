package top.zhaoxi.travel.service;

import top.zhaoxi.travel.domain.PageBean;
import top.zhaoxi.travel.domain.Route;

/**
 * 线路Service
 */
public interface RouteService {

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
