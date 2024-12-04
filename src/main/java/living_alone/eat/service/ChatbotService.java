package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Refrigerator;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.repository.RefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final MemberRepository memberRepository;
    private final RefrigeratorRepository refIngRepository;

    // API KEY
    @Value("${chatbot.api.key}")
    private String OPENAI_API_KEY;

    // 기본 GPT URL
    @Value("${chatbot.api.url}")
    private String OPENAI_API_URL;

    // 챗봇 응답 가져오기
    public String getChatbotResponse(String message, String userId) {
        RestTemplate restTemplate = new RestTemplate();

        // 사용자 기본키 가져오기
        Member member = memberRepository.findByLoginId(userId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        // 내 냉장고 재료 가져오기
        Optional<List<Refrigerator>> all = refIngRepository.findAllByMemberId(member.getId());
        String ingredients = "";

        if (all.isPresent()) {
            List<Refrigerator> refrigerator = all.get();
            for (Refrigerator ingredient : refrigerator) {
                ingredients += (ingredient.getName() + ingredient.getQuantity() + "개 ");
            }
        }
        message += ingredients.isBlank() ? " 냉장고가 비었습니다" : (" 내 냉장고에 있는 재료는 " + ingredients);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        // 요청 바디 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        // "messages" 필드에 시스템 메시지와 사용자 메시지 추가
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "자취생들을 위한 간단하게 만들 수 있는 쉬운 음식 레시피를 추천해준다. " +
                "레시피의 조리과정과 식재료들의 무게는 g단위로 자세하게 표시한다. " +
                "복잡한 레시피보다는 간단한 레시피 위주로 추천한다. " +
                "냉장고에 있는 재료 외에 다른 레시피를 원하면 상세히 알려준다." +
                "특정 레시피를 알려달라고 하면 냉장고에 있는 재료를 무시하고 알려준다");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", message);

        // messages 리스트에 시스템 메시지와 사용자 메시지 추가
        requestBody.put("messages", List.of(systemMessage, userMessage));

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // API 호출
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            // 성공적으로 API 호출 시
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, String> messageContent = (Map<String, String>) firstChoice.get("message");

                return messageContent.get("content");
            } else {
                return "챗봇에서 응답을 가져올 수 없습니다";
            }
        } catch (Exception e) {
            return "Chatbot error: " + e.getMessage();
        }
    }
}
