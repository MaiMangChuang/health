package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.OrderSetting;
import com.service.OrderSettingService;
import health.constant.MessageConstant;
import health.entity.Result;
import health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-22
 * Time: 21:05
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;
    private static final Logger loge = LoggerFactory.getLogger(OrderSettingController.class);



    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        List<Map<String, Integer>> list = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        // 解析
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> list = new ArrayList<>();
            OrderSetting os;
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate;
            int number;
            for (String[] row : strings) {
                orderDate = sdf.parse(row[0]);
                number = Integer.parseInt(row[1]);
                os = new OrderSetting(orderDate, number);
                list.add(os);
            }
            if(list.size()>0){
                orderSettingService.add(list);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            loge.debug(e.getMessage());
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }


}
