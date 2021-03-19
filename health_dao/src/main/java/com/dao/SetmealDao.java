package com.dao;

import com.github.pagehelper.Page;
import com.pojo.Setmeal;
import health.entity.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-19
 * Time: 18:33
 */
public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与选项组的联系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询套餐对应查询组的id
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    /**
     * 查询与该套餐关联的订单的数量
     * @param id 套餐id
     * @return
     */
    int findOrderCountBySetmealId(Integer id);

    /**
     * 删除与该套餐与检查组的联系
     * @param id 套餐id
     */
    void deleteSetmealCheckGroup(Integer id);

    /**
     * 删除该套餐
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 修改
     * @param setmeal
     */
    void update(Setmeal setmeal);
}
