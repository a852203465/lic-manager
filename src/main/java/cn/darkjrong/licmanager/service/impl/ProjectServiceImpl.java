package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.pojo.dto.PageDTO;
import cn.darkjrong.licmanager.common.pojo.dto.ProjectDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Project;
import cn.darkjrong.licmanager.common.pojo.query.ProjectQuery;
import cn.darkjrong.licmanager.common.pojo.vo.ProjectVO;
import cn.darkjrong.licmanager.mapper.ProjectMapper;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.darkjrong.licmanager.service.ProjectService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 项目信息服务实现类
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Slf4j
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectMapper, Project, Project, ProjectVO> implements ProjectService{

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private KeystoreService keystoreService;

    @Override
    public List<Project> queryList(PageDTO pageDTO) {
        ProjectQuery query = new ProjectQuery();
        BeanUtil.copyProperties(pageDTO, query);
        return projectMapper.findProject(query);
    }

    @Override
    public ProjectVO queryById(Serializable id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(this.getById(id));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProject(ProjectDTO projectDTO) {

        Assert.notNull(keystoreService.queryById(projectDTO.getKeystoreId()),
                ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());

        Project project = projectMapper.findProjectByKeystoreIdAndName(projectDTO.getKeystoreId(), projectDTO.getName());
        Assert.isNull(project, ResponseEnum.THE_PROJECT_ALREADY_EXISTS.getMessage());
        project = new Project();
        BeanUtil.copyProperties(projectDTO, project);
        project.setCreatedTime(DateUtil.current());
        this.saveOrUpdate(project);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProject(ProjectDTO projectDTO) {

        Assert.notNull(keystoreService.queryById(projectDTO.getKeystoreId()),
                ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());

        Project project = this.getById(projectDTO.getId());
        Assert.notNull(project, ResponseEnum.THE_PROJECT_DOES_NOT_EXIST.getMessage());

        if (!StrUtil.equals(project.getName(), projectDTO.getName())) {
            Assert.isNull(projectMapper.findProjectByKeystoreIdAndName(projectDTO.getKeystoreId(), projectDTO.getName()),
                    ResponseEnum.THE_PROJECT_ALREADY_EXISTS.getMessage());
        }

        BeanUtil.copyProperties(projectDTO, project);
        project.setUpdatedTime(DateUtil.current());
        this.saveOrUpdate(project);
    }

    @Override
    public List<ProjectVO> findProjectByKeystoreId(Long keystoreId) {
        Assert.notNull(keystoreId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(projectMapper.findProjectByKeystoreId(keystoreId));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteProject(List<Long> ids) {
        Assert.notEmpty(ids, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        ids.forEach(this::delete);
    }

    @Override
    public ProjectVO objectConversion(Project project) {
        ProjectVO projectVO = super.objectConversion(project);
        if (ObjectUtil.isNotNull(projectVO)) {
            projectVO.setKeystore(keystoreService.queryById(project.getKeystoreId()));
        }
        return projectVO;
    }
}
