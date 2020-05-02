package com.mbsystems.bmksecuritydemo.security;

import com.mbsystems.bmksecuritydemo.auth.ApplicationUserDetailService;
import com.mbsystems.bmksecuritydemo.jwt.JwtTokenVerifier;
import com.mbsystems.bmksecuritydemo.jwt.JwtUserNameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
    public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig( ApplicationUserDetailService userDetailsService, PasswordEncoder passwordEncoder ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable() // bmk use this if calls are for server not UI else below
////            .csrf()
////                .csrfTokenRepository( CookieCsrfTokenRepository.withHttpOnlyFalse() )
//            .and()
            .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
            .and()
            .addFilter( new JwtUserNameAndPasswordAuthenticationFilter( authenticationManager() ) )
            .addFilterAfter( new JwtTokenVerifier(), JwtUserNameAndPasswordAuthenticationFilter.class )
            .authorizeRequests()
            .antMatchers( "/", "index", "/css/*", "/js/*" ).permitAll()
            .antMatchers( "/api/**" ).hasRole( STUDENT.name() )
            .anyRequest()
            .authenticated();
// bmk all below were used for form and basic auth
//            .and()
//            .formLogin()
//                .loginPage( "/login" )
//                .permitAll()
//                .defaultSuccessUrl( "/courses", true )
//                .passwordParameter( "password" )
//                .usernameParameter( "username" )
//            .and()
//            .rememberMe()
//                .tokenValiditySeconds( (int) TimeUnit.DAYS.toDays( 1 ) )
//                .key( "somekeycreatedbyteam" )
//                .rememberMeParameter( "remember-me" )
//            .and()
//            .logout()
//                .logoutUrl("/logout")
//                //if you are using csrf().disable(), next line is good
//                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout", "GET" ) )
//                .clearAuthentication( true )
//                .invalidateHttpSession( true )
//                .deleteCookies( "JSESSIONID", "remember-me" )
//                .logoutSuccessUrl( "/login" );
////            .httpBasic();   //use this for Basic Authentication
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder( passwordEncoder );

        provider.setUserDetailsService( userDetailsService );

        return provider;
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.authenticationProvider( daoAuthenticationProvider() );
    }

    //  bmk - this was used while we were using the InMemoryUserDetailsManager
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails annaSmithUser = User.builder()
//                                        .username( "annasmith" )
//                                        .password( this.passwordEncoder.encode( "password" ) )
////                                        .roles( ApplicationUserRole.STUDENT.name() )
//                                        .authorities( STUDENT.getGrantedAuthorities() )
//                                        .build();
//
//        UserDetails lindaUser = User.builder()
//                .username( "linda" )
//                .password( this.passwordEncoder.encode( "password" ) )
////                .roles( ApplicationUserRole.ADMIN.name() )
//                .authorities( ADMIN.getGrantedAuthorities() )
//                .build();
//
//        UserDetails tomUser = User.builder()
//                .username( "tom" )
//                .password( this.passwordEncoder.encode( "password" ) )
////                .roles( ApplicationUserRole.ADMINTRAINEE.name() )
//                .authorities( ADMINTRAINEE.getGrantedAuthorities() )
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                annaSmithUser,
//                tomUser,
//                lindaUser
//        );
//    }
}
