package living_alone.eat.service;

import jakarta.transaction.Transactional;
import living_alone.eat.domain.Member;
import living_alone.eat.exception.MyDuplicateIdException;
import living_alone.eat.web.domain.dto.AddMemberForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자 저장 테스트")
    void saveMemberTest() {
        //given
        AddMemberForm addMemberForm = new AddMemberForm();
        addMemberForm.setLoginId("testAdmin");
        addMemberForm.setPassword("testAdmin");
        addMemberForm.setUsername("테스트관리자");
        addMemberForm.setEmail("testAdmin@gmail.com");

        //when
        memberService.save(addMemberForm);

        //then
        Member findMember = memberService.findByLoginId(addMemberForm.getLoginId()).orElseThrow();
        assertThat(addMemberForm.getLoginId()).isEqualTo(findMember.getLoginId());
        assertThat(passwordEncoder.matches("testAdmin", findMember.getPassword())).isTrue();
        assertThat(addMemberForm.getUsername()).isEqualTo(findMember.getUsername());
        assertThat(addMemberForm.getEmail()).isEqualTo(findMember.getEmail());
        assertThatThrownBy(() -> memberService.save(addMemberForm)).isInstanceOf(MyDuplicateIdException.class);
    }
}