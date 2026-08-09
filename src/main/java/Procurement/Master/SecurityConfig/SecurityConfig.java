package Procurement.Master.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import Procurement.Master.Security.JwtFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/",
                        "/index.html",
                        "/login.html",
                        "/register.html",
                        "/login.css",
                        "/login.js",
                        "/register.js",
                        "/favicon.ico",
                        "/api/auth/**"
                ).permitAll()

                // Authentication APIs
                .requestMatchers(
                        "/api/auth/login",
                        "/api/auth/register",
                        "/employee-dashboard.html",
                        "/employee-dashboard.css",
                        "/employee-dashboard.js",
                        "/employee-purchase-requisition.html",
                        "/employee-purchase-requisition.css",
                        "/employee-purchase-requisition.js",
                        "/manager-dashboard.css",
                        "/manager-dashboard.html",
                        "/manager-dashboard.js",
                        "/my-requests.html",
                        "/my-requests.js",
                        "/procurement.js",
                        "/procurement.html",
                        "/procurement.css",
                        "/register.html",
                        "/register.js",
                        "/api/employees",
                        "/api/requisitions",
                        "/workflow.html",
                        "/workflow.js",
                        "/api/workflow",
                        "/api/procurement/pending",
                        "/approval-history.html",
                        "/approval-history.css",
                        "/approval-history.js",
                        "/api/approval-history/all",
                        "/api/procurement/approve/",
                        "/api/procurement/**",
                        "/api/suppliers/**",
                        "/api/purchase-orders/**",
                        "/api/suppliers/purchase-orders/",
                        "/api/purchase-orders/**",
                        "/api/auth/**",
                        "/api/suppliers/**",
                        "/supplier.html",
                        "/supplier.js",
                        "/supplier.css",
                        "/api/purchase-orders/supplier/",
                       "/api/deliveries/**",
                       "/delivery.html",
                       "/delivery.css",
                       "/delivery.js",
                       "/my-history.html",
                       "/my-history.css",
                       "/my-history.js",
                        "/api/approval-history/**",
                        "/api/approval-history/employee/**",
                        "/supplier-registration.html",
                        "/supplier-registration.js",
                        "/procurement-delivery.html",
                        "/procurement-delivery.js",
                        "/procurement.css",
                        "/api/deliveries",
                        "/api/procurement/**",
                        "/api/suppliers/**",
                        "/api/purchase-orders/**",
                        "/api/deliveries/**",
                  "/"
                ).permitAll()
//                .requestMatchers("/api/employees/**").authenticated()
//                .requestMatchers("/api/requisitions/**").authenticated()
//                .requestMatchers("/api/workflow/**").authenticated()
//                .requestMatchers("/api/manager/**").authenticated()
//                .requestMatchers("/api/approval-history/**").authenticated()

                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class)

            .formLogin(form -> form.disable())

            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}