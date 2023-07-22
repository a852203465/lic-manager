package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.common.pojo.dto.ProjectDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Project;
import cn.darkjrong.licmanager.common.pojo.vo.ProjectVO;
import cn.darkjrong.licmanager.service.base.BaseService;

import java.util.List;

/**
 * 项目信息 服务
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
public interface ProjectService extends BaseService<Project, Project, ProjectVO> {

    /**
     * 保存项目
     *
     * @param projectDTO 项目DTO
     */
    void saveProject(ProjectDTO projectDTO);

    /**
     * 更新项目
     *
     * @param projectDTO 项目DTO
     */
    void updateProject(ProjectDTO projectDTO);

    /**
     * 查询项目通过密钥存储库id
     *
     * @param keystoreId 密钥存储库id
     * @return {@link List}<{@link ProjectVO}>
     */
    List<ProjectVO> findProjectByKeystoreId(Long keystoreId);

    /**
     * 删除项目
     *
     * @param ids id
     */
    void deleteProject(List<Long> ids);








}
