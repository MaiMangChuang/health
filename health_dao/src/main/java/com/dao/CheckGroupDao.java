package com.dao;

import com.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-14
 * Time: 22:34
 */
public interface CheckGroupDao {

    List<CheckGroup> findAll();

    /**
     * 添加检查组和检查项的关系
     * @param checkGroupid 检查组的id
     * @param checkitemId 检查项的id
     */
    void addCheckGroupCheckItem(@Param("checkGroupid")Integer checkGroupid, @Param("checkitemId")Integer checkitemId);

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);
}