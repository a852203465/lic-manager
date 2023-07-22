package cn.darkjrong.licmanager.common.pojo.dto;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 * 秘钥库DTO
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeystoreDTO extends Base implements Serializable {

    private static final long serialVersionUID = 3072774236798956556L;

    /**
     * 名称
     */
    @NotBlank(message = "名称 不能为空")
    private String name;

    /**
     * 私钥证书有效期(单位：年), 默认：1
     */
    @NotNull(message = "私钥证书有效期 不能为空")
    private Integer validity;

    /**
     * 私钥密码
     */
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$", message = "密码必须由字母和数字组成的至少6个字符组成")
    @NotBlank(message = "私钥密码 不能为空")
    private String password;


}
