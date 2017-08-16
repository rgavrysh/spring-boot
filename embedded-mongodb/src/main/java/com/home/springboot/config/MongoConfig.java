package com.home.springboot.config;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
public class MongoConfig {

    private static final String MONGO_DB_NAME = "embedded_db";
    private static final String MONGO_DB_URL = "localhost";

    @Bean
    MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean embeddedMongoFactoryBean = new EmbeddedMongoFactoryBean();
        embeddedMongoFactoryBean.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = embeddedMongoFactoryBean.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return mongoTemplate;
    }
}
