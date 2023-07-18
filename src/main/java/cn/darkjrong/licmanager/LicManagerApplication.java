package cn.darkjrong.licmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.darkjrong.licmanager.mapper")
@SpringBootApplication
public class LicManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicManagerApplication.class, args);
    }

}
