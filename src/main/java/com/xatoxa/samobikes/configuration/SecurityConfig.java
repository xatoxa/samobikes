package com.xatoxa.samobikes.configuration;

import com.xatoxa.samobikes.services.SamUserDetailsService;
import com.xatoxa.samobikes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new SamUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/users/**").hasAnyRole("ADMIN")
                .antMatchers("/bikes/management/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/parts/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/history/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/part-names/**").hasRole("ADMIN")
                .antMatchers("/bikes/**").hasRole("USER")
                .antMatchers("/comment/**").hasRole("USER")
                .antMatchers("/registration").anonymous()
                .antMatchers("/register-user").anonymous()
                .antMatchers("/check_username").permitAll()
                .antMatchers("/check_bike_info").permitAll()
                .antMatchers("/logout").anonymous()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login").permitAll()
                .and()
                .rememberMe().alwaysRemember(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**");
    }

}
