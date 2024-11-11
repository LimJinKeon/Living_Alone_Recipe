package living_alone.eat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import living_alone.eat.config.UserSessionUtil;
import living_alone.eat.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static living_alone.eat.config.UserSessionUtil.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    // 장보기 리스트 폼
    @GetMapping()
    public String emailForm() {
        return "menu/emailForm";
    }

    // 장보기 리스트 이메일 전송
    @ResponseBody
    @PostMapping("/send")
    public String sendEmail(@RequestBody String memo) throws JsonProcessingException {
        // JSON 문자열을 Map으로 변환
        Map<String, Object> memoMap = objectMapper.readValue(memo, Map.class);

        // "memo" 키의 값을 배열로 가정하고 가져오기
        List<String> memoList = (List<String>) memoMap.get("memo");

        // 리스트 요소들을 단일 문자열로 변환 (줄바꿈 문자 추가)
        String memoContent = memoList.stream().collect(Collectors.joining("\n"));

        emailService.sendSimpleEmail(getCurrentLoginId(), memoContent);
        return "리스트가 성공적으로 전송되었습니다";
    }
}
