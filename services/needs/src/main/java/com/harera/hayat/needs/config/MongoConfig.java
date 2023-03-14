package com.harera.hayat.needs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = { "com.harera.hayat.*" })
public class MongoConfig {
}
