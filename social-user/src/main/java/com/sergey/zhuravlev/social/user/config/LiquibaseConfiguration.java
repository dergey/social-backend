package com.sergey.zhuravlev.social.user.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.function.Supplier;

@Configuration
public class LiquibaseConfiguration {

    @Bean
    public SpringLiquibase liquibase(LiquibaseProperties liquibaseProperties,
                                     R2dbcProperties dataSourceProperties) {
        SpringLiquibase liquibase = createSpringLiquibase(liquibaseProperties, dataSourceProperties);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        return liquibase;
    }

    public static SpringLiquibase createSpringLiquibase(LiquibaseProperties liquibaseProperties,
                                                        R2dbcProperties dataSourceProperties) {
        SpringLiquibase liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setDataSource(createNewDataSource(liquibaseProperties, dataSourceProperties));
        return liquibase;
    }

    private static DataSource createNewDataSource(LiquibaseProperties liquibaseProperties, R2dbcProperties dataSourceProperties) {
        String user = getProperty(liquibaseProperties::getUser, dataSourceProperties::getUsername);
        String password = getProperty(liquibaseProperties::getPassword, dataSourceProperties::getPassword);
        return DataSourceBuilder.create().url(liquibaseProperties.getUrl()).username(user).password(password).build();
    }

    private static String getProperty(Supplier<String> property, Supplier<String> defaultValue) {
        String value = property.get();
        return (value != null) ? value : defaultValue.get();
    }
}
