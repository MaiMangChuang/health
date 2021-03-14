package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.CheckGroup;
import com.service.CheckGroupService;
import health.constant.MessageConstant;
import health.entity.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用服务添加
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

}
