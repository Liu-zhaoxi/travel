package top.zhaoxi.travel.service.impl;

import top.zhaoxi.travel.dao.FavoriteDao;
import top.zhaoxi.travel.dao.RouteDao;
import top.zhaoxi.travel.dao.impl.FavoriteDaoImpl;
import top.zhaoxi.travel.dao.impl.RouteDaoImpl;
import top.zhaoxi.travel.domain.*;
import top.zhaoxi.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        if (favorite != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
