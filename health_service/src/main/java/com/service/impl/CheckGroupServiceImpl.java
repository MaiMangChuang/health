package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dao.CheckGroupDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pojo.CheckGroup;
import com.pojo.CheckItem;
import com.service.CheckGroupService;
import com.service.CheckItemService;
import health.constant.MessageConstant;
import health.entity.PageResult;
import health.entity.QueryPageBean;
import health.exception.HealthException;
import health.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-14
 * Time: 22:34
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        LoggerUtils.getLogger(CheckGroupServiceImpl.class).debug("checkGroup = " + checkGroup.getId());
        Arrays.stream(checkitemIds).map(checkitemId->""+checkitemId).forEach(LoggerUtils.getLogger(CheckGroupServiceImpl.class)::debug);
        Arrays.stream(checkitemIds).forEach(checkitemId->checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId));
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(int checkGroupId) {
        return  checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.update(checkGroup);
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        if(checkitemIds!=null && checkitemIds.length>0){
            for(Integer checkitemId :checkitemIds){
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(int id) {
        // 检查 这个检查组是否被套餐使用了
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if(count > 0){
            // 被使用了
            throw new HealthException(MessageConstant.CHECKGROUP_IN_USE);
        }
        checkGroupDao.deleteCheckGroupCheckItem(id);
        checkGroupDao.deleteById(id);
    }
}
