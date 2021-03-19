package com.service;

import com.github.pagehelper.Page;
import com.pojo.Setmeal;
import health.entity.PageResult;
import health.entity.QueryPageBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-19
 * Time: 18:26
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param checkgroupIds
     * @param setmeal
     */
    void add(Integer[] checkgroupIds, Setmeal setmeal);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询套餐对应的查询组的id
     * @param id 套餐id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    /**
     * 删除套餐
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 修改套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);
}
