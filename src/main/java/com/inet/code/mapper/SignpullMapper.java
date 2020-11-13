package com.inet.code.mapper;

import com.inet.code.entity.Signpull;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
public interface SignpullMapper extends BaseMapper<Signpull> {
    /**
     * 查看状态
     * @author HCY
     * @since 2020-11-07
     * @param ua 头请求
     * @return 实体类
     */
    Signpull getCondition(String ua);

    /**
     * 进行签到操作
     * @param startTime 签到的开始时间
     * @param status 状态
     * @param studentNumber 学号
     * @return 布尔值
     */
    Boolean updateByCondition(String studentNumber,String startTime, String status);

    /**
     * 结束签到
     * @param memberNumber 学号
     * @param endTime 结束时间
     * @param status 状态
     * @param totalTime 总共的时间
     * @return 布尔值
     */
    Boolean updateSignOut(String memberNumber, String endTime, String status, String totalTime);

    /**
     * 修改签到者的状态
     * @param studentNumber 学号
     * @param status 状态
     * @return 布尔值
     */
    Boolean getConsent(String studentNumber, String status);

    /**
     * 重置所有用户的签到请求
     * @author HCY
     * @since 2020-11-09
     * @return 整数
     */
    Integer getReset();
}
