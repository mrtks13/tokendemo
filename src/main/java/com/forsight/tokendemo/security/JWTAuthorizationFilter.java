package com.forsight.tokendemo.security;

import com.forsight.tokendemo.util.ApplicationContextProvider;
import com.forsight.tokendemo.exception.ExpireTokenException;
import com.forsight.tokendemo.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.forsight.tokendemo.security.SecurityConstants.AUTHORIZATION_HEADER_NAME;
import static com.forsight.tokendemo.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTHORIZATION_HEADER_NAME);

        log.info("filter will run this header:{}", header);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER_NAME);
        token = token.replace(TOKEN_PREFIX, "");


        JwtTokenUtil jwtTokenUtil = ApplicationContextProvider.getApplicationContext().getBean(JwtTokenUtil.class);

        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpireTokenException();
        }
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        if (userName != null) {
            log.info("Set UsernamePasswordAuthenticationToken :{} ", userName);
            //TODO: Load user authorities from cache
            return new UsernamePasswordAuthenticationToken(userName, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_ROLE")));
        }
        return null;
    }

}

