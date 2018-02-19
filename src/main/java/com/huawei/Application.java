package com.huawei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author apple
 * @date 2018年2月19日-下午3:19:22
 * @description 应用程序入口
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@SpringBootApplication
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
