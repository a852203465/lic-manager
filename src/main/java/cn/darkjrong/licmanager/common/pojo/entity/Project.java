package cn.darkjrong.licmanager.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目信息
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Data
@TableName(value = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = -5679935007914513221L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 秘钥库ID
     */
    private Long keystoreId;

    /**
     * 名称
     */
    private String name;

    /**
     * 公司
     */
    private String company;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 电话
     */
    private String telephone;

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