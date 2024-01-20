package com.seed.config;


import com.seed.filter.JwtAuthenticationTokenFilter;
import com.seed.handler.config.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 15:20
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;


    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/register", "/captchaImage").permitAll()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
//                .antMatchers("/user/login").anonymous()
//                .antMatchers("/login").anonymous()
                //认证
//                .antMatchers("/logout").authenticated()//认证之后再退出登录
//                .antMatchers("/user/userInfo").authenticated()
//                .antMatchers("/upload").authenticated()
//                .antMatchers("/content/tag/list").authenticated()
//                .antMatchers("/login/getInfo").authenticated()
//                // 除上面外的所有请求全部不需要认证即可访问
//                .anyRequest().permitAll();
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        //配置认证 授权异常处理器
        httpSecurity.exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)//认证
                        .accessDeniedHandler(accessDeniedHandler);          //授权

//        httpSecurity.logout().disable();//关闭security默认的退出登录
        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        httpSecurity.cors();
    }


}
