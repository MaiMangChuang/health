package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dao.OrderSettingDao;
import com.pojo.OrderSetting;
import com.service.OrderSettingService;
import health.exception.HealthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-22
 * Time: 21:14
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(List<OrderSetting> orderSettings) throws HealthException {
        orderSettings.forEach(os -> {
            OrderSetting orderSettingDB = orderSettingDao.findByOrderDate(os.getOrderDate());
            if (orderSettingDB == null) {
                orderSettingDao.add(os);
            } else {
                //若可预约数小于已预约数，则抛出异常，不允许更改
                if (os.getNumber() < orderSettingDB.getReservations()) {
                    throw new HealthException(os.getOrderDate() + "中：可预约数不能小于已预约数");
                }
                orderSettingDao.updateNumber(os);
            }
        });
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        String startDate = month + "-1";
        String endDate = month + "-32";
        Map<String, String> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return orderSettingDao.getOrderSettingByMonth(map);
    }
}
