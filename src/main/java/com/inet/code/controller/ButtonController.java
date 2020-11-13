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
import java.sql.ResultSet;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/Button")
@CrossOrigin
@Api(tags = {"按钮的操作"},description = "用户模块")
public class ButtonController {
    @Resource
    private MessageService messageService;

    @Resource
    private HttpServletRequest request;

    /**
     * 获取名字
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @return Result风格的返回值
     */
    @ApiOperation("获取姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
    })
    @GetMapping("/userName")
    public Result getUserName(@RequestParam("Token") String token){
        return messageService.getUserName(token,"/apply/Button/userName");
    }


    /**
     * 用户注册
     * @param name 名字
     * @param studentNumber 学号
     * @param telephone 电话
     * @param grade 班级
     * @return Result风格的返回值
     */
    @ApiOperation("注册操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Name",value="姓名",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="StudentNumber",value="学号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Telephone",value="电话号码",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Grade",value="班级",dataType="String", paramType = "query",example=""),
    })
    @PostMapping("/enroll")
    public Result postEnroll(@RequestParam("Name") String name,
                             @RequestParam("StudentNumber") String studentNumber,
                             @RequestParam("Telephone") String telephone,
                             @RequestParam("Grade") String grade){
        return messageService.getSave(
                name
                ,studentNumber
                ,telephone
                ,grade
                ,request.getHeader("User-Agent")
                ,"/apply/Button/enroll"
        );
    }

    /**
     * 按钮的登录操作
     * @author HCY
     * @since 2020-11-08
     * @param studentNumber 学号
     * @param password 密码
     * @return Result
     */
    @ApiOperation("登录操作,进行更新UA")
    @ApiImplicitParams({
            @ApiImplicitParam(name="StudentNumber",value="学号",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="Password",value="密码",dataType="String", paramType = "query",example=""),
    })
    @PostMapping("/register")
    public Result postRegister(@RequestParam("StudentNumber") String studentNumber,
                               @RequestParam("Password") String password){

        return messageService.getRegister(studentNumber
                ,password
                ,request.getHeader("User-Agent")
                ,"/apply/Button/register");
    }

    /**
     * 按钮签到操作
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @return Result
     */
    @ApiOperation("按钮签到操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
    })
    @GetMapping("/checkInCode")
    public Result getCheckInCode(@RequestParam("Token") String token){
        return messageService.getCheckInCode(
                token,
                "/apply/Button/checkInCode");
    }

    /**
     * 签退操作
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @return Result
     */
    @ApiOperation("按钮签退操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
    })
    @GetMapping("/signOutInCode")
    public Result getSignBackOperation(@RequestParam("Token") String token){
        return messageService.getSignOutInCode(
                token
                ,"/apply/Button/signOutInCode");
    }

    /**
     * 修改密码
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return Result
     */
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Token",value="令牌",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="OldPassword",value="旧密码",dataType="String", paramType = "query",example=""),
            @ApiImplicitParam(name="NewPassword",value="新密码",dataType="String", paramType = "query",example=""),
    })
    @PutMapping("/storageChgPassword")
    public Result getStorageChgPassword(@RequestParam("Token") String token,
                                        @RequestParam("OldPassword") String oldPassword,
                                        @RequestParam("NewPassword") String newPassword){
        return messageService.getStorageChgPassword(
                token
                ,oldPassword
                ,newPassword
                ,"/apply/Button/storageChgPassword");

    }


}
