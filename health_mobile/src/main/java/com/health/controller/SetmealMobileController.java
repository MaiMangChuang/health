package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.Setmeal;
import com.service.SetmealService;
import health.constant.MessageConstant;
import health.entity.Result;
import health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    private static final Logger log = LoggerFactory.getLogger(SetmealMobileController.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐列表
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result findAll(){
        // 查询所有
        List<Setmeal> setmealList = setmealService.findAll();
        setmealList.forEach(setmeal->{
            setmeal.setImg(QiNiuUtils.DOMAIN +setmeal.getImg());
        });
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
    }

    /**
     * 套餐详情
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
//        调用服务查询套餐详情
        Setmeal setmeal = setmealService.findDetailById(id);
        //拼接图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        log.debug("setmeal.getCheckGroups().size() = "+setmeal.getCheckGroups().size());
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }


}
