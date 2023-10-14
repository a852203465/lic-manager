package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.license.core.common.pojo.params.LicenseCreatorV2Param;
import cn.darkjrong.license.core.common.utils.FileUtils;
import cn.darkjrong.license.creator.service.FileService;
import cn.darkjrong.license.creator.service.LicenseCreatorService;
import cn.darkjrong.licmanager.common.constants.NumberConstant;
import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.exceptions.LicenseWebException;
import cn.darkjrong.licmanager.common.pojo.dto.GenLicenseDTO;
import cn.darkjrong.licmanager.common.pojo.dto.LicenseDTO;
import cn.darkjrong.licmanager.common.pojo.dto.PageDTO;
import cn.darkjrong.licmanager.common.pojo.entity.License;
import cn.darkjrong.licmanager.common.pojo.query.LicenseQuery;
import cn.darkjrong.licmanager.common.pojo.vo.LicenseVO;
import cn.darkjrong.licmanager.common.pojo.vo.ProjectVO;
import cn.darkjrong.licmanager.mapper.LicenseMapper;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.darkjrong.licmanager.service.LicenseService;
import cn.darkjrong.licmanager.service.ProjectService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * license信息服务实现类
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
@Slf4j
@Service
public class LicenseServiceImpl extends BaseServiceImpl<LicenseMapper, License, License, LicenseVO> implements LicenseService{

    @Autowired
    private LicenseMapper licenseMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LicenseCreatorService licenseCreatorService;

    @Autowired
    private KeystoreService keystoreService;

    @Autowired
    private FileService fileService;

    @Override
    public List<License> queryList(PageDTO pageDTO) {
        LicenseQuery query = new LicenseQuery();
        BeanUtil.copyProperties(pageDTO, query);
        return licenseMapper.findLicense(query);
    }

    @Override
    public LicenseVO queryById(Serializable id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(this.getById(id));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveLicense(LicenseDTO licenseDTO) {

        Assert.notNull(projectService.queryById(licenseDTO.getProjectId()),
                ResponseEnum.THE_PROJECT_DOES_NOT_EXIST.getMessage());

        License license = licenseMapper.findLicenseByProjectIdAndName(licenseDTO.getProjectId(), licenseDTO.getName());
        Assert.isNull(license, ResponseEnum.THE_LICENSE_ALREADY_EXISTS.getMessage());
        license = new License();
        BeanUtil.copyProperties(licenseDTO, license);
        license.setCreatedTime(DateUtil.current());
        this.saveOrUpdate(license);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateLicense(LicenseDTO licenseDTO) {

        Assert.notNull(projectService.queryById(licenseDTO.getProjectId()),
                ResponseEnum.THE_PROJECT_DOES_NOT_EXIST.getMessage());

        License license = this.getById(licenseDTO.getId());
        Assert.notNull(license, ResponseEnum.THE_LICENSE_DOES_NOT_EXIST.getMessage());

        if (!StrUtil.equals(license.getName(), licenseDTO.getName())) {
            Assert.isNull(licenseMapper.findLicenseByProjectIdAndName(licenseDTO.getProjectId(), licenseDTO.getName()),
                    ResponseEnum.THE_LICENSE_ALREADY_EXISTS.getMessage());
        }

        BeanUtil.copyProperties(licenseDTO, license, CopyOptions.create().setIgnoreNullValue(Boolean.TRUE));
        license.setUpdatedTime(DateUtil.current());
        this.saveOrUpdate(license);
    }

    @Override
    public List<LicenseVO> findLicenseByProjectId(Long projectId) {
        Assert.notNull(projectId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(licenseMapper.findLicenseByProjectId(projectId));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteLicense(List<Long> ids) {
        Assert.notEmpty(ids, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        ids.forEach(this::delete);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void genLicense(GenLicenseDTO genLicenseDTO) {
        License license = this.getById(genLicenseDTO.getId());
        Assert.notNull(license, ResponseEnum.THE_LICENSE_DOES_NOT_EXIST.getMessage());

        if (DateUtil.compare(DateUtil.date(genLicenseDTO.getExpiredTime()), DateUtil.date(), DatePattern.NORM_DATE_PATTERN) <= 0) {
            throw new LicenseWebException(ResponseEnum.THE_END_TIME_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_THE_CURRENT_TIME);
        }

        ProjectVO projectVO = projectService.queryById(license.getProjectId());
        byte[] privateKey = keystoreService.findPrivateKeyById(projectVO.getKeystore().getId());

        LicenseCreatorV2Param licenseCreatorV2Param = new LicenseCreatorV2Param();
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreError(Boolean.TRUE);
        BeanUtil.copyProperties(genLicenseDTO, licenseCreatorV2Param, copyOptions);
        licenseCreatorV2Param.setPrivateKeysStore(privateKey);
        licenseCreatorV2Param.setKeyPwd(projectVO.getKeystore().getPrivatePwd());
        licenseCreatorV2Param.setStorePwd(projectVO.getKeystore().getStorePwd());
        licenseCreatorV2Param.setExpiryTime(DateUtil.date(genLicenseDTO.getExpiredTime()));
        licenseCreatorV2Param.setCheckIpAddress(ObjectUtil.equals(NumberConstant.ZERO, genLicenseDTO.getCheckIpAddress()) ? Boolean.FALSE : Boolean.TRUE);
        licenseCreatorV2Param.setCheckMacAddress(ObjectUtil.equals(NumberConstant.ZERO, genLicenseDTO.getCheckMacAddress()) ? Boolean.FALSE : Boolean.TRUE);

        byte[] bytes = licenseCreatorService.generateLicense(licenseCreatorV2Param);

        BeanUtil.copyProperties(genLicenseDTO, license);
        license.setLic(bytes);
        license.setGenTime(DateUtil.current());
        license.setUpdatedTime(DateUtil.current());

        this.updateById(license);
    }

    @Override
    public void downloadLicense(Long id, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        License license = this.getById(id);
        Assert.notNull(license, ResponseEnum.THE_LICENSE_DOES_NOT_EXIST.getMessage());
        fileService.download(license.getLic(), FileUtils.LICENSE_FILE, request, response);
    }

    @Override
    public void validateGenLicense(Long id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        License license = this.getById(id);
        Assert.notNull(license, ResponseEnum.THE_LICENSE_DOES_NOT_EXIST.getMessage());
        Assert.notNull(license.getLic(), ResponseEnum.UNGENERATED_LICENSE.getMessage());
    }

    @Override
    public LicenseVO objectConversion(License license) {
        LicenseVO licenseVO = super.objectConversion(license);
        if (ObjectUtil.isNotNull(licenseVO)) {
            licenseVO.setProject(projectService.queryById(license.getProjectId()));
        }
        return licenseVO;
    }









}
