package cn.darkjrong.licmanager.common.pojo.query;

import cn.darkjrong.licmanager.common.pojo.dto.ProjectFilterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目信息查询对象
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectQuery extends ProjectFilterDTO implements Serializable {

    private static final long serialVersionUID = -5679935007914513221L;



}