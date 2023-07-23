package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.pojo.vo.HomePageVO;
import cn.darkjrong.licmanager.common.pojo.vo.ResponseVO;
import cn.darkjrong.licmanager.service.HomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */
@Slf4j
@Validated
@RestController
@Api(tags = "首页管理")
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @ApiOperation("统计")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<HomePageVO> homePage() {
        return ResponseVO.success(homePageService.homePage());
    }



}
