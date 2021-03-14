package com.dao;


import com.github.pagehelper.Page;
import com.pojo.CheckItem;

import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
public interface CheckItemDao {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();
    /**
     * 按条件分页查询
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 添加
     * @return
     */
    void add(CheckItem checkItem);

    /**
     * 查询有多少个使用该检查项的检查组
     * @return
     */
    int findCountByCheckItemId(int id);
    /**
     * 根据id删除
     * @return
     */
    void deleteById(int id);
    /**
     * 根据id查询
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     * @return
     */
    void update(CheckItem checkItem);
}
