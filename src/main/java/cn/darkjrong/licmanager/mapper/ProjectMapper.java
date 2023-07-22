package cn.darkjrong.licmanager.mapper;

import cn.darkjrong.licmanager.common.pojo.entity.Project;
import cn.darkjrong.licmanager.common.pojo.query.ProjectQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息 Mapper
 *
 * @author Rong.Jia
 * @date 2023/07/22
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查询项目通过名字
     *
     * @param name 名字
     * @param keystoreId 秘钥库ID
     * @return {@link Project}
     */
    Project findProjectByKeystoreIdAndName(@Param("keystoreId") Long keystoreId, @Param("name") String name);

    /**
     * 查询项目
     *
     * @param query 查询
     * @return {@link List}<{@link Project}>
     */
    List<Project> findProject(ProjectQuery query);

    /**
     * 查询项目通过密钥存储库id
     *
     * @param keystoreId 密钥存储库id
     * @return {@link List}<{@link Project}>
     */
    List<Project> findProjectByKeystoreId(@Param("keystoreId") Long keystoreId);






}