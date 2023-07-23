package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.pojo.dto.ProjectDTO;
import cn.darkjrong.licmanager.common.pojo.dto.ProjectFilterDTO;
import cn.darkjrong.licmanager.common.pojo.vo.PageVO;
import cn.darkjrong.licmanager.common.pojo.vo.ProjectVO;
import cn.darkjrong.licmanager.common.pojo.vo.ResponseVO;
import cn.darkjrong.licmanager.service.ProjectService;
import cn.hutool.extra.validation.ValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 项目信息控制器
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Slf4j
@Validated
@Api(tags = "项目信息管理")
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation("添加项目")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> saveProject(@Validated @RequestBody ProjectDTO projectDTO) {
        log.info("saveProject {}", projectDTO.toString());
        projectDTO.setCreatedUser(getAccount());
        projectService.saveProject(projectDTO);
        return ResponseVO.success();
    }

    @ApiOperation("修改项目")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> updateProject(@Validated @RequestBody ProjectDTO projectDTO) {
        log.info("updateProject {}", projectDTO.toString());
        projectDTO.setUpdatedUser(getAccount());
        projectService.updateProject(projectDTO);
        return ResponseVO.success();
    }

    @ApiOperation("查询项目")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<PageVO<ProjectVO>> queryProject(@Validated ProjectFilterDTO filterDTO) {
        log.info("queryProject {}", filterDTO.toString());
        ValidationUtil.validate(filterDTO);
        return ResponseVO.success(projectService.page(filterDTO));
    }

    @ApiOperation("删除项目")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class, value = "主键ID", required = true),
    })
    public ResponseVO<Void> deleteProject(@PathVariable("id") @NotNull(message = "主键ID 不能为空") Long id) {
        log.info("deleteProject {}", id);
        projectService.delete(id);
        return ResponseVO.success();
    }

    @ApiOperation("批量删除项目")
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> deleteProjects(@RequestBody @Validated List<Long> ids) {
        log.info("deleteProjects {}", ids);
        projectService.deleteProject(ids);
        return ResponseVO.success();
    }














}
