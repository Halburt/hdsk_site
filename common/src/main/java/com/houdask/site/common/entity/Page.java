package com.houdask.site.common.entity;

import com.houdask.site.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 分页类
 * @param <T>
 */
public class Page<T> extends com.github.pagehelper.Page<T> {

    public static final int defaultPageSize = 10;
    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

    public Page() {
    }
    /**
     * 构造方法
     * @param request 传递 repage 参数，来记住页码
     * @param response 用于设置 Cookie，记住页码
     * @param defaultPageSize 默认分页大小，如果传递 -1 则为不分页，返回所有数据
     */
    public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
        // 设置页码参数（传递repage参数，来记住页码）
        String no = request.getParameter("pageNum");
        if (StringUtils.isNumeric(no)){
            CookieUtils.setCookie(response, "pageNum", no);
            this.setPageNum(Integer.parseInt(no));
        }else if (request.getParameter("repage")!=null){
            no = CookieUtils.getCookie(request, "pageNum");
            if (StringUtils.isNumeric(no)){
                this.setPageNum(Integer.parseInt(no));
            }
        }
        // 设置页面大小参数（传递repage参数，来记住页码大小）
        String size = request.getParameter("pageSize");
        if (StringUtils.isNumeric(size)){
            CookieUtils.setCookie(response, "pageSize", size);
            this.setPageSize(Integer.parseInt(size));
        }else if (request.getParameter("repage")!=null){
            size = CookieUtils.getCookie(request, "pageSize");
            if (StringUtils.isNumeric(size)){
                this.setPageSize(Integer.parseInt(size));
            }
        }else if (defaultPageSize != -2){
            this.setPageSize( defaultPageSize);
        }

        // 设置排序参数
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotBlank(orderBy)){
            this.setOrderBy(orderBy);
        }
    }

    public Page(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        super(pageNum, pageSize, count);
    }

    public Page(int[] rowBounds, boolean count) {
        super(rowBounds, count);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
