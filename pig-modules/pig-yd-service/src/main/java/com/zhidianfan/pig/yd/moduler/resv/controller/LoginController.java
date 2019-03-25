package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.util.PasswordUtils;
import com.zhidianfan.pig.common.util.UserUtils;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;

import com.zhidianfan.pig.yd.moduler.common.service.*;

import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;

import com.zhidianfan.pig.yd.moduler.resv.bo.SellerUserBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmallAppUserBo;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessService;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessSmsService;
import com.zhidianfan.pig.yd.moduler.resv.service.LoginService;
import com.zhidianfan.pig.yd.moduler.resv.service.LoginVersionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-20
 * @Time: 16:11
 */
@Api("登录信息")
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {


    @Resource
    private ISellerUserService iSellerUserService;
    @Resource
    private ISmallAppUserService iSmallAppUserService;

    @Resource
    private AuthFeign authFeign;

    @Autowired
    private LoginVersionLogService loginVersionLogService;

    @Resource
    private BusinessSmsService businessSmsService;


    @Resource
    private ISysSyncUserBusinessService iSysSyncUserBusinessService;

    @Resource
    private ISysSyncUserService iSysSyncUserService;

    @Resource
    private IBusinessService ibusinessService;

    @Resource
    private LoginService loginService;

    @Autowired
    private BusinessService businessService;






    @ApiOperation("网页后台用户")
    @GetMapping("webStoreUser")
    public ResponseEntity  store(HttpServletRequest request){

        SellerUserBo sellerUserBo = new SellerUserBo();


        String password = request.getHeader("auth_password");
        String client = request.getHeader("auth_client_type");

        String username ;
        try {
            username = URLDecoder.decode(request.getHeader("auth_username"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("账号格式错误");
        }
        //判断用户名和密码

        TipCommon tipCommon = authFeign.checkAuth(username, password, client);
        if (200 != tipCommon.getCode()) {
           throw new RuntimeException("账号或密码错误");
        }

        if(!PasswordUtils.checkSimplePassword(password)){
            tipCommon.setCode(500);
            tipCommon.setMsg("密码过于简单");
            return ResponseEntity.badRequest().body(tipCommon);
        }

        //String userName = UserUtils.getUserName();

        SellerUser condition = new SellerUser();
        condition.setLoginName(username);
        SellerUser sellerUser = iSellerUserService.selectOne(new EntityWrapper<>(condition));
        Integer businessId = Integer.valueOf(sellerUser.getBusinessId());
        BeanUtils.copyProperties(sellerUser,sellerUserBo);

        //查询角色权限
        sellerUserBo=loginService.findRoleMenus(sellerUserBo, username, businessId);

        sellerUserBo.setBusinessList(businessService.getEmpBrandList(sellerUser.getId(),2));

        return ResponseEntity.ok(sellerUserBo);
    }






    /**
     * 登录版本日志
     * @param loginVersionLog 登录版本日志
     * @return 记录是否成功
     */
    @PostMapping("versionlog")
    public ResponseEntity versionLog(@RequestBody LoginVersionLog loginVersionLog){


        //记录该账户十次登录记录
        Boolean flag = loginVersionLogService.insertVersionLog(loginVersionLog.getTerminal(),loginVersionLog.getAccount(),loginVersionLog.getVersion());


        return ResponseEntity.ok(flag? SuccessTip.SUCCESS_TIP: ErrorTip.ERROR_TIP);
    }


    @ApiOperation("小程序用户")
    @GetMapping("smallAppUser")
    public ResponseEntity<SmallAppUser>  smallAppUser(HttpServletRequest request) throws UnsupportedEncodingException {


        String username =  URLDecoder.decode(request.getHeader("auth_username"),"UTF-8");
        String password = request.getHeader("auth_password");
        String client = request.getHeader("auth_client_type");
        //判断用户名和密码

        TipCommon tipCommon = authFeign.checkAuth(username, password, client);
        if (200 != tipCommon.getCode()) {
            throw new RuntimeException("账号或密码错误");
        }

       // String userName = UserUtils.getUserName();

        SmallAppUser condition = new SmallAppUser();
        condition.setLoginName(username);

        log.info("查询小程序账号信息：{} ",username);
        SmallAppUser sellerUser = iSmallAppUserService.selectOne(new EntityWrapper<>(condition));
        return ResponseEntity.ok(sellerUser);
    }


    @ApiOperation("小程序用户信息")
    @GetMapping("smallAppUser/info")
    public ResponseEntity<SmallAppUserBo>  smallAppUser(String account){

        SmallAppUserBo smallAppUserBo = new SmallAppUserBo();

        SmallAppUser condition = new SmallAppUser();
        condition.setLoginName(account);
        SmallAppUser sellerUser = iSmallAppUserService.selectOne(new EntityWrapper<>(condition));
        if(sellerUser==null){
            throw new RuntimeException("账号错误");
        }

        BeanUtils.copyProperties(sellerUser,smallAppUserBo);

        SysSyncUser username = new SysSyncUser();
        username.setUsername(account);
        SysSyncUser sysSyncUser = iSysSyncUserService.selectOne(new EntityWrapper<>(username));
        if(sysSyncUser==null){
            throw new RuntimeException("数据异常");
        }

        SysSyncUserBusiness userId = new SysSyncUserBusiness();
        userId.setUserId(sysSyncUser.getUserId());
        SysSyncUserBusiness syncUserBusiness = iSysSyncUserBusinessService.selectOne(new EntityWrapper<>(userId));
        if(syncUserBusiness==null){
            throw new RuntimeException("数据异常");
        }
        String role = syncUserBusiness.getRole();
        smallAppUserBo.setGroupName(role.split(":")[2]);
        smallAppUserBo.setBusinessName(role.split(":")[4].replace("@",""));
        return ResponseEntity.ok(smallAppUserBo);
    }

    @ApiOperation("小程序用户头像修改")
    @PostMapping("smallAppUser/info/editIcon")
    public ResponseEntity<Tip>  smallAppUserEdit(String account,String iconUrl){

        iconUrl=StringEscapeUtils.unescapeHtml4(iconUrl);
        SmallAppUser condition = new SmallAppUser();
        condition.setLoginName(account);
        SmallAppUser sellerUser = iSmallAppUserService.selectOne(new EntityWrapper<>(condition));
        if(sellerUser==null){
            throw new RuntimeException("账号错误");
        }
        sellerUser.setIconUrl(iconUrl);
        boolean b = iSmallAppUserService.updateById(sellerUser);
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);

    }


    @ApiOperation("发送重置密码验证码")
    @GetMapping("/reset/password/code/send")
    public ResponseEntity<Tip> sendResetPasswordCode(String mobile){
        //判断手机号码是否正确
        SuccessTip tip = authFeign.userFindExist(mobile);
        if(200!=tip.getCode()){
          return ResponseEntity.ok(new ErrorTip(400,"此手机号未注册账号"));
        }
        boolean b = businessSmsService.sendResetPasswordCode(mobile);
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }

    @ApiOperation("验证重置密码验证码")
    @GetMapping("/reset/password/code/verify")
    public ResponseEntity<Tip> verifyResetPasswordCode(String mobile,String code){

        boolean b = businessSmsService.verifyResetPasswordCode(mobile, code);
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }


    @ApiOperation("更改密码根据手机号")
    @PostMapping("/reset/password")
    public ResponseEntity<Tip> resetPassword(String mobile,String password,String code){

        boolean b = businessSmsService.resetPassword(mobile,password,code);
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }


    @ApiOperation("更改密码根据账号")
    @PostMapping("/reset/password/account")
    public ResponseEntity<Tip> resetPasswordAccount(String account,String password,String oldPassword){

        SuccessTip successTip = authFeign.updateUserPasswordAccount(account, password, oldPassword);

        //更新business密码
        if(successTip.getCode()==200) {
            Business business = new Business();
            business.setLoginPassword(password);
            Business condition = new Business();
            condition.setLoginUser(account);
            ibusinessService.update(business, new EntityWrapper<>(condition));
        }

        return ResponseEntity.ok(successTip);
    }





}
