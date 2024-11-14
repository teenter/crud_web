package org.nekto.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value(value = "${jwt.secret}")
    private String secret;


    public UserDetails parseUserDetails(String token) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                ArrayList<?> roles = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .get("roles", ArrayList.class);
                if (roles != null) {
                    for (Object role: roles) {
                        if (role instanceof String) {
                            grantedAuthorities.add(
                                    new SimpleGrantedAuthority("ROLE_" + ((String) role).toUpperCase()));
                        }
                    }
                }
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
    public void validateToken(String token) throws ExpiredJwtException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        claims.getBody().getExpiration().before(new Date());
    }
}
