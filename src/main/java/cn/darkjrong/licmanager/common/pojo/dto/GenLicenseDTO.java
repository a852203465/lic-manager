package cn.darkjrong.licmanager.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 生成许可证DTO
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Data
@ApiModel("生成许可证参数")
public class GenLicenseDTO implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "主键 不能为空")
    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    /**
     * 证书主题
     */
    @NotBlank(message = "证书主题 不能为空")
    @ApiModelProperty(value = "证书主题", required = true)
    private String subject;

    /**
     * 申请码
     */
    @NotBlank(message = "申请码 不能为空")
    @ApiModelProperty(value = "申请码", required = true)
    private String appCode;

    /**
     * 过期时间
     */
    @NotNull(message = "过期时间 不能为空")
    @ApiModelProperty(value = "过期时间", required = true)
    private Long expiredTime;

    /**
     * 用户数量
     */
    @NotNull(message = "用户数量 不能为空")
    @ApiModelProperty(value = "用户数量", required = true)
    private Integer consumerAmount;

    /**
     * 描述
     */
    @NotBlank(message = "描述 不能为空")
    @ApiModelProperty(value = "描述", required = true)
    private String description;






}
