package com.sap.cloud.s4hana.examples.addressmgr.util;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import org.apache.cxf.jaxrs.ext.ResourceComparator;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A CustomResourceComparator that implements an algorithm for finding service classes that also works if multiple
 * API interfaces have the same base path. The algorithm dives into the actual API interface to check if it indeed
 * contains an operation that matches the full request path.
 */
public class CustomResourceComparator implements ResourceComparator {
    private static final Logger logger = CloudLoggerFactory.getLogger(CustomResourceComparator.class);
    private static final String HTTP_METHOD_PATCH = "PATCH";

    @Override
    public int compare(ClassResourceInfo cri1, ClassResourceInfo cri2, Message message) {
        String requestMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);
        String requestUri = (String) message.get(Message.REQUEST_URI);
        // base path may be "/", only replace first occurrence
        String requestPath = requestUri.replaceFirst((String) message.get(Message.BASE_PATH), "");
        logger.debug("Comparing {} and {} for request {} {}",
                cri1.getServiceClass().getName(),
                cri2.getServiceClass().getName(),
                requestMethod, requestPath);

        // remove trailing "/", add initial "/" (e.g. removed before)
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }
        if (!requestPath.startsWith("/")) {
            requestPath = "/" + requestPath;
        }

        if (hasMatchingOperation(cri1, requestPath, requestMethod)) {
            logger.debug("Comparator prefers {}", cri1.getServiceClass().getName());
            return -1; // Prefer cri1
        } else if (hasMatchingOperation(cri2, requestPath, requestMethod)) {
            logger.debug("Comparator prefers {}", cri2.getServiceClass().getName());
            return 1; // Prefer cri2
        } else {
            logger.debug("Comparator found no match");
            return 0; // No match, both equally not suited, let CXF continue to make a decision
        }
    }

    /**
     * Checks each method (operation) provided by the service class' interface if it is a match for the request with
     * the given
     * path and HTTP method.
     *
     * @param cri         the class info whose service class will be analysed.
     * @param requestPath the path of the request, starting with '/', without host or base path.
     * @return true if the service class of cri has a matching method, false otherwise.
     */
    private static boolean hasMatchingOperation(ClassResourceInfo cri, String requestPath, String requestMethod) {
        if (cri.getServiceClass() == null ||
                cri.getServiceClass().getInterfaces() == null ||
                cri.getServiceClass().getInterfaces().length == 0) {
            logger.warn("{} does not implement an API interface.");
            return false;
        }

        final Class<?> apiInterface = cri.getServiceClass().getInterfaces()[0];

        // Determine root path on class level, if any
        final Path rootPathAnnotation = apiInterface.getAnnotation(javax.ws.rs.Path.class);
        final String rootPath = rootPathAnnotation != null ? rootPathAnnotation.value() : "";

        for (final Method method : apiInterface.getMethods()) {
            final Path pathAnnotation = method.getAnnotation(javax.ws.rs.Path.class);
            if (pathAnnotation != null) {
                final String methodPath = rootPath + pathAnnotation.value();

                // OPTIONS request match if the path matches any method declaration
                if (requestMethod.equals(HttpMethod.OPTIONS) && pathMatches(methodPath, requestPath)) {
                    return true;
                }

                // For non-OPTIONS request, method and path need to match
                final String methodHttpMethod = getHttpMethod(method);
                if (requestMethod.equals(methodHttpMethod) && pathMatches(methodPath, requestPath)) {
                    return true;
                }

                // otherwise continue
            }
        }
        return false;
    }

    /**
     * Finds the HTTP method present on the given Java method, and converts it to a String.
     *
     * @param method Java method to analyse.
     * @return The HTTP method represented as a String, or "" if no HTTP method was found on the Java method.
     */
    private static String getHttpMethod(Method method) {
        if (method.getAnnotation(javax.ws.rs.POST.class) != null) {
            return HttpMethod.POST;
        } else if (method.getAnnotation(javax.ws.rs.GET.class) != null) {
            return HttpMethod.GET;
        } else if (method.getAnnotation(io.swagger.jaxrs.PATCH.class) != null) {
            return HTTP_METHOD_PATCH;
        } else if (method.getAnnotation(javax.ws.rs.PUT.class) != null) {
            return HttpMethod.PUT;
        } else if (method.getAnnotation(javax.ws.rs.OPTIONS.class) != null) {
            return HttpMethod.OPTIONS;
        } else if (method.getAnnotation(javax.ws.rs.DELETE.class) != null) {
            return HttpMethod.DELETE;
        } else if (method.getAnnotation(javax.ws.rs.HEAD.class) != null) {
            return HttpMethod.HEAD;
        }
        return "";
    }

    /**
     * Check whether the request path matches the path from the JAX-RS annotation.
     * Considers path parameters declared as '{param}' and accepts any value in the request path for those.
     *
     * @param pathFromAnnotation Path specified in annotation.
     * @param pathFromRequest    Path from request.
     * @return true if there is a match, false otherwise
     */
    private static boolean pathMatches(String pathFromAnnotation, String pathFromRequest) {
        // Accept any value for {param} segements
        final String annotationPathPattern = pathFromAnnotation.replaceAll("\\{(.*?)\\}", "([^\\/]*)");
        final Matcher matcher = Pattern.compile(annotationPathPattern).matcher(pathFromRequest);

        return matcher.matches();
    }

    @Override
    public int compare(OperationResourceInfo arg0, OperationResourceInfo arg1, Message arg2) {
        return 0; // No customization for operation resources
    }

}