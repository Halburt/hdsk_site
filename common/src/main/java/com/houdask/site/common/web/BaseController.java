package com.houdask.site.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author Halburt 2018-01-16
 */
public abstract class BaseController {

    protected String adminPath="";
    /**
     * 日志对象
     */
//    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加Model消息
     * @param message
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
}
