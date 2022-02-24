package net.xdclass.xdclassredis.dao;

import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jinxm
 * @date 2022-02-24
 * @description
 */
@Repository
public class VideoCardDao {

    public List<VideoCardDO> list() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<VideoCardDO> cardDOList = new ArrayList<>();

        VideoCardDO videoCardDO = new VideoCardDO();
        videoCardDO.setId(1);
        videoCardDO.setTitle("热门视频");

        VideoDO videoDO1 = new VideoDO(1,"springcloud微服务视频","xdclass.net",43);
        VideoDO videoDO2 = new VideoDO(2,"Paas工业级项目实战","xdclass.net",32);
        VideoDO videoDO3 = new VideoDO(3,"面试专题视频","xdclass.net",43);
        VideoDO videoDO4 = new VideoDO(4,"spring源码实战","xdclass.net",4);
        List<VideoDO> videoDOS = new ArrayList<>();
        videoDOS.add(videoDO1);
        videoDOS.add(videoDO2);
        videoDOS.add(videoDO3);
        videoDOS.add(videoDO4);
        videoCardDO.setList(videoDOS);


        VideoCardDO videoCardDO2 = new VideoCardDO();
        videoCardDO2.setId(2);
        videoCardDO2.setTitle("项目实战视频");

        VideoDO videoDO5 = new VideoDO(1,"springcloud微服务视频项目","xdclass.net",43);
        VideoDO videoDO6 = new VideoDO(2,"Paas工业级项目实战项目","xdclass.net",32);
        VideoDO videoDO7 = new VideoDO(3,"面试专题视频项目","xdclass.net",43);
        VideoDO videoDO8 = new VideoDO(4,"设计模式视频","xdclass.net",4);
        List<VideoDO> videoDOS2 = new ArrayList<>();
        videoDOS2.add(videoDO5);
        videoDOS2.add(videoDO6);
        videoDOS2.add(videoDO7);
        videoDOS2.add(videoDO8);
        videoCardDO2.setList(videoDOS2);



        cardDOList.add(videoCardDO);
        cardDOList.add(videoCardDO2);

        return cardDOList;
    }
}
