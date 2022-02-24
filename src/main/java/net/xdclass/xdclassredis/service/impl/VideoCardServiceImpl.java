package net.xdclass.xdclassredis.service.impl;

import net.xdclass.xdclassredis.dao.VideoCardDao;
import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/

@Service
public class VideoCardServiceImpl implements VideoCardService {

    @Autowired
    private VideoCardDao videoCardDao;

    @Override
    public List<VideoCardDO> list() {

        return videoCardDao.list();
    }
}
