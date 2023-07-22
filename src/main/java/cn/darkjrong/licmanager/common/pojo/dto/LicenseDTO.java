package cn.darkjrong.licmanager.common.pojo.dto;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * licenseDTO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("license信息参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class LicenseDTO extends Base implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID 不能为空")
    @ApiModelProperty(value = "项目ID", required = true)
    private Long projectId;

    /**
     * 名称
     */
    @NotBlank(message = "名称 不能为空")
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 证书主题
     */
    @NotBlank(message = "证书主题 不能为空")
    @ApiModelProperty(value = "证书主题", required = true)
    private String subject;

    /**
     * 申请码
     */
    @ApiModelProperty(value = "申请码")
    private String appCode;




}