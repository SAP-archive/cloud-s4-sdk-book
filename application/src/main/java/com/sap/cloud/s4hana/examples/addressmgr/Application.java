package com.sap.cloud.s4hana.examples.addressmgr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.cloud.s4hana.examples"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.cloud.s4hana.examples"})
public class Application extends JpaBaseConfiguration {
    @Autowired
    AutowireCapableBeanFactory beanFactory;

    protected Application(
            DataSource dataSource,
            JpaProperties properties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider,
            ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManagerProvider, transactionManagerCustomizers);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        return Maps.newHashMap(ImmutableMap.of());
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(ObjectMapper objectMapper) {
                super.configure(objectMapper);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            }
        };
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
