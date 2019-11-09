package com.anan.rbac.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * 配置文件配置类
 */
@Configuration
public class PropertyConfig {

    private final Logger log = LoggerFactory.getLogger(PropertyConfig.class);

    @Bean("propertySourceBean")
    public PropertySource propertySourceBean() {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
        PropertySource propertySource = null;

        try {
            propertySource = yamlLoader.load("application",resolver.getResource("classpath:application.yml"), null);
        } catch (IOException e) {
            log.info("加载 application.yml 配置文件失败");
            e.printStackTrace();
        }

        return propertySource;
    }

}
