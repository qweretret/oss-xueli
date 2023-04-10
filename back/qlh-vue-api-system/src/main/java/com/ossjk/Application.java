package com.ossjk;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import cn.hutool.core.swing.DesktopUtil;

@SpringBootApplication()
@PropertySource({ "classpath:druid.properties" })
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
