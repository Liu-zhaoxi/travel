package top.zhaoxi.travel.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedis连接工具类
 */
public final class JedisUtil {

    private static JedisPool jedisPool;

    static {
        //获取配置文件
        InputStream inputStream = Jedis.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建Properties对象，获取配置文件
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取配置文件信息，并设置到JedisPoolConfig中
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));

        //初始化JedisPool对象
        jedisPool = new JedisPool(jedisPoolConfig,properties.getProperty("host"),Integer.parseInt(properties.getProperty("port")));
    }

    /**
     * 获取连接方法
     * @return
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 关闭redis数据库方法
     */
    public static void close(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }
}
