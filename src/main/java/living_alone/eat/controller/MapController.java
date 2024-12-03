package living_alone.eat.controller;

import living_alone.eat.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import static living_alone.eat.config.UserSessionUtil.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    // 근처 마트 폼
    @GetMapping()
    public String mapForm() {
        return "menu/map";
    }

    // 사용자의 기본 주소 가져오기
    @GetMapping("/defaultAddress")
    public ResponseEntity<Map<String, String>> getDefaultAddress() {
        // 사용자가 설정한 기본 주소 반환
        String  defaultAddress = mapService.getDefaultAddress(getCurrentLoginId());

        Map<String, String> response = new HashMap<>();
        if (defaultAddress != null) {
            response.put("address", defaultAddress);
        } else {
            response.put("address", null); // 기본 주소가 없을 경우 null 반환
        }
        return ResponseEntity.ok(response);
    }
}
