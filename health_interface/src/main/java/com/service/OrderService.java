package com.service;

import com.pojo.Order;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-27
 * Time: 16:57
 */
public interface OrderService {

    /**
     * 预约提交
     * @param paraMap
     * @return
     */
    Order submitOrder(Map<String, String> paraMap);

    /**
     * 成功信息的展示
     * @param id
     * @return
     */
    Map<String, Object> findOrderDetailById(int id);
}
