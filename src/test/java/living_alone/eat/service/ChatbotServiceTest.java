package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.web.domain.dto.AddMemberForm;
import living_alone.eat.web.domain.dto.RefrigeratorForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
class ChatbotServiceTest {

    @Autowired
    private ChatbotService chatbotService;

    Logger logger = Logger.getLogger(ChatbotServiceTest.class.getName());

    @Test
    @DisplayName("챗봇 응답 테스트")
    void chatbotResponseTest() throws Exception {
        String chatbotResponse = chatbotService.getChatbotResponse("김치볶음밥 만드는 법 알려줘", "test");
        logger.info(chatbotResponse);
    }
}