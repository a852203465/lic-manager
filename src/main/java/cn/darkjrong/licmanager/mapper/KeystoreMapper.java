package cn.darkjrong.licmanager.mapper;

import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.query.KeystoreQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 秘钥库 Mapper 接口
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
public interface KeystoreMapper extends BaseMapper<Keystore> {

    /**
     * 查询密钥存储库根据名字
     *
     * @param name 名字
     * @return {@link Keystore}
     */
    Keystore findKeystoreByName(@Param("name") String name);

    /**
     * 查询密钥存储库
     *
     * @param query 查询
     * @return {@link List}<{@link Keystore}>
     */
    List<Keystore> findKeystore(KeystoreQuery query);



}
