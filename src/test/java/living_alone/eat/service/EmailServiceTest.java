package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.web.domain.dto.AddMemberForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    EmailService emailService;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("이메일 전송 테스트")
    public void emailServiceTest() throws Exception {
        //given
        AddMemberForm addMemberForm = new AddMemberForm();
        addMemberForm.setLoginId("testAdmin");
        addMemberForm.setPassword("testAdmin1");
        addMemberForm.setUsername("테스트관리자");
        addMemberForm.setEmail("0907john@naver.com");
        memberService.save(addMemberForm);

        //when
        emailService.sendSimpleEmail(addMemberForm.getLoginId(), "테스트용\n데이터");

        //then
        // 이메일로 데이터 확인
    }

}