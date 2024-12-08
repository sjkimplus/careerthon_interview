package com.sparta.careerthon_interview.common.config;

import com.sparta.careerthon_interview.common.exception.CareerthonException;
import com.sparta.careerthon_interview.common.exception.ErrorCode;
import com.sparta.careerthon_interview.user.dto.AuthUser;
import com.sparta.careerthon_interview.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpRequest,
            @NonNull HttpServletResponse httpResponse,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String authorizationHeader = httpRequest.getHeader("Authorization");
        String url = httpRequest.getRequestURI();

        // Skip JWT authentication for Swagger and public API paths
        if (url.startsWith("/v3/api-docs") || url.startsWith("/swagger-ui") || url.startsWith("/swagger-ui.html")) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && !url.startsWith("/users/sign-up") && !url.startsWith("/users/sign-in")) {
            String jwt = jwtUtil.substringToken(authorizationHeader);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);
                Long userId = Long.parseLong(claims.getSubject());
                String email = claims.get("username", String.class);
                UserRole userRole = UserRole.of(claims.get("userRole", String.class));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    AuthUser authUser = new AuthUser(userId, email, userRole);

                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (SecurityException | MalformedJwtException e) {
                throw new CareerthonException(ErrorCode.JWT_INVALID);
            } catch (ExpiredJwtException e) {
                throw new CareerthonException(ErrorCode.JWT_EXPIRED);
            } catch (UnsupportedJwtException e) {
                throw new CareerthonException(ErrorCode.JWT_TYPE_ERROR);
            } catch (Exception e) {
                throw new CareerthonException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
