package net.xdclass.xdclassredis;

import net.xdclass.xdclassredis.model.UserDO;
import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.vo.UserPointVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Test
    void testDLock() {
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent("coupon:1001", "success", 20, TimeUnit.SECONDS);
        System.out.println("加锁是否成功:" + flag);
    }

    @Test
    public void saveRank(){

        String DAILY_RANK_KEY = "video:rank:daily";

        VideoDO video1 = new VideoDO(3,"PaaS工业级微服务大课","xdclass.net",1099);
        VideoDO video2 = new VideoDO(5,"AlibabaCloud全家桶实战","xdclass.net",59);
        VideoDO video3 = new VideoDO(53,"SpringBoot2.X+Vue3综合实战","xdclass.net",49);
        VideoDO video4 = new VideoDO(15,"玩转23种设计模式+最近实战","xdclass.net",49);
        VideoDO video5 = new VideoDO(45,"Nginx网关+LVS+KeepAlive","xdclass.net",89);

        redisTemplate.opsForList().leftPushAll(DAILY_RANK_KEY,video5,video4,video3,video2,video1);
    }

    @Test
    public void replaceRank(){

        String DAILY_RANK_KEY = "video:rank:daily";
        VideoDO video = new VideoDO(5432,"小滴课堂面试专题第一季","xdclass.net",323);
        redisTemplate.opsForList().set(DAILY_RANK_KEY,1,video);

    }

    /**
     * 用户画像去重
     */
    @Test
    public void userProfile(){


        BoundSetOperations operations = redisTemplate.boundSetOps("user:tags:1");

        operations.add("car","student","rich","dog","guangdong","rich");

        Set<String> set1 = operations.members();
        System.out.println(set1);


        operations.remove("dog");
        Set<String> set2 = operations.members();
        System.out.println(set2);


    }


    /**
     * 社交应用
     */
    @Test
    public void testSocial(){


        BoundSetOperations operationsLW = redisTemplate.boundSetOps("user:lw");
        operationsLW.add("A","B","C","D","E");
        System.out.println("老王的粉丝："+operationsLW.members());


        BoundSetOperations operationsXD = redisTemplate.boundSetOps("user:xd");
        operationsXD.add("A","B","F","G","H","K","J","W");
        System.out.println("小d的粉丝："+operationsXD.members());

        //差集
        Set lwSet = operationsLW.diff("user:xd");
        System.out.println("老王的专属用户："+lwSet);

        //差集
        Set xdSet = operationsXD.diff("user:lw");
        System.out.println("小D的专属用户："+xdSet);


        //交集
        Set interSet = operationsLW.intersect("user:xd");
        System.out.println("同时关注了两个人的用户："+interSet);

        //并集
        Set unionSet = operationsLW.union("user:xd");
        System.out.println("两个人的并集："+unionSet);


        //判断A用户是不是老王的粉丝

        boolean flag = operationsLW.isMember("A");
        System.out.println("A用户是不是老王的粉丝:"+flag);
    }




    @Test
    void testData() {

        UserPointVO p1 = new UserPointVO("老王","13113");
        UserPointVO p2 = new UserPointVO("老A","324");
        UserPointVO p3 = new UserPointVO("老B","242");
        UserPointVO p4 = new UserPointVO("老C","542345");
        UserPointVO p5 = new UserPointVO("老D","235");
        UserPointVO p6 = new UserPointVO("老E","1245");
        UserPointVO p7 = new UserPointVO("老F","2356432");
        UserPointVO p8 = new UserPointVO("老G","532332");

        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");


        operations.add(p1,324);
        operations.add(p2,542);
        operations.add(p3,52);
        operations.add(p4,434);
        operations.add(p5,1123);
        operations.add(p6,64);
        operations.add(p7,765);
        operations.add(p8,8);


    }

}
