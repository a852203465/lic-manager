package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.common.pojo.dto.GenLicenseDTO;
import cn.darkjrong.licmanager.common.pojo.dto.LicenseDTO;
import cn.darkjrong.licmanager.common.pojo.entity.License;
import cn.darkjrong.licmanager.common.pojo.vo.LicenseVO;
import cn.darkjrong.licmanager.service.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * license信息 service 接口
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
public interface LicenseService extends BaseService<License, License, LicenseVO> {

    /**
     * 保存许可证
     *
     * @param licenseDTO 许可证DTO
     */
    void saveLicense(LicenseDTO licenseDTO);

    /**
     * 更新许可证
     *
     * @param licenseDTO 许可证DTO
     */
    void updateLicense(LicenseDTO licenseDTO);

    /**
     * 查询许可证通过项目ID
     *
     * @param projectId 项目ID
     * @return {@link List}<{@link LicenseVO}>
     */
    List<LicenseVO> findLicenseByProjectId(Long projectId);

    /**
     * 删除许可证
     *
     * @param ids id
     */
    void deleteLicense(List<Long> ids);

    /**
     * 生成许可证
     *
     * @param genLicenseDTO 生成许可证DTO
     */
    void genLicense(GenLicenseDTO genLicenseDTO);

    /**
     * 下载许可证
     *
     * @param id id
     * @param request 请求对象
     * @param response 响应对象
     */
    void downloadLicense(Long id, HttpServletRequest request, HttpServletResponse response);

    /**
     * 验证是否已生成许可证
     *
     * @param id id
     */
    void validateGenLicense(Long id);


}
