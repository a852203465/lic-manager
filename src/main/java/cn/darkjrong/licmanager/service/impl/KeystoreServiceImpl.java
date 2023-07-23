package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.license.core.common.utils.KeyStoreUtils;
import cn.darkjrong.license.creator.domain.SecretKey;
import cn.darkjrong.license.creator.service.FileService;
import cn.darkjrong.license.creator.service.KeyStoreService;
import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.dto.PageDTO;
import cn.darkjrong.licmanager.common.pojo.entity.Keystore;
import cn.darkjrong.licmanager.common.pojo.query.KeystoreQuery;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.mapper.KeystoreMapper;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.darkjrong.licmanager.service.ProjectService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProjectService projectService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean delete(Serializable id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        Assert.isFalse(CollectionUtil.isNotEmpty(projectService.findProjectByKeystoreId(Convert.toLong(id))),
                ResponseEnum.DATA_QUOTE.getMessage());
        return super.delete(id);
    }

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
        SecretKey secretKey = keyStoreService.genSecretKey(Convert.toLong(keystoreDTO.getValidity()), keystore.getPassword());
        keystore.setPrivateKey(secretKey.getPrivateKey());
        keystore.setPublicKey(secretKey.getPublicKey());
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
        keystore.setUpdatedTime(DateUtil.current());
        this.saveOrUpdate(keystore);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteKeystore(List<Long> ids) {
        Assert.notEmpty(ids, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        ids.forEach(this::delete);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void regenerate(Long id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        Keystore keystore = this.getById(id);
        Assert.notNull(keystore, ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());
        SecretKey secretKey = keyStoreService.genSecretKey(Convert.toLong(keystore.getValidity()), keystore.getPassword());
        keystore.setPrivateKey(secretKey.getPrivateKey());
        keystore.setPublicKey(secretKey.getPublicKey());
        keystore.setUpdatedTime(DateUtil.current());
        this.updateById(keystore);
    }

    @Override
    public void downloadPublic(Long id, Boolean flag, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        Keystore keystore = this.getById(id);
        Assert.notNull(keystore, ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());
        if (flag) {
            fileService.download(keystore.getPrivateKey(), KeyStoreUtils.PRIVATE_KEYS, request, response);
        }else {
            fileService.download(keystore.getPublicKey(), KeyStoreUtils.PUBLIC_CERTS, request, response);
        }
    }

    @Override
    public byte[] findPrivateKeyById(Long id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        Keystore keystore = this.getById(id);
        Assert.notNull(keystore, ResponseEnum.THE_KEY_LIBRARY_DOES_NOT_EXIST.getMessage());
        return keystore.getPrivateKey();
    }


}
