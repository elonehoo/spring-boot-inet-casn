package com.inet.code.controller;


import com.inet.code.service.MessageService;
import com.inet.code.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/QuickMark")
@CrossOrigin
@Api(tags = {"扫码的操作"},description = "用户模块")
public class QuickMarkController {
    @Resource
    private HttpServletRequest request;

    @Resource
    private MessageService messageService;



    /**
     * 登录,扫码操作
     * @author HCY
     * @since 2020-11-07
     * @return Result
     */
    @ApiOperation("扫码,进行签到或者签退处理")
    @GetMapping("/login")
    public Result getLogin(){
        return messageService.getLogin(
                request.getHeader("User-Agent")
                ,"/apply/QuickMark/login"
        );
    }



}
