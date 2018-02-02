package com.houdask.site.common.web;

import com.houdask.site.common.result.JsonReult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Halburt 2018-01-16
 */
public abstract class BaseController {

    protected   Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected String adminPath="";
    /**
     * 日志对象
     */
//    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加Model消息
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        model.addAttribute("message", sb.toString());
    }
    /**
     * 服务端参数有效性验证
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
        return true;
    }


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonReult errorHandler(Exception ex) {
        return JsonReult.error(JsonReult.DICT_SYSTEM_ERROR,"系统异常");
    }
  /*  @ResponseBody
    @ExceptionHandler(value = ArithmeticException.class)
    public JsonReult ArithmeticExceptionHandler(ArithmeticException ex) {
        return JsonReult.error(JsonReult.DICT_SYSTEM_ERROR,"ArithmeticException系统异常");
    }*/
}
