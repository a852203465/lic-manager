package cn.darkjrong.licmanager.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * license信息
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Data
@TableName(value = "license")
public class License implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 证书主题
     */
    private String subject;

    /**
     * 私钥别名
     */
    private String privateAlias;

    /**
     * 申请码
     */
    private String appCode;

    /**
     * 授权文件
     */
    private byte[] lic;

    /**
     * 生成时间
     */
    private Long genTime;

    /**
     * 过期时间
     */
    private Long expiredTime;

    /**
     * 用户数量
     */
    private Integer consumerAmount;

    /**
     * 创建人
     */
    private String createdUser;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 更新人
     */
    private String updatedUser;

    /**
     * 更新时间
     */
    private Long updatedTime;

    /**
     * 描述
     */
    private String description;

}