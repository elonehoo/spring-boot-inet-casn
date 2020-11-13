package com.inet.code.mapper;

import com.inet.code.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查看学号是否被注册了
     * @param studentNumber 学号
     * @return 实体类
     */
    Message getByNumber(String studentNumber);

    /**
     * 登录操作,扫码模式
     * @author HCY
     * @since 2020-11-07
     * @param ua 文件
     * @return 实体类
     */
    Message getByUa(String ua);

    /**
     * 登录操作,并且更新ua
     * @author HCY
     * @since 2020-11-07
     * @param studentNumber 学号
     * @param password 密码
     * @return 实体类
     */
    Message getByRegister(String studentNumber, String password);

    /**
     * 获取所有的用户谢谢
     * @author HCY
     * @since 2020-11-08
     * @return 集合
     */
    List<Message> getListMessage();

    /**
     * 展示所有签到状态的同学
     * @author HCY
     * @since 2020-11-08
     * @param starter 状态
     * @return 集合
     */
    List<Message> getStaterMessage(String starter);

    /**
     * 通过姓名查询用户
     * @author HCY
     * @since 2020-11-08
     * @param name 姓名
     * @return 集合
     */
    List<Message> getQueryName(String name);

    /**
     * 获取签到的人的信息
     * @author HCY
     * @since 2020-11-13
     * @param ua 请求头
     * @return 实体类
     */
    Message getByMessageUa(String ua);
}
