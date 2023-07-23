package cn.darkjrong.licmanager.common.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * web配置类
 *
 * @author Rong.Jia
 * @date 2019/08/14 11:45
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<>();
        patterns.add("*.js");
        patterns.add("*.css");
        patterns.add("/fonts/**");
        patterns.add("/css/**");
        patterns.add("/js/**");
        patterns.add("/favicon.ico");
        patterns.add("/plugins/**");
        patterns.add("/images/**");
        patterns.add("/login");
        patterns.add("/register");
        patterns.add("/auth/**");
        patterns.add("/error");
        patterns.add("/swagger/**");
        patterns.add("/swagger-ui.html");
        patterns.add("/doc.html");
        patterns.add("/webjars/**");
        patterns.add("/v2/**");
        patterns.add("/**/v2/api-docs");
        patterns.add("/swagger-resources/**");

        registry.addInterceptor(interceptor).excludePathPatterns(patterns).addPathPatterns("/**");
    }


}
