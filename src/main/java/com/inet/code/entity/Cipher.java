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
@TableName(value = "cipher")
public class Cipher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("memberUUID")
    private String memberUUID;

    @TableField("memberNumber")
    private String memberNumber;

    @TableField("memberCipher")
    private String memberCipher;

    public Cipher() {
    }

    public Cipher(String memberUUID, String memberNumber, String memberCipher) {
        this.memberUUID = memberUUID;
        this.memberNumber = memberNumber;
        this.memberCipher = memberCipher;
    }
}
