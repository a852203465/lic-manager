package cn.darkjrong.licmanager.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器
 *
 * @author Rong.Jia
 * @date 2023/07/18
 */
@Slf4j
@Api(hidden = true)
@Controller
public class PageController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("page-keystore")
    public String keystore() {
        return "keystore";
    }

    @RequestMapping("page-project")
    public String project() {
        return "project";
    }

    @RequestMapping("page-license")
    public String license() {
        return "license";
    }

}
