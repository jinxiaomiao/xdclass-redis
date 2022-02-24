package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import net.xdclass.xdclassredis.vo.UserPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author jinxm
 * @date 2022-02-24
 * @description
 */

@RestController
@RequestMapping("api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DAILY_RANK_KEY = "video:rank:daily";

    @RequestMapping("daily_rank")
    public JsonData videoDailyRank(){

        List<VideoDO> list =  redisTemplate.opsForList().range(DAILY_RANK_KEY,0,-1);

        return JsonData.buildSuccess(list);

    }

    /**
     * 返回全部榜单，从大到小
     * @return
     */
    @RequestMapping("real_rank1")
    public JsonData realRank1() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        Set<UserPointVO> set = operations.reverseRange(0, -1);
        return JsonData.buildSuccess(set);

    }

    /**
     * 返回全部榜单，从小到大
     * @return
     */
    @RequestMapping("real_rank2")
    public JsonData realRank2() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        Set<UserPointVO> set = operations.range(0, -1);
        return JsonData.buildSuccess(set);

    }



    /**
     * 返回全部榜单，从大到小,指定长度
     * @return
     */
    @RequestMapping("real_rank3")
    public JsonData realRank3() {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        Set<UserPointVO> set = operations.reverseRange(0, 3);
        return JsonData.buildSuccess(set);

    }


    /**
     * 查看某个用户的排名
     * @param phone
     * @param name
     * @return
     */
    @RequestMapping("find_myrank")
    public JsonData realMyRank(String phone,String name) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        UserPointVO userPointVO = new UserPointVO(name,phone);

        long rank = operations.reverseRank(userPointVO);

        return JsonData.buildSuccess(++rank);

    }



    /**
     * 加积分
     * @param phone
     * @param name
     * @return
     */
    @RequestMapping("uprank")
    public JsonData uprank(String phone,String name,int point) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        UserPointVO userPointVO = new UserPointVO(name,phone);

        operations.incrementScore(userPointVO,point);

        Set<UserPointVO> set = operations.range(0, -1);
        return JsonData.buildSuccess(set);

    }



    /**
     * 查看个人的积分
     * @param phone
     * @param name
     * @return
     */
    @RequestMapping("mypoint")
    public JsonData mypoint(String phone,String name) {
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");

        UserPointVO userPointVO = new UserPointVO(name,phone);

        double score = operations.score(userPointVO);
        return JsonData.buildSuccess(score);

    }

}
