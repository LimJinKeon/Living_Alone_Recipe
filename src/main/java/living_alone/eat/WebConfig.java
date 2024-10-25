package living_alone.eat;

import living_alone.eat.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**") // 로그인 안할 시 모든 경로 막기
                .excludePathPatterns(
                        "/", "/*.ico", "/login", "/logout",     // 로그인 안해도 접근 가능한 경로
                        "/css/**", "/img/**", "/members/add", "/error");
    }
}
*/
