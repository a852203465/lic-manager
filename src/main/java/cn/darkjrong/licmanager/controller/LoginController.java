package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.pojo.dto.LoginDTO;
import cn.darkjrong.licmanager.common.pojo.dto.RegisterDTO;
import cn.darkjrong.licmanager.common.pojo.vo.ResponseVO;
import cn.darkjrong.licmanager.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Slf4j
@Validated
@RestController
@Api(tags = "登录管理")
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> login(@Validated @RequestBody LoginDTO loginDTO, HttpSession session) {
        log.debug("login {}", loginDTO.toString());
        loginService.login(loginDTO, session);
        return ResponseVO.success();
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> register(@Validated @RequestBody RegisterDTO registerDTO) {
        log.debug("register {}", registerDTO.toString());
        loginService.register(registerDTO);
        return ResponseVO.success();
    }

    @ApiOperation("登出")
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> logout(HttpServletRequest request) {
        loginService.logout(request);
        return ResponseVO.success();
    }

}
