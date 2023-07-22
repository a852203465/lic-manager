package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.service.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 删除密钥库
     *
     * @param ids id
     */
    void deleteKeystore(List<Long> ids);

    /**
     * 重新生成
     *
     * @param id id
     */
    void regenerate(Long id);

    /**
     * 下载公钥
     *
     * @param id id
     * @param flag 标识, true:私钥,false:公钥
     * @param response 响应对象
     * @param request 请求对象
     */
    void downloadPublic(Long id, Boolean flag, HttpServletRequest request, HttpServletResponse response);










}
