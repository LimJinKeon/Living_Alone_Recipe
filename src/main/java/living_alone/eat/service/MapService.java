package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static living_alone.eat.config.UserSessionUtil.getCurrentLoginId;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MemberRepository memberRepository;

    // 기본 주소 가져오기
    public String getDefaultAddress(String loginId) {
        Member member = memberRepository.findByLoginId(getCurrentLoginId()).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        return member.getAddress();
    }
}
