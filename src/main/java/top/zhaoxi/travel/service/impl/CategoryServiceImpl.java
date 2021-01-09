package top.zhaoxi.travel.service.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import top.zhaoxi.travel.dao.CategoryDao;
import top.zhaoxi.travel.dao.impl.CategoryDaoImpl;
import top.zhaoxi.travel.domain.Category;
import top.zhaoxi.travel.service.CategoryService;
import top.zhaoxi.travel.utils.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis客户端中查询
        //1.1连接Jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可以使用sortedset排序查询
        //Set<String> category = jedis.zrange("category",0,-1);
        //1.3查询sortedset排序查询
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        //2.判断查询的集合是否为空
        List<Category> categoryList = null;
        if (category == null || category.size() == 0){
            //3.如果为空，这需要从MySQL数据库中查询，再把数据存储到redis
            //3.1从数据库中查询
            categoryList = categoryDao.findAll();
            //3.2将集合中的数据存储到redis中的category的key中
            for (int i = 0;i<categoryList.size();i++){
                jedis.zadd("category",categoryList.get(i).getCid(),categoryList.get(i).getCname());
            }
        }else {
            //4.如果不为空，将set的数据存入list
            categoryList = new ArrayList<>();
            for (Tuple tuple : category) {
                Category cate = new Category();
                cate.setCname(tuple.getElement());
                cate.setCid((int)tuple.getScore());
                categoryList.add(cate);
            }
        }
        return categoryList;
    }
}
