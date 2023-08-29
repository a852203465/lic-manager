package cn.darkjrong.licmanager.common.pojo.vo;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 秘钥库VO
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@ApiModel("秘钥库信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class KeystoreVO extends Base implements Serializable {

    private static final long serialVersionUID = 3072774236798956556L;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 私钥证书有效期(单位：年), 默认：1
     */
    @ApiModelProperty("私钥证书有效期(单位：年), 默认：1")
    private Integer validity;

    /**
     * 秘钥库密码
     */
    @ApiModelProperty("秘钥库密码")
    private String storePwd;

    /**
     * 私钥密码
     */
    @ApiModelProperty("私钥密码")
    private String privatePwd;

    /**
     * 公钥密码
     */
    @ApiModelProperty("公钥密码")
    private String publicPwd;

    /**
     * 私钥
     */
//    private byte[] privateKey;

    /**
     * 公钥
     */
    @ApiModelProperty("公钥")
    private byte[] publicKey;




}
