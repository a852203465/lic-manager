package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.exceptions.LicenseWebException;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.dto.PageDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.query.KeystoreQuery;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.mapper.KeystoreMapper;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

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
    public List<Keystore> queryList(PageDTO pageDTO) {
        KeystoreQuery query = new KeystoreQuery();
        BeanUtil.copyProperties(pageDTO, query);
        return keystoreMapper.findKeystore(query);
    }

    @Override
    public KeystoreVO queryById(Serializable id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(this.getById(id));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveKeystore(KeystoreDTO keystoreDTO) {
        Keystore keystore = keystoreMapper.findKeystoreByName(keystoreDTO.getName());
        Assert.isNull(keystore, ResponseEnum.THE_KEY_LIBRARY_ALREADY_EXISTS.getMessage());
        keystore = new Keystore();

        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(Boolean.TRUE);
        copyOptions.setIgnoreError(Boolean.TRUE);

        BeanUtil.copyProperties(keystoreDTO, keystore, copyOptions);
        keystore.setPrivateKey(getBytes(keystoreDTO.getPrivateKey()));
        keystore.setPublicKey(getBytes(keystoreDTO.getPublicKey()));
        keystore.setCreatedTime(DateUtil.current());
        this.saveOrUpdate(keystore);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateKeystore(KeystoreDTO keystoreDTO) {
        Keystore keystore = this.getById(keystoreDTO.getId());
        Assert.notNull(keystore, ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());

        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(Boolean.TRUE);
        copyOptions.setIgnoreError(Boolean.TRUE);

        BeanUtil.copyProperties(keystoreDTO, keystore, copyOptions);
        keystore.setPrivateKey(getBytes(keystoreDTO.getPrivateKey()));
        keystore.setPublicKey(getBytes(keystoreDTO.getPublicKey()));
        keystore.setUpdatedTime(DateUtil.current());
        this.saveOrUpdate(keystore);
    }

    private byte[] getBytes(MultipartFile file) {
        Assert.isFalse(ObjectUtil.isNull(file) || file.isEmpty(),
                ResponseEnum.FILE_DOES_NOT_EXIST.getMessage());
        try {
            return file.getBytes();
        }catch (Exception e) {
            log.error("getBytes {}", e.getMessage());
            throw new LicenseWebException(ResponseEnum.FILE_UPLOAD_EXCEPTION_RETRY);
        }
    }




}
