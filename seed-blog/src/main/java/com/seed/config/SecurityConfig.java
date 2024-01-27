package com.seed.config;

import com.seed.filter.JwtAuthenticationTokenFilter;
import com.seed.service.system.web.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 15:20
 */
@Configuration
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;




    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //1、不通过Session获取SecurityContext
        http
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //2、关闭csrf
        http
                .csrf(AbstractHttpConfigurer::disable);
        //3、配置HTTP请求的授权规则。
        // 配置规则是要求所有未认证的请求都进行认证，
        // 并使用httpBasic方法启用HTTP基本认证。
        http
                .authorizeHttpRequests((authz)->
                        authz.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        //4、安全策略
        // 禁用同源策略中的X-Frame-Options头，
        // 这意味着允许页面在iframe中加载展示，否则浏览器通常会出于安全原因阻止这种加载行为。
        http
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        //5、配置认证 授权异常处理器
        http
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                );
        //6、允许跨域
        http.cors(Customizer.withDefaults());
        //7、关闭security默认的退出登录
        //添加Logout filter
        http
                .logout(LogoutConfigurer::disable);
        //8、把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //用户无需经过登录验证即可直接访问
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs",
                        "/druid/**");
    }
}
