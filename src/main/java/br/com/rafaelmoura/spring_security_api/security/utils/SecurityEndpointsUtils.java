package br.com.rafaelmoura.spring_security_api.security.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityEndpointsUtils {
    public final String[] ENDPOINTS_WITHOUT_AUTH = {
            "/api/users",
            "/api/users/login",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/index.html/**",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/h2-console/**"
    };
    public final String[] ENDPOINTS_WITH_CUSTOMER_AUTH = {
            "/api/products/v1/**"
    };
    public final String[] ENDPOINTS_WITH_ADMIN_AUTH = {
            "/api/products/v1/**",
    };
}
