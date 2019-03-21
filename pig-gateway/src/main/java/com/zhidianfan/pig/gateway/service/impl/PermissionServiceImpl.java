
package com.zhidianfan.pig.gateway.service.impl;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.zhidianfan.pig.common.vo.MenuVO;
import com.zhidianfan.pig.gateway.component.dto.TipCommon;
import com.zhidianfan.pig.gateway.feign.MenuService;
import com.zhidianfan.pig.gateway.feign.UserService;
import com.zhidianfan.pig.gateway.service.PermissionService;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lengleng
 * @date 2017/10/28
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private UserService userService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<MenuVO> urls = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                if (!StrUtil.equals(authority.getAuthority(), "ROLE_USER")) {
                    Set<MenuVO> menuVOSet = menuService.findMenuByRole(authority.getAuthority());
                    if (CollUtil.isNotEmpty(menuVOSet)) {
                        CollUtil.addAll(urls, menuVOSet);
                    }
                }
            }

            //判断在pig中，权限是否正常
            for (MenuVO menu : urls) {
                if (StringUtils.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        String url = request.getRequestURI();

        if (antPathMatcher.match("/admin/**", url)
                || antPathMatcher.match("/ydservice/**", url)
                || antPathMatcher.match("/ydsms/**", url)
                || antPathMatcher.match("/auth/**", url)
                || antPathMatcher.match("/ydlog/**", url)
//                || antPathMatcher.match("/**", url)
                || antPathMatcher.match("/ydbase/**", url)) {//暂时都放开

            log.info("对：{} 放行", url);

            return hasPermission;
        }

        //客户端校验通过后，准备校验下一步校验
        String username = request.getHeader("auth_username");
        String password = request.getHeader("auth_password");
        String client = request.getHeader("auth_client_type");
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(username, password, client)) {
            return false;
        }
        //判断用户名密码
        TipCommon tipCommon = userService.checkAuth(username, password, client);
        if (200 != tipCommon.getCode()) {
            hasPermission = false;
            return hasPermission;
        }
        //判断用户名与url之间的关系
        log.info("检查 {} 与 {} 的权限关系", username, url);
        tipCommon = userService.checkUsernameUrl(username, url);
        if (200 == tipCommon.getCode()) {
            hasPermission = true;
            return hasPermission;
        }

        return hasPermission;
    }
}
