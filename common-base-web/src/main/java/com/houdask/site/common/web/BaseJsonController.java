package com.houdask.site.common.web;


import com.houdask.site.common.result.JsonReult;
import com.houdask.site.common.service.HDException;
import com.houdask.site.common.utils.DateUtils;
import com.houdask.site.common.utils.IdGen;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * 统一定义JSON api父类
 */
public class BaseJsonController extends BaseController{

    /**
     * 参数绑定异常
     */
    @ResponseBody
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, javax.validation.ValidationException.class})
    public JsonReult bindException(Exception exception) {
        //按需重新封装需要返回的错误信息
        logger.error("bindException:{} ERROR_MSG:{} ", DateUtils.getNow(), exception.getMessage()  );
        return JsonReult.error(JsonReult.DICT_PARAM_ERROR,"参数错误");

    }
    @ResponseBody
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
    @ResponseBody
    @ExceptionHandler(value = HDException.class)
    public JsonReult serviceExceptionHandler(HDException ex) {
        String errorId = IdGen.uuid();
        logger.error("HDException:{} ERROR_ID:{}  CODE:{} MSG:{} ", DateUtils.getNow(),errorId ,ex.getCode(),  ex.getMsg() );
        return JsonReult.error(ex.getCode(), errorId +" "+ ex.getMsg());
    }
    /**
     * 授权登录异常
     */
    @ResponseBody
    @ExceptionHandler({AuthenticationException.class})
    public JsonReult authenticationException(AuthenticationException ex) {
        logger.error("SysException:{} ERROR_MSG:{} ", DateUtils.getNow(), ex.getMessage()  );
        return JsonReult.error(JsonReult.AUTH_ERROR,"权限不足。");
    }
}
