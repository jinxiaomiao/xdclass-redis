package net.xdclass.xdclassredis;

import net.xdclass.xdclassredis.model.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class XdclassRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testSpringSet() {
        redisTemplate.opsForValue().set("name", "xdclass.net");

        stringRedisTemplate.opsForValue().set("str_name", "xdclass.net+str_name");
    }

    @Test
    void testSpringGet() {
        String str1 = (String)redisTemplate.opsForValue().get("name");
        System.out.println(str1);

        String str2 = stringRedisTemplate.opsForValue().get("str_name");
        System.out.println(str2);
    }

    @Test
    void testSerial() {
        UserDO userDO = new UserDO();
        userDO.setId(1);
        userDO.setName("哈哈");
        userDO.setPwd("abcd");
        redisTemplate.opsForValue().set("user-service:user:1", userDO);

    }

}
