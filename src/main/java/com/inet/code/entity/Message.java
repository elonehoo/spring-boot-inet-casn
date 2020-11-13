package com.inet.code.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author HCY
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "message")
@ExcelTarget("签到统计表")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("memberUUID")
    @ExcelIgnore
    private String memberUUID;

    @TableField("memberNumber")
    @Excel(name = "学号", width = 13.0)
    private String memberNumber;

    @TableField("memberName")
    @Excel(name = "姓名")
    private String memberName;

    @TableField("memberPhone")
    @ExcelIgnore
    private String memberPhone;

    @TableField("memberClazz")
    @Excel(name = "班级",width = 15.0)
    private String memberClazz;

    @TableField("memberUA")
    @ExcelIgnore
    private String memberUA;

    @TableField(exist = false)
    @Excel(name = "签到时间(h)",width = 13.0)
    private String totalTime;

    @TableField(exist = false)
    @ExcelIgnore
    private String checkIS;

    public Message() {
    }

    public Message(String memberUUID, String memberNumber, String memberName, String memberPhone, String memberClazz, String memberUA) {
        this.memberUUID = memberUUID;
        this.memberNumber = memberNumber;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.memberClazz = memberClazz;
        this.memberUA = memberUA;
    }
}
