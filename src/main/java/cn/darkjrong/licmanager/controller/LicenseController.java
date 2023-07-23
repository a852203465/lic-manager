package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.pojo.dto.GenLicenseDTO;
import cn.darkjrong.licmanager.common.pojo.dto.LicenseDTO;
import cn.darkjrong.licmanager.common.pojo.dto.LicenseFilterDTO;
import cn.darkjrong.licmanager.common.pojo.vo.LicenseVO;
import cn.darkjrong.licmanager.common.pojo.vo.PageVO;
import cn.darkjrong.licmanager.common.pojo.vo.ResponseVO;
import cn.darkjrong.licmanager.service.LicenseService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * license信息控制层
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Slf4j
@Validated
@Api(tags = "许可证管理")
@RestController
@RequestMapping("/license")
public class LicenseController extends BaseController {

    @Autowired
    private LicenseService licenseService;

    @ApiOperation("添加许可证")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> saveLicense(@Validated @RequestBody LicenseDTO licenseDTO) {
        log.info("saveLicense {}", licenseDTO.toString());
        licenseDTO.setCreatedUser(getAccount());
        licenseService.saveLicense(licenseDTO);
        return ResponseVO.success();
    }

    @ApiOperation("修改许可证")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> updateLicense(@Validated @RequestBody LicenseDTO licenseDTO) {
        log.info("updateLicense {}", licenseDTO.toString());
        licenseService.updateLicense(licenseDTO);
        licenseDTO.setUpdatedUser(getAccount());
        return ResponseVO.success();
    }

    @ApiOperation("查询许可证")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<PageVO<LicenseVO>> queryLicense(@Validated LicenseFilterDTO filterDTO) {
        log.info("queryLicense {}", filterDTO.toString());
        ValidationUtil.validate(filterDTO);
        return ResponseVO.success(licenseService.page(filterDTO));
    }

    @ApiOperation("删除许可证")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class, value = "主键ID", required = true),
    })
    public ResponseVO<Void> deleteLicense(@PathVariable("id") @NotNull(message = "主键ID 不能为空") Long id) {
        log.info("deleteLicense {}", id);
        licenseService.delete(id);
        return ResponseVO.success();
    }

    @ApiOperation("批量删除许可证")
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> deleteLicenses(@RequestBody @Validated List<Long> ids) {
        log.info("deleteLicenses {}", ids);
        licenseService.deleteLicense(ids);
        return ResponseVO.success();
    }

    @ApiOperation("生成许可证")
    @PatchMapping(value = "/gen", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> genLicense(@RequestBody @Validated GenLicenseDTO genLicenseDTO) {
        log.info("genLicense {}", genLicenseDTO);
        licenseService.genLicense(genLicenseDTO);
        return ResponseVO.success();
    }

    @ApiOperation("验证是否已生成许可证")
    @GetMapping(value = "/validate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class, value = "主键ID", required = true),
    })
    public ResponseVO<Void> validateGenLicense(@PathVariable("id") @NotNull(message = "主键ID 不能为空") Long id) {
        log.info("validateGenLicense {}", id);
        licenseService.validateGenLicense(id);
        return ResponseVO.success();
    }

    @ApiOperation("下载许可证")
    @GetMapping(value = "/download/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class, value = "主键ID", required = true),
    })
    public ResponseVO<Void> downloadLicense(@PathVariable("id") @NotNull(message = "主键ID 不能为空") Long id,
                                            HttpServletRequest request, HttpServletResponse response) {
        log.info("downloadLicense {}", id);
        licenseService.downloadLicense(id, request, response);
        return ResponseVO.success();
    }





}
