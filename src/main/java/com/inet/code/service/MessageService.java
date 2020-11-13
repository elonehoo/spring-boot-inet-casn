package com.inet.code.service;

import com.inet.code.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inet.code.utils.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
public interface MessageService extends IService<Message> {
    /**
     * 进行注册
     * @author HCY
     * @since 2020-11-07
     * @param name 名字
     * @param studentNumber 学号
     * @param telephone 电话号码
     * @param grade 班级
     * @param ua 头文件
     * @param path 路径
     * @return Result风格
     */
    Result getSave(String name, String studentNumber, String telephone, String grade, String ua,String path);

    /**
     * 登录操作,扫码操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    Result getLogin(String ua, String path);

    /**
     * 签到操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @param name 名字
     * @return Result风格
     */
    Result getSignIn(String ua, String name , String path);

    /**
     * 签退操作
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    Result getSignOut(String ua,String name ,String path);

    /**
     * 登录操作,并且更新UA
     * @author HCY
     * @since 2020-11-07
     * @param studentNumber 学号
     * @param password 密码
     * @param ua 头请求
     * @param path 路径
     * @return Result风格
     */
    Result getRegister(String studentNumber, String password, String ua, String path);

    /**
     * 按钮的签到
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    Result getCheckInCode(String token, String path);

    /**
     * 按钮的签退
     * @author HCY
     * @since 2020-11-08
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    Result getSignOutInCode(String token, String path);

    /**
     * 展示所有的用户以及签到时间
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    Result getListMessage(String path);

    /**
     * 展示所有未同意签到的同学
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    Result getMaybeMessage(String path);

    /**
     * 展示所有已经在签到的同学
     * @author HCY
     * @since 2020-11-08
     * @param path 路径
     * @return Result风格
     */
    Result getTrueMessage(String path);

    /**
     * 通过姓名查询
     * @author HCY
     * @since 2020-11-08
     * @param name 姓名
     * @param path 路径
     * @return Result风格
     */
    Result getQueryName(String name, String path);

    /**
     * 通过UUID删除用户
     * @author HCY
     * @since 2020-11-08
     * @param uuid uuid
     * @param path 路径
     * @return Result风格
     */
    Result getRemoveUUID(String uuid, String path);

    /**
     * 同意用户的签到请求
     * @author HCY
     * @since 2020-11-08
     * @param studentNumber 学号
     * @param path 路径
     * @return Result风格
     */
    Result getConsent(String studentNumber,String path);

    /**
     * 拒绝用户的请求
     * @param studentNumber 学号
     * @param path 路径
     * @return Result风格
     */
    Result getRefuse(String studentNumber, String path);

    /**
     * 重置用户的签到数据
     * @author HCY
     * @since 2020-11-09
     * @param path 路径
     * @return Result风格
     */
    Result getReset(String path);


    /**
     * 下载文件
     * @author HCY
     * @since 2020-11-09
     * @param response HttpServletResponse
     * @param path 路径
     * @return Result风格
     */
    Result getDown(HttpServletResponse response, String path);

    /**
     * 修改密码
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param path 路径
     * @return Result风格
     */
    Result getStorageChgPassword(String token, String oldPassword, String newPassword, String path);

    /**
     * 通过token获取名字
     * @author HCY
     * @since 2020-11-13
     * @param token 令牌
     * @param path 路径
     * @return Result风格
     */
    Result getUserName(String token, String path);
}
