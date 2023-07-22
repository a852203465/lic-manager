package cn.darkjrong.licmanager.mapper;

import cn.darkjrong.licmanager.common.pojo.entity.License;
import cn.darkjrong.licmanager.common.pojo.query.LicenseQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * license信息 Mapper
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
public interface LicenseMapper extends BaseMapper<License> {

    /**
     * 查询许可证通过名字
     *
     * @param name 名字
     * @return {@link License}
     */
    License findLicenseByName(@Param("name") String name);

    /**
     * 查询许可证
     *
     * @param query 查询
     * @return {@link List}<{@link License}>
     */
    List<License> findLicense(LicenseQuery query);




}