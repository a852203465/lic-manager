package cn.darkjrong.licmanager.common.pojo.vo;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * license VO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("许可证信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class LicenseVO extends Base implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;

    /**
     * 项目ID
     */
    @ApiModelProperty("项目信息")
    private ProjectVO project;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 证书主题
     */
    @ApiModelProperty("证书主题")
    private String subject;

    /**
     * 私钥别名
     */
    @ApiModelProperty("私钥别名")
    private String privateAlias;

    /**
     * 申请码
     */
    @ApiModelProperty("申请码")
    private String appCode;

    /**
     * 生成时间
     */
    @ApiModelProperty("生成时间")
    private Long genTime;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private Long expiredTime;

    /**
     * 用户数量
     */
    @ApiModelProperty("用户数量")
    private Integer consumerAmount;

    /**
     * 验证IP地址(0:否,1:是)
     */
    @ApiModelProperty(value = "验证IP地址(0:否,1:是)")
    private Integer checkIpAddress;

    /**
     * 验证mac地址(0:否,1:是)
     */
    @ApiModelProperty(value = "验证mac地址(0:否,1:是)")
    private Integer checkMacAddress;


}