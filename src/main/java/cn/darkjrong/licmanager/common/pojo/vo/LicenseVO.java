package cn.darkjrong.licmanager.common.pojo.vo;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * license VO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("license信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class LicenseVO extends Base implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;

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



}