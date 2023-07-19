package cn.darkjrong.licmanager.common.pojo.dto;

import cn.darkjrong.licmanager.common.pojo.bo.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

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
    private String name;

    /**
     * 私钥证书有效期(单位：年), 默认：1
     */
    private Integer validity;

    /**
     * 私钥密码
     */
    private String password;

    /**
     * 私钥
     */
    private MultipartFile privateKey;

    /**
     * 公钥
     */
    private MultipartFile publicKey;




}
