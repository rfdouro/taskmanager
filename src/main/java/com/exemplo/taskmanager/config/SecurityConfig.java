package com.exemplo.taskmanager.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exemplo.taskmanager.model.Usuario;
import com.exemplo.taskmanager.repository.UsuarioRepository;
import com.exemplo.taskmanager.util.BasicSecurityUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements CommandLineRunner {

  @Autowired
  BasicSecurityUtil basicAuth;

  @Autowired
  UsuarioRepository usuarioRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(c -> {
      c.disable();
    });
    http.cors(c -> {
      c.disable();
    });
    http.authorizeHttpRequests(c -> {
      c.requestMatchers("/auth**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()// Endpoint de
          // autenticação público
          .requestMatchers("/tasks/**").hasAuthority("TASK")
          .requestMatchers("/responsibles/**").hasAuthority("RESPONSIBLE")
          .requestMatchers("/usuarios/**").hasAuthority("ADMIN");
      c.anyRequest().authenticated();
    });
    http.headers(h -> {
      h.frameOptions(fo -> {
        fo.sameOrigin();
      });
    });

    http.sessionManagement(t -> {
      t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });
    http.addFilterBefore(new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
        try {
          String authHeader = request.getHeader("Authorization");
          if (authHeader != null && authHeader.startsWith("Basic ")) {
            String token = authHeader.substring(6);
            String userName = basicAuth.extractUsername(token);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails user = usuarioRepository.findByLogin(userName).get();
              if (basicAuth.isValidAuth(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);// aqui é feita a adição da camada de segurança no contexto da aplicação
              }
            }
          }
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
        filterChain.doFilter(request, response);
      }
    }, UsernamePasswordAuthenticationFilter.class);
    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      Usuario u = new Usuario();
      u.setLogin("admin");
      u.setSenha("{noop}123456");
      u.setPermissoes(List.of("ADMIN", "RESPONSIBLE"));
      usuarioRepository.save(u);

    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }

}
