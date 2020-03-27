package com.mbsystems.bmksecuritydemo.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.ADMIN;
import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.mbsystems.bmksecuritydemo.security.ApplicationUserRole.STUDENT;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername( String username ) {
            Optional<ApplicationUser> applicationUserOptional = this.getApplicationUsers()
                    .stream()
                    .filter( applicationUser -> username.equals( applicationUser.getUsername() ) )
                    .findFirst();

        System.out.println( applicationUserOptional );
        if ( applicationUserOptional.isPresent() ) {
            System.out.println( "bmk we have the user");
        }

        return applicationUserOptional;
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "annasmith",
                        passwordEncoder.encode("password"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUsers;
    }
}
