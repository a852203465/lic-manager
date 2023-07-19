package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.LicManagerApplicationTests;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

class KeystoreServiceTest extends LicManagerApplicationTests {

    @Autowired
    private KeystoreService keystoreService;

    @Test
    void saveKeystore() throws IOException {
        KeystoreDTO keystoreDTO = new KeystoreDTO();
        keystoreDTO.setName("测试2");
        keystoreDTO.setValidity(1);
        keystoreDTO.setPassword("123456a");

        keystoreDTO.setPrivateKey(FileUtils.file2MultipartFile(new File("F:\\keystore\\privateKeys.keystore")));
        keystoreDTO.setPublicKey(FileUtils.file2MultipartFile(new File("F:\\keystore\\publicCerts.keystore")));

        keystoreService.saveKeystore(keystoreDTO);

    }
}