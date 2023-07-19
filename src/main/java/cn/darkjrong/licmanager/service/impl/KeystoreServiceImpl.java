package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.mapper.KeystoreMapper;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 秘钥库 服务实现类
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@Slf4j
@Service
public class KeystoreServiceImpl extends BaseServiceImpl<KeystoreMapper, Keystore, Keystore, KeystoreVO> implements KeystoreService {

    @Autowired
    private KeystoreMapper keystoreMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveKeystore(KeystoreDTO keystoreDTO) {
        Keystore keystore = keystoreMapper.findKeystoreByName(keystoreDTO.getName());
        Assert.isNull(keystore, ResponseEnum.THE_KEY_LIBRARY_ALREADY_EXISTS.getMessage());
        keystore = new Keystore();
        BeanUtil.copyProperties(keystoreDTO, keystore);
        keystore.setCreatedTime(DateUtil.current());
        this.saveOrUpdate(keystore);
    }
}
