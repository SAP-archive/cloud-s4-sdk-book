package com.sap.cloud.s4hana.examples.addressmgr.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sap.cloud.sdk.cloudplatform.servlet.response.ErrorResponseBuilder;
import com.sap.cloud.sdk.frameworks.jaxrs.response.JaxRsErrorResponseBuilder;
import com.sap.cloud.sdk.frameworks.jaxrs.response.mapper.NotFoundExceptionMapper;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable>{

    private final ErrorResponseBuilder responseBuilder = ErrorResponseBuilder
            .newBuilder()
            .withMapper(new NotFoundExceptionMapper());

    @Override
    public Response toResponse(final Throwable throwable) {
        return JaxRsErrorResponseBuilder.build(responseBuilder.build(throwable));
    }
}

