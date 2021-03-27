package com.health.controller;

import com.aliyuncs.exceptions.ClientException;
import health.constant.MessageConstant;
import health.constant.RedisMessageConstant;
import health.entity.Result;
import health.utils.SMSUtils;
import health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 体检预约 发送验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            Jedis jedis = jedisPool.getResource();
            // 先从redis看是否存在
            String key = RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone;
            if (!StringUtils.isEmpty(jedis.get(key))) {
                return new Result(false, "验证码已经发送过了，请注意查收");
            }
            String code = ValidateCodeUtils.generateValidateCode4String(6);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, code);
            //将验证码存redis中，存活时间为60分钟
            jedis.setex(key,60*60,code);
            jedis.close();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    /**
     * 发送登陆用的验证码
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        Jedis jedis = jedisPool.getResource();
        // 先从redis看是否存在
        String key = RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone;
        String codeInRedis = jedis.get(key);
        if(null != codeInRedis){
            // 不为空，发送过了
            return new Result(false,"验证码已经发送过了，请注意查收");
        }
        // 没发送过
        // 生成验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(6) ;
        // 发送短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
            // 存入redis, 有效时间10，15
            // 延长有效期 expire key 秒
            jedis.setex(key, 10*60, validateCode);
            jedis.close();
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

}
