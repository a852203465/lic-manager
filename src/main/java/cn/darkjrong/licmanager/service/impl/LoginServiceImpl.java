package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.pojo.dto.LoginDTO;
import cn.darkjrong.licmanager.common.pojo.dto.RegisterDTO;
import cn.darkjrong.licmanager.common.pojo.dto.UserInfoDTO;
import cn.darkjrong.licmanager.common.pojo.vo.UserInfoVO;
import cn.darkjrong.licmanager.service.LoginService;
import cn.darkjrong.licmanager.service.UserInfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录服务实现类
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void login(LoginDTO loginDTO, HttpSession session) {
        Assert.notBlank(loginDTO.getAccount(), ResponseEnum.THE_ACCOUNT_CANNOT_BE_EMPTY.getMessage());
        UserInfoVO userInfoVO = userInfoService.findUserInfoByAccount(loginDTO.getAccount());
        Assert.notNull(userInfoVO, ResponseEnum.THE_ACCOUNT_DOES_NOT_EXIST_PLEASE_CHANGE_THE_ACCOUNT_TO_LOGIN.getMessage());

        Assert.isTrue(DigestUtil.bcryptCheck(loginDTO.getPassword(), userInfoVO.getPassword()),
                ResponseEnum.THE_ACCOUNT_OR_PASSWORD_IS_INCORRECT.getMessage());

        session.setAttribute("uid",userInfoVO.getId());
        session.setAttribute("account",userInfoVO.getAccount());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void register(RegisterDTO registerDTO) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtil.copyProperties(registerDTO, userInfoDTO);
        userInfoDTO.setCreatedUser(registerDTO.getAccount());
        userInfoService.saveUserInfo(userInfoDTO);
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("uid");
        request.getSession().removeAttribute("account");
    }
}
