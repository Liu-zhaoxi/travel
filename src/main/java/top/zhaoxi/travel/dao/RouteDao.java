package top.zhaoxi.travel.dao;

import top.zhaoxi.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * 根据cid,start,pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 根据rid查询出具体的路线信息
     * @param rid
     * @return
     */
    Route findRouteByRid(int rid);
}
