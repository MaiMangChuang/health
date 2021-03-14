package com.service;



import com.pojo.CheckItem;
import health.entity.PageResult;
import health.entity.QueryPageBean;

import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
public interface CheckItemService {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();
    /**
     * 分页查询
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 添加
     * @return
     */
    void add(CheckItem checkItem);
}
