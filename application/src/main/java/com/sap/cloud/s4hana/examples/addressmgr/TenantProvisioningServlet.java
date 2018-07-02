package com.sap.cloud.s4hana.examples.addressmgr;

import liquibase.exception.LiquibaseException;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.sap.cloud.s4hana.examples.addressmgr.datasource.GenericEntityManagerFacade;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.liquibase.SchemaPerTenantProvisioner;
import static com.sap.cloud.s4hana.examples.addressmgr.util.TenantUtil.SCHEMA_PREFIX;

@WebServlet("/api/callback/tenant/*")
public class TenantProvisioningServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(TenantProvisioningServlet.class);
    public static final int TENANT_PARAMETER_INDEX = 1;
    private static final String LIQUIBASE_CONFIG =  "db.changelog/db.changelog-master.yaml";

    @Override
    protected void doPut(final HttpServletRequest request, final HttpServletResponse response ) throws IOException {
        final String tenantId = retrieveTenantId(request);

        try {
            final EntityManager entityManager = GenericEntityManagerFacade.getInstance().getEntityManager();
            final Connection connection = ((SessionImpl) entityManager.unwrap(Session.class)).connection();
            final SchemaPerTenantProvisioner schemaProvisioner = new SchemaPerTenantProvisioner(connection, LIQUIBASE_CONFIG, SCHEMA_PREFIX);
            schemaProvisioner.subscribeTenant(tenantId);
            connection.close();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.setContentType("application/json");
        } catch (SQLException | LiquibaseException e) {
            logger.error("Tenant subscription failed for {}.", tenantId, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
        }
    }

    @Override
    protected void doDelete( final HttpServletRequest request, final HttpServletResponse response )
    {
        final String tenantId = retrieveTenantId(request);
        try {
            final EntityManager entityManager = GenericEntityManagerFacade.getInstance().getEntityManager();
            final Connection connection = ((SessionImpl) entityManager.unwrap(Session.class)).connection();
            final SchemaPerTenantProvisioner schemaProvisioner = new SchemaPerTenantProvisioner(connection, LIQUIBASE_CONFIG, SCHEMA_PREFIX);
            schemaProvisioner.unsubscribeTenant(tenantId);
            connection.close();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.setContentType("application/json");
        } catch (SQLException e) {
            logger.error("Tenant unsubscription failed for {}.", tenantId, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
        }
    }

    private String retrieveTenantId(final HttpServletRequest request) {
        System.out.println(request.getPathInfo());
        final String pathInfo = request.getPathInfo();
        return pathInfo.split("/")[TENANT_PARAMETER_INDEX];
    }
}
