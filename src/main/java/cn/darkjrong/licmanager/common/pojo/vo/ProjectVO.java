package cn.darkjrong.licmanager.common.pojo.vo;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目信息VO
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@ApiModel("项目信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectVO extends Base implements Serializable {

    private static final long serialVersionUID = -5679935007914513221L;

    /**
     * 秘钥库
     */
    @ApiModelProperty("秘钥库")
    private KeystoreVO keystore;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 公司
     */
    @ApiModelProperty("公司")
    private String company;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contact;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String telephone;



}