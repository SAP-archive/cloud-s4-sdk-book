package com.sap.cloud.s4hana.examples.addressmgr.config;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.cloud.service.common.PostgresqlServiceInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sap.cloud.s4hana.examples.addressmgr.datasource.GetDatabaseConfig;
import com.sap.cloud.s4hana.examples.addressmgr.util.TenantUtil;
import static com.sap.cloud.s4hana.examples.addressmgr.util.TenantUtil.DEFAULT_TENANT;

public class SchemaPerTenantConnectionProvider implements MultiTenantConnectionProvider {

    @Override
    public Connection getAnyConnection() throws SQLException {
        final PostgresqlServiceInfo postgreInfo = GetDatabaseConfig.retrieveDatabaseInfo("my-postgresql", PostgresqlServiceInfo.class);
        Map<String, String> properties = new HashMap<String, String>();

        properties.put(Environment.USER, postgreInfo.getUserName());
        properties.put(Environment.PASS, postgreInfo.getPassword());
        properties.put(Environment.URL, postgreInfo.getJdbcUrl());

        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure(properties);

        return connectionProvider.getConnection();
    }

    @Override
    public void releaseAnyConnection(final Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(final String tenantIdentifier) throws SQLException {
        final Connection connection = this.getAnyConnection();

        try (PreparedStatement statement = connection.prepareStatement(String.format("SET SCHEMA '%s'", TenantUtil.createSchemaName(tenantIdentifier)))) {
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" + TenantUtil.createSchemaName(tenantIdentifier) + "]",
                    e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(final String tenantIdentifier, final Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(String.format("SET SCHEMA '%s'", TenantUtil.createSchemaName(DEFAULT_TENANT)))) {
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" + TenantUtil.createSchemaName(DEFAULT_TENANT) + "]", e);
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(final Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(final Class<T> aClass) {
        return null;
    }
}
