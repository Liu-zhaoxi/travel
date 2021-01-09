package top.zhaoxi.travel.service.impl;

import top.zhaoxi.travel.dao.FavoriteDao;
import top.zhaoxi.travel.dao.RouteDao;
import top.zhaoxi.travel.dao.RouteImgDao;
import top.zhaoxi.travel.dao.SellerDao;
import top.zhaoxi.travel.dao.impl.FavoriteDaoImpl;
import top.zhaoxi.travel.dao.impl.RouteDaoImpl;
import top.zhaoxi.travel.dao.impl.RouteImgDaoImpl;
import top.zhaoxi.travel.dao.impl.SellerDaoImpl;
import top.zhaoxi.travel.domain.PageBean;
import top.zhaoxi.travel.domain.Route;
import top.zhaoxi.travel.domain.RouteImg;
import top.zhaoxi.travel.domain.Seller;
import top.zhaoxi.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pageBean = new PageBean<>();
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页显示条数
        pageBean.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);

        //设置当前页面显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) +1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据rid查询route表中的route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.根据route的rid查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3.根据route的sid查询卖家信息
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        //返回结果
        return route;
    }
}
