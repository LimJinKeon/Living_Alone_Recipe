package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.web.domain.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginForm form) {
        return memberRepository.login(form);
    }
}
