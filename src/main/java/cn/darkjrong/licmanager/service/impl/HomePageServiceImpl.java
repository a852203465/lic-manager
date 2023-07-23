package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.pojo.vo.HomePageVO;
import cn.darkjrong.licmanager.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主页服务实现类
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Slf4j
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private KeystoreService keystoreService;

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private ProjectService projectService;

    @Override
    public HomePageVO homePage() {

        HomePageVO homePageVO = new HomePageVO();
        homePageVO.setKeystoreTotal(keystoreService.count());
        homePageVO.setProjectTotal(projectService.count());
        homePageVO.setLicenseTotal(licenseService.count());
        homePageVO.setUserInfoTotal(userInfoService.count());

        return homePageVO;
    }
}
