package edu.hike.idde.myproject.spring.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.hike.idde.myproject.spring.dao.config.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("!memo")
@Configuration //Tells that the following class contains bean methods
public class DataSourceFactory {

    @Autowired
    private JdbcConfig jdbcConfig;

    @Bean
    DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcConfig.getJdbcUrl());
        hikariConfig.setUsername(jdbcConfig.getJdbcUsername());
        hikariConfig.setPassword(jdbcConfig.getJdbcPassword());
        hikariConfig.setMaximumPoolSize(
                jdbcConfig.getConnectionPoolSize() != null ? jdbcConfig.getConnectionPoolSize() : 10
        );

        return new HikariDataSource(hikariConfig);
    }
}
