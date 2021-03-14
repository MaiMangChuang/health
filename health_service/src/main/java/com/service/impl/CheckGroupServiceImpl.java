package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dao.CheckGroupDao;
import com.pojo.CheckGroup;
import com.service.CheckGroupService;
import com.service.CheckItemService;
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
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        LoggerUtils.getLogger(CheckGroupServiceImpl.class).debug("checkGroup = " + checkGroup.getId());
        Arrays.stream(checkitemIds).map(checkitemId->""+checkitemId).forEach(LoggerUtils.getLogger(CheckGroupServiceImpl.class)::debug);
        Arrays.stream(checkitemIds).forEach(checkitemId->checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId));
    }
}
