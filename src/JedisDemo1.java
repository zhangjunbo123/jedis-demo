import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDemo1 {

    public static void main(String[] args) {
        demo1();
        demo2();
    }

    public static void demo1() {
        // 1.设置ip地址和端口
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        //2. 保存数据
        jedis.set("name", "bogege");
        //3.获取数据
        String value = jedis.get("name");
        System.out.println(value);
        //4.释放资源
        jedis.close();
    }

    /**
     * 连接池方式
     */
    public static void demo2() {
        //获取连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置
        config.setMaxTotal(30);
        config.setMaxIdle(10);

        //获取连接池
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);

        //获取Jedis对象
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth("123456");
            jedis.set("name2", "bogege");
            String value = jedis.get("name2");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }

}
