package com.phatpt.springExercise.Security;

import com.phatpt.springExercise.Security.Service.AccountDetailService;
import com.phatpt.springExercise.Security.jwt.JwtAuthEntryPoint;
import com.phatpt.springExercise.Security.jwt.JwtAuthTokenFilter;
import com.phatpt.springExercise.Security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    private final AccountDetailService accountDeatailService;

    private final JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    private static final String[] PUBLIC_MATCHERS = { "/css/**", "/js/**", "/image/**", "/book/**", "/user/**", "/**" };

    @Autowired
    public WebSecurityConfig(AccountDetailService accountDeatailService,
            JwtAuthEntryPoint unauthorizedHandler, JwtUtils jwtUtils) {
        this.accountDeatailService = accountDeatailService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter(){
        return new JwtAuthTokenFilter(jwtUtils, accountDeatailService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService((userDetailsService()));

        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
                            throws Exception{
        authenticationManagerBuilder.userDetailsService(accountDeatailService).passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/admin/roles").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.GET, "/categories/**").permitAll()
                                
                                .antMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.POST, "/products/create/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.GET, "/products/admin/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.GET, "/products/customer/**").permitAll()                          
                                                  
                                                    

                                .antMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("USER", "ADMIN")

                                .antMatchers(HttpMethod.POST, "/orderDetails/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers(HttpMethod.GET, "/orderDetails/**").hasAnyRole("USER", "ADMIN")

                                .antMatchers(HttpMethod.POST, "/cart/**").hasRole("USER")

                                .antMatchers("/accounts/**").hasAnyRole("USER", "ADMIN")

                                .antMatchers(PUBLIC_MATCHERS).permitAll()
                                
                                .antMatchers("/public/**").permitAll().anyRequest().authenticated()
                                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/deleteSession");
                                
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
