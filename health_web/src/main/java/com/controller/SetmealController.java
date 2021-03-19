package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.pojo.Setmeal;
import com.service.SetmealService;
import health.constant.MessageConstant;
import health.entity.PageResult;
import health.entity.QueryPageBean;
import health.entity.Result;
import health.utils.LoggerUtils;
import health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-18
 * Time: 23:43
 */
@RestController()
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    private static final  String D = ".";
    private static final Logger log = LoggerUtils.getLogger(SetmealController.class);

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(D));
        String filename = UUID.randomUUID() + extension;
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);
            Map<String,String> map = new HashMap<String,String>();
            map.put("imgName",filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @RequestMapping("/add")
    public Result add(Integer[] checkgroupIds, @RequestBody Setmeal setmeal){
        setmealService.add(checkgroupIds,setmeal);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> setmeals = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeals);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("setmeal",setmeal);
        map.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    @RequestMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(Integer id){
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,checkgroupIds);
    }


    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        // 调用业务服务修改
        setmealService.update(setmeal, checkgroupIds);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }


}
