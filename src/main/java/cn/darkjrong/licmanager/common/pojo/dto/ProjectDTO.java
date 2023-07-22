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
 * 项目信息DTO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("项目信息参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDTO extends Base implements Serializable {

    private static final long serialVersionUID = -5679935007914513221L;

    /**
     * 秘钥库ID
     */
    @NotNull(message = "秘钥库ID 不能为空")
    @ApiModelProperty(value = "秘钥库ID", required = true)
    private Long keystoreId;

    /**
     * 名称
     */
    @NotBlank(message = "名称 不能为空")
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 公司
     */
    @NotBlank(message = "公司 不能为空")
    @ApiModelProperty(value = "公司", required = true)
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