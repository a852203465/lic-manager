package cn.darkjrong.licmanager.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目信息过滤DTO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("项目信息过滤参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = -5679935007914513221L;

    /**
     * 秘钥库ID
     */
    @ApiModelProperty(value = "秘钥库ID")
    private Long keystoreId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private String company;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contact;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;



}