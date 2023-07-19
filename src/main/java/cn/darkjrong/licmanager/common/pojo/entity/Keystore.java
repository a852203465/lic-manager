package cn.darkjrong.licmanager.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 秘钥库
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@Data
@TableName("keystore")
public class Keystore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
    private byte[] privateKey;

    /**
     * 公钥
     */
    private byte[] publicKey;

    /**
     * 创建人
     */
    private String createdUser;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 更新人
     */
    private String updatedUser;

    /**
     * 更新时间
     */
    private Long updatedTime;

    /**
     * 描述
     */
    private String description;


}
