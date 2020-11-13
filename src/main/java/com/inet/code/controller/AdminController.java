package com.inet.code.controller;

import com.inet.code.service.MessageService;
import com.inet.code.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * AdminController
 *
 * @author HCY
 * @since 2020/11/8
 */
@RestController
@RequestMapping("/Index")
@CrossOrigin
@Api(tags = {"管理的操作"},description = "管理模块")
public class AdminController {
    @Resource
    private MessageService messageService;

    /**
     * 获取所有用户的信息
     * @author HCY
     * @since 2020-11-08
     * @return Result
     */
    @ApiOperation("获取所有用户的信息")
    @GetMapping("/listMessage")
    public Result getListMessage(){
        return messageService.getListMessage("/apply/Index/listMessage");
    }

    /**
     * 获取所有签到了但是还没同意的用户
     * @author HCY
     * @since 2020-11-08
     * @return Result
     */
    @ApiOperation("获取所有签到了但是还没同意的用户")
    @GetMapping("/maybe")
    public Result getMaybe(){
        return messageService.getMaybeMessage("/apply/Index/maybe");
    }

    /**
     * 获取所有已经签到了并且同意的用户
     * @author HCY
     * @since 2020-11-08
     * @return Result
     */
    @ApiOperation("获取所有已经签到了并且同意的用户")
    @GetMapping("/true")
    public Result getTrue(){
        return messageService.getTrueMessage("/apply/Index/true");
    }

    /**
     * 通过姓名进行查询
     * @author HCY
     * @since 2020-10-08
     * @param name 姓名
     * @return Result
     */
    @ApiOperation("通过姓名进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="Name",value="姓名",dataType="String", paramType = "query"),
    })
    @GetMapping("/query")
    public Result getQuery(@RequestParam("Name") String name){
        return messageService.getQueryName(name,"/apply/Index/query");
    }

    /**
     * 删除用户
     * @author HCY
     * @since 2020-11-08
     * @param uuid 序号
     * @return Result
     */
    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="UUID",value="uuid",dataType="String", paramType = "query"),
    })
    @DeleteMapping("/remove")
    public Result deleteRemove(@RequestParam("UUID") String uuid){
        return messageService.getRemoveUUID(uuid,"/apply/Index/remove");
    }

    /**
     * 同意用户的请求
     * @author HCY
     * @since 2020-11-08
     * @param studentNumber 学号
     * @return Result
     */
    @ApiOperation("同意用户的签到请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name="studentNumber",value="学号",dataType="String", paramType = "query"),
    })
    @GetMapping("/consent")
    public Result getConsent(@RequestParam("studentNumber") String studentNumber){
        return messageService.getConsent(studentNumber,"/apply/Index/consent");
    }

    /**
     * 拒绝用户的请求以及强制下线
     * @author HCY
     * @since 2020-11-08
     * @param studentNumber 学号
     * @return Result
     */
    @ApiOperation("拒绝用户的签到请求,以及强制下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name="studentNumber",value="学号",dataType="String", paramType = "query"),
    })
    @GetMapping("/refuse")
    public Result getRefuse(@RequestParam("studentNumber") String studentNumber){
        return messageService.getRefuse(studentNumber,"/apply/Index/refuse");
    }

    /**
     * 重置所有用户的签到请求和数据
     * @author HCY
     * @since 2020-11-09
     * @return Result
     */
    @ApiOperation("重置用户的签到时间和状态,不要点,不要测试")
    @GetMapping("/reset")
    public Result getReset(){
        return messageService.getReset("/apply/Index/reset");
    }

    @ApiOperation("返回下载的网址,对于网址进行GET请求")
    @GetMapping("/down")
    public Result getDown(HttpServletResponse response){
        return messageService.getDown(response,"/apply/Index/down");
    }
}
