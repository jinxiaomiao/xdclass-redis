package net.xdclass.xdclassredis.controller;

import com.google.code.kaptcha.Producer;
import net.xdclass.xdclassredis.util.CommonUtil;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author jinxm
 * @date 2022-02-23
 * @description
 */
@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Producer captchaProducer;

    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String captchaText = captchaProducer.createText();
        String key = getCaptchaKey(request);
        //存到redis，10分钟过期
        redisTemplate.opsForValue().set(key, captchaText,10, TimeUnit.MINUTES);
        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        }

    }

    /**
     * 支持手机号、邮箱发送验证码
     * @return
     */
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@RequestParam(value = "to", required = true)String to,
                                     @RequestParam(value = "captcha", required = true)String  captcha,
                                     HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
            //TODO 发送验证码
            return JsonData.buildSuccess();
        }else {
            return JsonData.buildError("验证码错误");
        }

    }

    /**
     * 获取缓存的key
     * @param request
     * @return
     */
    private String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:"+CommonUtil.MD5(ip+userAgent);
        return key;
    }
}
