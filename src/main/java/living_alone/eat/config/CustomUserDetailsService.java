package living_alone.eat.config;

import living_alone.eat.domain.Member;
import living_alone.eat.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 다릅니다."));

        // 사용자의 권한을 GrantedAuthority 형태로 변환
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        return new org.springframework.security.core.userdetails.User(
                member.getLoginId(),
                member.getPassword(),
                authorities
        );
    }
}
