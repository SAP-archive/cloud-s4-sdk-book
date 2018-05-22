package com.sap.cloud.s4hana.examples.addressmgr.config;

import com.sap.xs2.security.commons.SAPOfflineTokenServicesCloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@Profile("cloud")
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().authorizeRequests()
                .antMatchers("/**").authenticated();
    }

    @Bean
    protected SAPOfflineTokenServicesCloud offlineTokenServices() {
        return new SAPOfflineTokenServicesCloud();
    }
}
