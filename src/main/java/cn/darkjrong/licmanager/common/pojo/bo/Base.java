package cn.darkjrong.licmanager.common.pojo.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共属性
 *
 * @author Rong.Jia
 * @date 2023/07/19
 */
@Data
public class Base implements Serializable {

    /**
     * 主键
     */
    private Long id;

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
