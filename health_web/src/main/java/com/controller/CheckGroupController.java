package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.CheckGroup;
import com.pojo.CheckItem;
import com.service.CheckGroupService;
import health.constant.MessageConstant;
import health.entity.PageResult;
import health.entity.QueryPageBean;
import health.entity.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    
    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/findAll")
    public Result findAll(){
      List<CheckGroup>  all = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用服务添加
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 通过id获取检查组
     */
    @RequestMapping("/findById")
    public Result findById(int id){
        CheckGroup  checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /**
     * 修改提交
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务删除
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
