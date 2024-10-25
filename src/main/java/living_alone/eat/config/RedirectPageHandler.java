package living_alone.eat.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class RedirectPageHandler implements AuthenticationSuccessHandler {

    private final HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // 저장된 요청이 있을 경우 그 URL로 리다이렉트
            String targetUrl = savedRequest.getRedirectUrl();
            response.sendRedirect(targetUrl);
        } else {
            // 기본 URL 설정
            response.sendRedirect("/home");
        }
    }
}
