package com.service.impl;

import com.dao.MemberDao;
import com.dao.OrderDao;
import com.dao.OrderSettingDao;
import com.pojo.Member;
import com.pojo.Order;
import com.pojo.OrderSetting;
import com.service.OrderService;
import health.exception.HealthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-27
 * Time: 17:00
 */
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order submitOrder(Map<String, String> paraMap) {
        //1. 通过日期查询预约设置是否存在 t_ordersetting
        String orderDateStr = paraMap.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try {
            orderDate = sdf.parse(orderDateStr);
        } catch (ParseException e) {
            //e.printStackTrace();
            throw new HealthException("日期格式错误");
        }
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if(null == orderSetting) {
            //不存在就报错
            throw new HealthException("所选日期，不能预约");
        }
        //存在 则判断是否约满
        if(orderSetting.getReservations() >= orderSetting.getNumber()) {
            //   full 报错
            throw new HealthException("所选日期，预约已满");
        }
        //2. 是否为会员 通过手机号码查询 t_member
        String telephone = paraMap.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(null == member) {
            //非会员
            //   添加到会员表 获取id
            member = new Member();
            member.setIdCard(paraMap.get("idCard"));
            member.setName(paraMap.get("name"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setSex(paraMap.get("sex"));
            // 添加会员, 获取id
            memberDao.add(member);
        }
        //3. 判断是否重复预约 t_order 通过member_id,orderDate, setmeal_id
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf(paraMap.get("setmealId")));
        List<Order> orderList = orderDao.findByCondition(order);

        if(null != orderList && orderList.size() > 0){
            //重复则报错
            throw new HealthException("不能重复预约");
        }
        //没重复
        //可预约，添加订单
        order.setOrderType(paraMap.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);
        //4. 更新已预约数量 t_ordersetting
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return order;
    }

    @Override
    public Map<String, Object> findOrderDetailById(int id) {
        return orderDao.findById4Detail(id);
    }
}
