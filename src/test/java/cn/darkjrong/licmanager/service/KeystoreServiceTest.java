package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.LicManagerApplicationTests;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class KeystoreServiceTest extends LicManagerApplicationTests {

    @Autowired
    private KeystoreService keystoreService;

    @Test
    void saveKeystore() {
        KeystoreDTO keystoreDTO = new KeystoreDTO();
        keystoreDTO.setName("测试");
        keystoreDTO.setValidity(1);
        keystoreDTO.setPassword("123456a");
        keystoreDTO.setPrivateKey(FileUtil.readBytes("F:\\keystore\\privateKeys.keystore"));
        keystoreDTO.setPublicKey(FileUtil.readBytes("F:\\keystore\\publicCerts.keystore"));

        keystoreService.saveKeystore(keystoreDTO);

    }
}