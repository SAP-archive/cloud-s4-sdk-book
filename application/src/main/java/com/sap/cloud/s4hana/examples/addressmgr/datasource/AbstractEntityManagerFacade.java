package com.sap.cloud.s4hana.examples.addressmgr.datasource;

import org.slf4j.Logger;
import org.springframework.cloud.service.common.PostgresqlServiceInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

public abstract class AbstractEntityManagerFacade {

    private static final Logger logger = CloudLoggerFactory.getLogger(AbstractEntityManagerFacade.class);
    public static final String BOOK_PROJECT_DATABASE = "my-postgresql";
    private EntityManagerFactory entityManagerFactory;

    protected AbstractEntityManagerFacade() {
        final PostgresqlServiceInfo postgreInfo = GetDatabaseConfig.retrieveDatabaseInfo(BOOK_PROJECT_DATABASE, PostgresqlServiceInfo.class);
        final Map<String, String> properties = buildProperties(postgreInfo);
        this.entityManagerFactory = Persistence.createEntityManagerFactory("pu", properties);
    }

    protected Map<String, String> buildProperties(final PostgresqlServiceInfo postgreInfo) {
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", postgreInfo.getUserName());
        properties.put("javax.persistence.jdbc.password", postgreInfo.getPassword());
        properties.put("javax.persistence.jdbc.url", postgreInfo.getJdbcUrl());
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
