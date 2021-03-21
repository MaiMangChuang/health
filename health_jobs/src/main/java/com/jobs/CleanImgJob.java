package com.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.service.SetmealService;
import health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 清理垃圾图片 任务类
 * User: mai
 * Date: 2021-03-21
 * Time: 19:08
 */
@Component
public class CleanImgJob {

    public static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);
    @Reference
    SetmealService setmealService;
    /**
     * 清理垃圾图片的执行方法
     */
    public void clearImg() {
        log.info("定时任务开始执行  " + System.currentTimeMillis());
        // 查出7牛上的所有图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        // 查出数据库中的所有图片
        List<String> imgInDb = setmealService.findImgs();
        // 7牛的-数据库的 imgIn7Niu剩下的就是要删除的
        // imgIn7Niu.size(7) - 3 = imgIn7Niu.size(4)
        // imgInDb里的对象在 imgIn7Niu 能找到就把这删除
        imgIn7Niu.removeAll(imgInDb);
        // 删除7牛上的垃圾图片
        String[] strings = imgIn7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(strings);
        log.info("定时任务执行结束  " + System.currentTimeMillis());
    }
}
