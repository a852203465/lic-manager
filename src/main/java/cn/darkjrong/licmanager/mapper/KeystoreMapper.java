package cn.darkjrong.licmanager.mapper;

import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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




}
