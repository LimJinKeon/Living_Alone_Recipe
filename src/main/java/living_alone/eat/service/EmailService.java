package living_alone.eat.service;

import living_alone.eat.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final MemberService memberService;

    public void sendSimpleEmail(String loginId, String memo) {
        // 사용자 이메일 가져오기
        Member member = memberService.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        String to = member.getEmail();

        // 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("장보기 리스트");
        message.setText(memo);
//        message.setFrom("0907john24@gmail.com");

        mailSender.send(message);
    }
}