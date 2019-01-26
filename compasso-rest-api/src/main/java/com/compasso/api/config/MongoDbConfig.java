package com.compasso.api.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages="com.compasso.api.domain")
@EnableMongoAuditing
class MongoDbConfig extends AbstractMongoConfiguration {

    @Value("${database.uri:mongodb://localhost:27017/compasso-db})")
    String uri;

    @Override
    protected String getDatabaseName() {
        return "compasso-db";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(uri));
    }
}
