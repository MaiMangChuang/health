package com.service;

import com.pojo.CheckGroup;
import com.pojo.CheckItem;
import health.entity.PageResult;
import health.entity.QueryPageBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-14
 * Time: 22:32
 */
public interface CheckGroupService {
    /**
     * 查询所有
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加检查组
     * @return
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(int checkGroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(int id);
}
