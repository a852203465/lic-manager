package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.utils.AuthUtils;

/**
 * 基本控制器
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
public abstract class BaseController {

    protected String getAccount(){
        return AuthUtils.getCurrentUser();
    }







}
