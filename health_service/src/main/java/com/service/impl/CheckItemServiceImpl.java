package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.service.CheckItemService;
import com.dao.CheckItemDao;
import health.constant.MessageConstant;
import health.entity.PageResult;
import health.entity.QueryPageBean;
import health.exception.HealthException;
import org.springframework.beans.factory.annotation.Autowired;
import com.pojo.CheckItem;
import org.springframework.util.StringUtils;


import java.util.List;

/**
 * Description: No Description
 * User: Eric  jdk proxy com.sum.proxy
 * interfaceClass发布到zookeeper上的接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public void deleteById(int id) throws HealthException {
     int coun = checkItemDao.findCountByCheckItemId(id);
     if(coun>0){
         throw  new HealthException("该检查项已经被使用了，不能删除!");
     }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
