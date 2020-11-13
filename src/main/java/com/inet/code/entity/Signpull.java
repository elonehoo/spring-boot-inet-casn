package com.inet.code.entity;

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
@TableName(value = "signpull")
public class Signpull implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("memberUUID")
    private String memberUUID;

    @TableField("memberNumber")
    private String memberNumber;

    @TableField("checkIS")
    private String checkIS;

    @TableField("checkStart")
    private String checkStart;

    @TableField("checkTerminate")
    private String checkTerminate;

    @TableField("checkTime")
    private String checkTime;

    public Signpull(String memberUUID, String memberNumber, String checkIS, String checkStart, String checkTerminate, String checkTime) {
        this.memberUUID = memberUUID;
        this.memberNumber = memberNumber;
        this.checkIS = checkIS;
        this.checkStart = checkStart;
        this.checkTerminate = checkTerminate;
        this.checkTime = checkTime;
    }

    public Signpull() {
    }
}
