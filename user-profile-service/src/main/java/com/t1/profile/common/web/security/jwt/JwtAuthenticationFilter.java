package com.t1.profile.common.web.security.jwt;

import com.t1.profile.common.web.security.details.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   UserDetailsServiceImpl userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
            IOException {
        try {
            String jwt = JwtFromRequest.getJwt(request);
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                String username = tokenProvider.getUserNameFromJWT(jwt);
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
// Устанавливаем аутентификацию в контекст безопасности
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
// Логирование ошибки
            /*logger.error("Не удалось установить аутентификацию пользователя: {}",
                    ex.getMessage());*/
        }
        filterChain.doFilter(request, response);
    }


}
