package cn.darkjrong.licmanager.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 秘钥库过滤查询DTO
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@ApiModel("秘钥库过滤查询参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class KeystoreFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 3072774236798956556L;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;


}
