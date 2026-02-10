package cat.itacademy.s05.t01.n01.blackjack.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(
        basePackages = "cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mongodb"
)
public class MongoReactiveConfig extends AbstractReactiveMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "blackjack";
    }
}
