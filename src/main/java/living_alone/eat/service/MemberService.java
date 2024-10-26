package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Refrigerator;
import living_alone.eat.domain.Role;
import living_alone.eat.exception.MyDuplicateId;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.web.domain.dto.AddMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            throw new MyDuplicateId("이미 존재하는 아이디입니다");
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
                .ref(new Refrigerator())
                .build();
        return memberRepository.save(member);
    }

    private boolean duplicatedIdCheck(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public Optional<Member> findByUsername(String name) {
        return memberRepository.findByUsername(name);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
