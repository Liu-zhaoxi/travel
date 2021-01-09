package top.zhaoxi.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zhaoxi.travel.dao.RouteDao;
import top.zhaoxi.travel.domain.Route;
import top.zhaoxi.travel.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        //1.定义sql,模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuffer stringBuffer = new StringBuffer(sql);

        List params = new ArrayList();//条件

        //2.判断是否有值
        if (cid != 0){
            stringBuffer.append(" and cid = ? ");
            params.add(cid);//添加?对应的值
        }

        if (rname != null && rname.length() > 0){
            stringBuffer.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }

        sql = stringBuffer.toString();

        //3.返回查询结果
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //1.定义sql模板
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuffer stringBuffer = new StringBuffer(sql);

        List params = new ArrayList();//条件
        //2.判断参数是否有值
        if (cid != 0){
            stringBuffer.append(" and cid = ? ");
            params.add(cid);//添加?对应的值
        }

        if (rname != null && rname.length() > 0){
            stringBuffer.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }

        stringBuffer.append(" limit ? , ? ");//分页条件

        params.add(start);
        params.add(pageSize);

        sql = stringBuffer.toString();
        //3.返回查询结果
        return template.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        //1.定义sql
        String sql = "select * from tab_route where rid = ?";
        //2.返回查询结果
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public Route findRouteByRid(int rid) {
        String sql = "select * from tab_route where rid = ?)";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
