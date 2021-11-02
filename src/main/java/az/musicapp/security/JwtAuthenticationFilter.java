package az.musicapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


            String token = tokenProvider.resolveToken(httpServletRequest);
            if (token != null && tokenProvider.validateToken(token)) {


                Authentication auth = tokenProvider.getAuthentication(token);



                if (auth != null) {

                    SecurityContextHolder.getContext().setAuthentication(auth);


                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);


        }
    }




