package com.zhidianfan.pig.yd.config;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigVersion;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LogVersion;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigVersionService;
import com.zhidianfan.pig.yd.moduler.common.service.ILogVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program pig
 * @description:
 * @author: 李凌峰
 * @create: 2019/03/12 16:02
 */
@Slf4j
@Component
public class VersionFilter implements Filter {

    @Autowired
    private IConfigVersionService configVersionService;

    @Autowired
    private ILogVersionService logVersionService;

    private static VersionFilter versionFilter;


    /**
     * 替换StringToLong
     * @param version
     * @return
     */
    private Long replaceVersion(String version){
        version = version.replaceAll("\\.","")
                .replaceAll("-","");
        Long newVersion = Long.valueOf(version);
        return newVersion;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        versionFilter = this;
        versionFilter.configVersionService = this.configVersionService;
        versionFilter.logVersionService = this.logVersionService;
        log.info("过滤器初始化");
    }

    /**
     * 版本过滤
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        log.info("执行过滤器逻辑");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("utf8");
//        校验
        String loginUser = httpServletRequest.getHeader("login_user");
        String loginVersion = httpServletRequest.getHeader("login_version");
        String clientType = httpServletRequest.getHeader("client_type");
        if(loginUser == null || loginVersion == null || clientType == null){
            filterChain.doFilter(request, response);
            return;
        }

        ConfigVersion configVersion = versionFilter.configVersionService.selectOne(new EntityWrapper<ConfigVersion>().eq("client_type",clientType));
        if(configVersion == null || configVersion.getEnableVersion() == null || configVersion.getLastestVersion() == null){
            Map map = new HashMap();
            map.put("code",500);
            map.put("msg","Mistake Version");
            String result = JsonUtils.obj2Json(map);
            httpServletResponse.getOutputStream().write(result.getBytes("utf8"));
            return;
        }
//        取得版本号
        Long nowloginVersion = replaceVersion(loginVersion);
        Long enableVersion = replaceVersion(configVersion.getEnableVersion());

        LogVersion logVersion = new LogVersion();
        logVersion.setLoginVersion(loginVersion);
        logVersion.setLastestVersion(configVersion.getLastestVersion());
        logVersion.setUpdatedTime(new Date());
//        当前登录设备及账号是否存在于记录表
        LogVersion logVersionExist = versionFilter.logVersionService.selectOne(new EntityWrapper<LogVersion>().eq("login_user",loginUser).eq("client_type", clientType));
//        如果当前登录版本号在允许区间内
        if(nowloginVersion >= enableVersion){
            filterChain.doFilter(request, response);
            logVersion.setIsEnable(1);
            if(logVersionExist != null){
                boolean updateLog = versionFilter.logVersionService.update(logVersion, new EntityWrapper<LogVersion>().eq("login_user",loginUser).eq("client_type", clientType));
            }else {
                logVersion.setLoginUser(loginUser);
                logVersion.setClientType(clientType);
                logVersion.setCreatedTime(new Date());
                boolean insertLog = versionFilter.logVersionService.insert(logVersion);
            }
            return;
        }
//        版本不符
        logVersion.setIsEnable(0);
        if(logVersionExist != null){
            boolean updateLog = versionFilter.logVersionService.update(logVersion, new EntityWrapper<LogVersion>().eq("login_user",loginUser).eq("client_type", clientType));
        }else {
            logVersion.setLoginUser(loginUser);
            logVersion.setClientType(clientType);
            logVersion.setCreatedTime(new Date());
            boolean insertLog = versionFilter.logVersionService.insert(logVersion);
        }
        Map map = new HashMap();
        map.put("code",500);
        map.put("msg","Mistake Version");
        String result = JsonUtils.obj2Json(map);
        httpServletResponse.getOutputStream().write(result.getBytes("utf8"));
        return;
    }

    @Override
    public void destroy() {

    }
}
