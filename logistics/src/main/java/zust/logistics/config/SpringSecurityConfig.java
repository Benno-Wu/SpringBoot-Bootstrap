package zust.logistics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义拦截路径，优先级从高到低
        http.authorizeRequests()
//                //swagger放行
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers(
//                        "/v2/api-docs",
//                        "/swagger-resources",
//                        "/swagger-resources/**",
//                        "/configuration/ui",
//                        "/configuration/security",
//                        "/swagger-ui.html/**",
//                        "/webjars/**"
//
//                ).permitAll()
                //放行所有
//                .antMatchers("/").permitAll();
                .anyRequest().permitAll();
    }
}