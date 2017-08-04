package com.home.springboot.config;

import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory servletContainerFactory = new TomcatEmbeddedServletContainerFactory();
        servletContainerFactory.setDisplayName("CustomTomcat");
        servletContainerFactory.setPort(8081);
        servletContainerFactory.setSessionTimeout(3, TimeUnit.MINUTES);
        return servletContainerFactory;
    }
}
