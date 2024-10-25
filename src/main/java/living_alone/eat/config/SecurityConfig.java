package living_alone.eat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/.ico","/login", "/logout", // 로그인 없이 접근 가능한 경로
                                        "/css/**", "/img/**", "/members/add", "/error").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")            // 로그인 페이지 설정
                                .usernameParameter("loginId")   // 기본은 username, 나는 객체 필드명이 loginId라서
                                .passwordParameter("password")
                                .successHandler(new RedirectPageHandler())
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                )
                .sessionManagement(SessionManagement ->
                        SessionManagement
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 방식 설정
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
