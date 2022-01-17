package chrome.extension.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.MongoConfigurationSupport
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ['chrome.extension.backend.repositories'])
class MongoConfig extends MongoConfigurationSupport {

    @Override
    protected String getDatabaseName() {
        return '{{cookiecutter.DATABASE_NAME}}'
    }

    @Override
    protected boolean autoIndexCreation() {
        return true
    }
}
