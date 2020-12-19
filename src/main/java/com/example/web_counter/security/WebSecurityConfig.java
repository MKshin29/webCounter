package com.example.web_counter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("111")).roles("User").authorities("Savevisit")
                .and()
                .withUser("admin").password(passwordEncoder().encode("222")).roles("User", "Admin").authorities("Savevisit", "Stats");


//            .jdbcAuthentication().dataSource(dataSource)
//            .usersByUsernameQuery("select username, password from users where username=?")
//            .authoritiesByUsernameQuery("select username, role from users where username=?")
//            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/test").permitAll()
                .antMatchers("/stats").hasAuthority("Stats")
                .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .csrf().disable();
}

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
