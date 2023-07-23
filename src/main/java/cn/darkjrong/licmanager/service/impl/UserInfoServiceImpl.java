package cn.darkjrong.licmanager.service.impl;

import cn.darkjrong.licmanager.common.constants.AuthConstant;
import cn.darkjrong.licmanager.common.constants.NumberConstant;
import cn.darkjrong.licmanager.common.constants.RegularVerifyConstant;
import cn.darkjrong.licmanager.common.enums.ResponseEnum;
import cn.darkjrong.licmanager.common.exceptions.LicenseWebException;
import cn.darkjrong.licmanager.common.pojo.dto.PageDTO;
import cn.darkjrong.licmanager.common.pojo.dto.PwdDTO;
import cn.darkjrong.licmanager.common.pojo.dto.UserInfoDTO;
import cn.darkjrong.licmanager.common.pojo.entity.UserInfo;
import cn.darkjrong.licmanager.common.pojo.query.UserInfoQuery;
import cn.darkjrong.licmanager.common.pojo.vo.UserInfoVO;
import cn.darkjrong.licmanager.mapper.UserInfoMapper;
import cn.darkjrong.licmanager.service.UserInfoService;
import cn.darkjrong.licmanager.service.base.impl.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息服务实现类
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfo, UserInfoVO> implements UserInfoService{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> queryList(PageDTO pageDTO) {
        UserInfoQuery query = new UserInfoQuery();
        BeanUtil.copyProperties(pageDTO, query);
        return userInfoMapper.findUserInfo(query);
    }

    @Override
    public UserInfoVO queryById(Serializable id) {
        Assert.notNull(id, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        return this.objectConversion(this.getById(id));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveUserInfo(UserInfoDTO userInfoDTO) {
        Assert.isTrue(ReUtil.isMatch(RegularVerifyConstant.PWD_REG, userInfoDTO.getPassword()),
                ResponseEnum.THE_PASSWORD_FORMAT_IS_INCORRECT.getMessage());

        UserInfo userInfo = userInfoMapper.findUserInfoByAccount(userInfoDTO.getAccount());
        Assert.isNull(userInfo, ResponseEnum.THE_USER_ALREADY_EXISTS.getMessage());
        userInfo = new UserInfo();

        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(Boolean.TRUE);
        copyOptions.setIgnoreError(Boolean.TRUE);

        BeanUtil.copyProperties(userInfoDTO, userInfo, copyOptions);
        userInfo.setCreatedTime(DateUtil.current());
        userInfo.setPassword(DigestUtil.bcrypt(userInfoDTO.getPassword()));
        userInfo.setStatus(NumberConstant.ONE);
        this.saveOrUpdate(userInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUserInfo(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = this.getById(userInfoDTO.getId());
        Assert.notNull(userInfo, ResponseEnum.THE_USER_DOES_NOT_EXIST.getMessage());

        if (!StrUtil.equals(userInfo.getAccount(), userInfoDTO.getAccount())) {
            Assert.isNull(userInfoMapper.findUserInfoByAccount(userInfoDTO.getAccount()),
                    ResponseEnum.THE_USER_ALREADY_EXISTS.getMessage());
        }

        BeanUtil.copyProperties(userInfoDTO, userInfo);
        userInfo.setUpdatedTime(DateUtil.current());
        this.saveOrUpdate(userInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteUserInfo(List<Long> ids) {
        Assert.notEmpty(ids, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());
        ids.forEach(this::delete);
    }

    @Override
    public void verifyPwd(PwdDTO pwdDTO) {

        Assert.isTrue(ReUtil.isMatch(RegularVerifyConstant.PWD_REG, pwdDTO.getNewPwd()),
                ResponseEnum.THE_PASSWORD_FORMAT_IS_INCORRECT.getMessage());

        UserInfo userInfo = userInfoMapper.findUserInfoByAccount(pwdDTO.getAccount());
        Assert.notNull(userInfo, ResponseEnum.THE_USER_DOES_NOT_EXIST.getMessage());

        // 校验原密码
        Assert.isTrue(DigestUtil.bcryptCheck(pwdDTO.getOldPwd(), userInfo.getPassword()),
                ResponseEnum.THE_OLD_PASSWORD_IS_INCORRECT.getMessage());

        // 校验新密码与旧密码是否相同
        Assert.isFalse(DigestUtil.bcryptCheck(pwdDTO.getNewPwd(), userInfo.getPassword()),
                ResponseEnum.THE_NEW_PASSWORD_IS_THE_SAME_AS_THE_OLD_PASSWORD.getMessage());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void modifyPwd(PwdDTO pwdDTO) {
        verifyPwd(pwdDTO);
        UserInfo userInfo = userInfoMapper.findUserInfoByAccount(pwdDTO.getAccount());
        userInfoMapper.updatePasswordById(userInfo.getId(), DigestUtil.bcrypt(pwdDTO.getNewPwd()));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void disableUserInfo(String account) {
        Assert.notBlank(account, ResponseEnum.THE_ACCOUNT_CANNOT_BE_EMPTY.getMessage());

        UserInfo userInfo = userInfoMapper.findUserInfoByAccount(account);
        Assert.notNull(userInfo, ResponseEnum.THE_USER_DOES_NOT_EXIST.getMessage());

        // 判断是否管理员
        Assert.isFalse(StrUtil.equals(AuthConstant.ADMINISTRATOR, account), ResponseEnum.SYSTEM_ADMINISTRATOR_CANNOT_DISABLE.getMessage());

        // 判断禁用用户是否是当前登录用户
//        Assert.isFalse(StrUtil.equals(AuthUtils.getCurrentUser(), userInfo.getAccount()), ResponseEnum.CURRENT_USER_CANNOT_DISABLE.getMessage());

        if (Validator.equal(NumberConstant.A_NEGATIVE, userInfo.getStatus())) {
            userInfoMapper.updateStatusById(userInfo.getId(), NumberConstant.ONE);
        } else if (Validator.equal(NumberConstant.ONE, userInfo.getStatus())) {
            userInfoMapper.updateStatusById(userInfo.getId(), NumberConstant.A_NEGATIVE);
        } else {
            log.error("disableUserInfo() Invalid specified state");
            throw new LicenseWebException(ResponseEnum.INVALID_SPECIFIED_STATE);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String resetPwd(String account) {
        Assert.notBlank(account, ResponseEnum.THE_ACCOUNT_CANNOT_BE_EMPTY.getMessage());
        UserInfo userInfo = userInfoMapper.findUserInfoByAccount(account);

        Assert.notNull(userInfo, ResponseEnum.THE_USER_DOES_NOT_EXIST.getMessage());
        userInfoMapper.updatePasswordById(userInfo.getId(), DigestUtil.bcrypt(AuthConstant.DEFAULT_PASSWORD));
        return AuthConstant.DEFAULT_PASSWORD;
    }













}
