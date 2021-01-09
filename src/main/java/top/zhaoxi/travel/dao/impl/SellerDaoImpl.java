package top.zhaoxi.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhaoxi.travel.dao.SellerDao;
import top.zhaoxi.travel.domain.Seller;
import top.zhaoxi.travel.utils.JDBCUtils;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findBySid(int sid) {
        //1.定义sql
        String sql = "select * from tab_seller where sid = ? ";
        //2.返回查询结果
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
    }
}
