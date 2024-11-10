package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Role;
import living_alone.eat.exception.MyDuplicateIdException;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.web.domain.dto.AddMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 정보 저장
    @Transactional
    public Member save(AddMemberForm form) {
        // 아이디 중복 확인
        boolean duplicate = duplicatedIdCheck(form.getLoginId());
        if (duplicate) {
            throw new MyDuplicateIdException("이미 존재하는 아이디입니다");
        }

        // 비밀번호 암호화 및 관리자 확인
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        Role role = Role.USER;
        if (form.getLoginId() == "admin") {
            role = Role.ADMIN;
        }

        Member member = Member.builder()
                .username(form.getUsername())
                .loginId(form.getLoginId())
                .password(encodedPassword)
                .role(role)
                .build();
        return memberRepository.save(member);
    }

    private boolean duplicatedIdCheck(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
