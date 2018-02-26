package com.houdask.site.common.web;


 import com.houdask.site.common.result.JsonReult;
import com.houdask.site.common.service.HDException;
import com.houdask.site.common.utils.DateUtils;
import com.houdask.site.common.utils.IdGen;
import org.apache.shiro.authc.AuthenticationException;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * TODO 统一定义web异常处理  待完善
 */
public class BaseWebController extends BaseController{

    @Value("${adminPath}")
    protected String adminPath;
    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, javax.validation.ValidationException.class})
    public JsonReult bindException(Exception exception) {
        //按需重新封装需要返回的错误信息
        logger.error("bindException:{} ERROR_MSG:{} ", DateUtils.getNow(), exception.getMessage()  );
        return JsonReult.error(JsonReult.DICT_PARAM_ERROR,"参数错误");

    }
    @ExceptionHandler(value = Exception.class)
    public JsonReult errorHandler(Exception ex) {
        logger.error("SysException:{} ERROR_MSG:{} ", DateUtils.getNow(), ex.getMessage()  );
        return JsonReult.error(JsonReult.DICT_SYSTEM_ERROR,"系统异常");
    }
    /**
     * 全局异ServiceException常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HDException.class)
    public JsonReult serviceExceptionHandler(HDException ex) {
        String errorId = IdGen.uuid();
        logger.error("HDException:{} ERROR_ID:{}  CODE:{} MSG:{} ", DateUtils.getNow(),errorId ,ex.getCode(),  ex.getMsg() );
        return JsonReult.error(ex.getCode(), errorId +" "+ ex.getMsg());
    }
    /**
     * 授权登录异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public JsonReult authenticationException(AuthenticationException ex) {
        logger.error("SysException:{} ERROR_MSG:{} ", DateUtils.getNow(), ex.getMessage()  );
        return JsonReult.error(JsonReult.AUTH_ERROR,"权限不足。");
    }
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

}
