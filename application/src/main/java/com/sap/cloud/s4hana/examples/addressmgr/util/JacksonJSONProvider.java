package com.sap.cloud.s4hana.examples.addressmgr.util;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * Make Jackson accept application/json to increase priority over Johnzon
 *
 * See https://stackoverflow.com/questions/38904640/how-to-use-jackson-as-json-provider-for-jax-rs-client-instead-of-johnzon-in-tome
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JacksonJSONProvider extends JacksonJsonProvider {

}
