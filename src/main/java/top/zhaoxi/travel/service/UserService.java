package top.zhaoxi.travel.service;

import top.zhaoxi.travel.domain.PageBean;
import top.zhaoxi.travel.domain.Route;
import top.zhaoxi.travel.domain.User;

public interface UserService {
    /**
     * 注册用户方法
     * @param user
     * @return
     */
    boolean register(User user);

    boolean active(String code);

    User login(User user);
}
