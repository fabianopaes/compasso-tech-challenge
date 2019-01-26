package com.compasso.api.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@Configuration
@EnableMongoRepositories(basePackages="com.compasso.api.domain")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${database.uri:mongodb://localhost:27017/compasso-db})")
    String uri;

    @Override
    protected String getDatabaseName() {
        return "compasso-db";
    }

    @Override
    public MongoClient mongoClient()  {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = null;
        try {
            mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                    .build();
            MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();
            return new MongoClient(bindIp, port);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        return new MongoClient(new MongoClientURI(uri));
    }

}
