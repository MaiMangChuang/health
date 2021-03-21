package com.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.dao.SetmealDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pojo.Setmeal;
import com.service.SetmealService;
import health.entity.PageResult;
import health.entity.QueryPageBean;
import health.exception.HealthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-19
 * Time: 18:27
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroupId :checkgroupIds){
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        // 判断是否被订单使用
        int count = setmealDao.findOrderCountBySetmealId(id);
        // 使用了则报错
        if(count > 0){
            throw new HealthException("该套餐已经被使用了，不能删除");
        }
        // 未使用
        // 先删除套餐与检查组关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.update(setmeal);
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
    }

    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }
}
