package top.zhaoxi.travel.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhaoxi.travel.dao.FavoriteDao;
import top.zhaoxi.travel.domain.Favorite;
import top.zhaoxi.travel.utils.JDBCUtils;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        //1.定义sql
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {}

        //2.返回查询结果
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        //1.定义sql
        String sql = "select count(*) from tab_favorite where rid = ?";

        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        //定义sql
        String sql = "insert into tab_favorite value(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
