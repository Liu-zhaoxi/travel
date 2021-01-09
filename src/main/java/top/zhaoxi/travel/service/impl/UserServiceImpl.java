package top.zhaoxi.travel.service.impl;

import top.zhaoxi.travel.dao.FavoriteDao;
import top.zhaoxi.travel.dao.RouteDao;
import top.zhaoxi.travel.dao.UserDao;
import top.zhaoxi.travel.dao.impl.FavoriteDaoImpl;
import top.zhaoxi.travel.dao.impl.RouteDaoImpl;
import top.zhaoxi.travel.dao.impl.UserDaoImpl;
import top.zhaoxi.travel.domain.User;
import top.zhaoxi.travel.service.UserService;
import top.zhaoxi.travel.utils.MailUtils;
import top.zhaoxi.travel.utils.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 注册用户方法
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为空
        if (u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送，邮件正文
        String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"请激活你的网站");

        return true;
    }

    /**
     * 激活用户方法
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
