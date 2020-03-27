package com.mbsystems.bmksecuritydemo.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByUsername( String username );
}
