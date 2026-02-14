package cat.itacademy.s05.t01.n01.blackjack.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql"
)
@Profile("mysql")
public class MySqlConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(
                        "cat.itacademy.s05.t01.n01.blackjack.domain.model.aggregates",
                        "cat.itacademy.s05.t01.n01.blackjack.domain.model.entity",
                        "cat.itacademy.s05.t01.n01.blackjack.infrastructure.persistence.mysql")
                .build();
    }
}
