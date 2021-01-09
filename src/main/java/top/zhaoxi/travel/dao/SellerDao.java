package top.zhaoxi.travel.dao;

import top.zhaoxi.travel.domain.Seller;

public interface SellerDao {

    /**
     * 根据sid查询卖家对象
     * @param sid
     * @return
     */
    Seller findBySid(int sid);
}
