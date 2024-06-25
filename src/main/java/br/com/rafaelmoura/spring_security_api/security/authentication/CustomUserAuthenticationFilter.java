package br.com.rafaelmoura.spring_security_api.security.authentication;

import br.com.rafaelmoura.spring_security_api.model.entity.User;
import br.com.rafaelmoura.spring_security_api.repository.UserRepository;
import br.com.rafaelmoura.spring_security_api.security.config.SecurityConfiguration;
import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!isPublicEndpoint(request)) {
            String token = getTokenFromRequest(request);

            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                User user = userRepository.findByEmail(subject).get();

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                throw new BadCredentialsException("Token nao informado na requisicao");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<String> publicEndpoints = Arrays.asList(SecurityConfiguration.ENDPOINTS_WITHOUT_AUTH);
        AntPathMatcher pathMatcher = new AntPathMatcher();

        return publicEndpoints.stream().anyMatch(endpoint -> pathMatcher.match(endpoint, requestURI));
    }

}
