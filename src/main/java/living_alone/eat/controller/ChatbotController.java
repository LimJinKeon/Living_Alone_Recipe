package living_alone.eat.controller;

import living_alone.eat.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

//    @PostMapping("/message")
//    public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> request) {
//        String userMessage = request.get("message");
//        String response = chatbotService.getChatbotResponse(userMessage);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Map<String, String> request,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        // 로그인한 사용자 아이디 가져오기
        String userId = "";
        if (userDetails != null) {
            userId = userDetails.getUsername();
        }

        String userMessage = request.get("message");
        String response = chatbotService.getChatbotResponse(userMessage, userId);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("response", response);

        return ResponseEntity.ok(responseBody);
    }
}
