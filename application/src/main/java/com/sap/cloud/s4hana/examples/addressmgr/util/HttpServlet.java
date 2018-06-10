package com.sap.cloud.s4hana.examples.addressmgr.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpServlet extends javax.servlet.http.HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void service( final HttpServletRequest request, final HttpServletResponse response )
        throws ServletException,
            IOException
    {
        if( request.getMethod().equalsIgnoreCase("PATCH") ) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected void doPatch( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
