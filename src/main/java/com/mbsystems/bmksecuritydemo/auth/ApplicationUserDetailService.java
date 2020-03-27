package com.mbsystems.bmksecuritydemo.auth;

import com.google.common.collect.Lists;
//import com.mbsystems.bmksecuritydemo.domain.User;
//import com.mbsystems.bmksecuritydemo.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.ADMIN;
import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.STUDENT;

@Service
public class ApplicationUserDetailService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public ApplicationUserDetailService( @Qualifier("fake") ApplicationUserDao applicationUserDao ) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return applicationUserDao
                .selectApplicationUserByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found" ) );
    }
}
