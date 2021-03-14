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


    void add(CheckItem checkItem);
}
