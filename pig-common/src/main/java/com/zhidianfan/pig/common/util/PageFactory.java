package com.zhidianfan.pig.common.util;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/26
 * @Modified By:
 */

public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();

        int limit = Integer.valueOf(ServletRequestUtils.getStringParameter(request, "limit", "1"));     //每页多少条数据
        int offset = Integer.valueOf(ServletRequestUtils.getStringParameter(request, "page", "15"));   //每页的偏移量(本页当前有多少条) page
        String sort = request.getParameter("orderByField");         //排序字段名称
        String order = request.getParameter("isAsc");       //asc或desc(升序或降序)
        Page<T> page = new Page<>(limit, offset);
        if (StringUtils.isEmpty(sort)) {
            page.setOpenSort(false);
            return page;
        } else {
            page.setOrderByField(sort);
            if ("true".equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}
