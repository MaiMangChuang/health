import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-27
 * Time: 17:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class redisTest {
    @Autowired
    JedisPool jedisPool;

    @Test
    public void testRedis(){
        Jedis resource = jedisPool.getResource();
        resource.set("mmc","mmc123456");
        resource.set("hmb","hmb123");
        resource.close();
    }

    @Test
    public void testRedis2(){
        Jedis resource = jedisPool.getResource();
        System.out.println(resource.get("mmc"));
        System.out.println(resource.get("hmb"));
        resource.close();
    }
}
