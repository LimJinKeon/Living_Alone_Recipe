package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.exception.MyDuplicateId;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.web.domain.dto.AddMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(AddMemberForm form) {
        Member member = new Member(form.getLoginId(), form.getPassword(), form.getUsername());
        boolean duplicate = duplicatedIdCheck(member.getLoginId());

        if (duplicate) {
            throw new MyDuplicateId("이미 존재하는 아이디입니다");
        }
        return memberRepository.save(member);
    }

    private boolean duplicatedIdCheck(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUsername(String name) {
        return memberRepository.findByUsername(name);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
