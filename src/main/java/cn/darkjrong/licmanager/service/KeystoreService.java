package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.service.base.BaseService;

/**
 * <p>
 * 秘钥库 服务类
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
public interface KeystoreService extends BaseService<Keystore, Keystore, KeystoreVO> {

    /**
     * 保存密钥存储库
     *
     * @param keystoreDTO 密钥存储库DTO
     */
    void saveKeystore(KeystoreDTO keystoreDTO);

    /**
     * 修改密钥存储库
     *
     * @param keystoreDTO 密钥存储库DTO
     */
    void updateKeystore(KeystoreDTO keystoreDTO);


}
