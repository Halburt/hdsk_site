package com.houdask.site.common.entity;

import com.houdask.site.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * @param <T>
 */
public class Page<T>  implements Serializable{

    public static final int defaultPageSize = 10;
    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

    //当前页码
    private int pageNum ;
    //每页记录数
    private int pageSize;
    //数据集合
    private List<T> list = new ArrayList<T>();
//  数据总记录数（不一定等于list.size()）
    private long  count;//

    public Page(){}

    public Page(com.github.pagehelper.Page resultPage) {
        this.list = resultPage.getResult();
        this.pageNum = resultPage.getPageNum();
        this.pageSize = resultPage.getPageSize();
        this.count = resultPage.getTotal();
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
