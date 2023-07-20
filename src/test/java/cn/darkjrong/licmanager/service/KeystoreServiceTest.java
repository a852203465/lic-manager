package cn.darkjrong.licmanager.service;

import cn.darkjrong.licmanager.LicManagerApplicationTests;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KeystoreServiceTest extends LicManagerApplicationTests {

    @Autowired
    private KeystoreService keystoreService;

    @Test
    void saveKeystore() {
        for (int i = 0; i < 500; i++) {
            KeystoreDTO keystoreDTO = new KeystoreDTO();
            keystoreDTO.setName("测试" + i);
            keystoreDTO.setValidity(1);
            keystoreDTO.setPassword("123456a");
            keystoreService.saveKeystore(keystoreDTO);
        }

    }
}