package living_alone.eat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   //csrf 비활성화
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/.ico","/login", "/logout", // 로그인 없이 접근 가능한 경로
                                        "/css/**", "/img/**", "/members/add", "/error").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")  //관리자만 접근 가능한 경로
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")            // 로그인 페이지 설정
                                .usernameParameter("loginId")   // 기본은 username, 나는 객체 필드명이 loginId라서
                                .passwordParameter("password")
                                .successHandler(new RedirectPageHandler())  // 이전에 접속 시도했던 URL로 리다이렉트
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                )
                .sessionManagement(SessionManagement ->
                        SessionManagement
                                .maximumSessions(1)                 // 한 사용자당 허용되는 세션 수
                                .maxSessionsPreventsLogin(false)    // 중복 로그인 방지, 새로운 로그인 시도 허용
                                .sessionRegistry(sessionRegistry()) // 동시 로그인 세션 관리 및 추적
                                .expiredUrl("/login?expire"));      // 세션 만료 시 URL, 순서 중요
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 방식 설정
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // 인증 설정
    }

}
