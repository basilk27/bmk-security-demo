package com.mbsystems.bmksecuritydemo.jwt;

import com.mbsystems.bmksecuritydemo.exceptions.BmkSecurityExceptions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.parser;

public class JwtTokenVerifier extends OncePerRequestFilter {

    public JwtTokenVerifier() {
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader( "Authorization" );

        if ( StringUtils.isEmpty( authorizationHeader ) || !authorizationHeader.startsWith( "Bearer " ) ) {
            filterChain.doFilter( request, response );
            return;
        }

        try {
            String secretkey = "securesecuresecuresecuresecuresecuresecuresecure";

            String token =  authorizationHeader.replace( "Bearer ", "" );

            Jws<Claims> claimsJws = Jwts.parser()
                                        .setSigningKey( Keys.hmacShaKeyFor( secretkey.getBytes() ) )
                                        .parseClaimsJws( token );

            Claims claimsBody = claimsJws.getBody();

            String username = claimsBody.getSubject();

            var authorities = (List<Map<String, String>>) claimsBody.get( "authorities" );

            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                                                .map( m -> new SimpleGrantedAuthority( m.get( "authority" ) ) )
                                                .collect( Collectors.toSet());

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken( username,
                                                             null,
                                                              simpleGrantedAuthoritySet );

            SecurityContextHolder.getContext().setAuthentication( authentication );
        }
        catch ( Exception ex ) {
            throw new BmkSecurityExceptions( "JwtTokenVerifier::doFilterInternal - Token Invalid", ex );
        }

        filterChain.doFilter( request, response );
    }
}
