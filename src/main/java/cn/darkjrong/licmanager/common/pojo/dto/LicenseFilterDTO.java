package cn.darkjrong.licmanager.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * license信息过滤DTO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("license信息过滤参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class LicenseFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 3365919605740992275L;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 证书主题
     */
    @ApiModelProperty(value = "证书主题")
    private String subject;






}