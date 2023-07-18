package cn.darkjrong.licmanager.controller;

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
@Controller
public class PageController {

    @RequestMapping("demo")
    public String demo() {
        return "demo";
    }






}
