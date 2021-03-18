package com.controller;

import health.constant.MessageConstant;
import health.entity.Result;
import health.utils.LoggerUtils;
import health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-18
 * Time: 23:43
 */
@RestController("/setmeal")
public class SetmealController {
    private static final  String D = ".";
    private static final Logger log = LoggerUtils.getLogger(SetmealController.class);

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(D));
        String filename = UUID.randomUUID() + extension;
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);
            Map<String,String> map = new HashMap<String,String>();
            map.put("imgName",filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

}
