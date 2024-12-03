package living_alone.eat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import living_alone.eat.web.domain.dto.kamis.DailyPriceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class KamisService {

    @Value("${kamis.api.key}")
    private String API_KEY;

    @Value("${kamis.api.id}")
    private String API_ID;

    @Value("${kamis.api.url}")
    private String API_URL;

    public List<DailyPriceDto> getRecentlyPriceTrend() throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("&p_cert_key=").append(API_KEY);
        urlBuilder.append("&p_cert_id=").append(API_ID);

        // URL 연결 설정
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setInstanceFollowRedirects(true); // 리다이렉션 자동 처리

        // 리다이렉트
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
            String newUrl = conn.getHeaderField("Location"); // 리다이렉트된 URL
            System.out.println("Redirected to: " + newUrl);

            conn = (HttpURLConnection) new URL(newUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        }

        // 응답 내용 읽기
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseJsonToDto(response.toString());
    }

    // JSON 파싱 및 DTO 매핑
    private List<DailyPriceDto> parseJsonToDto(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        List<DailyPriceDto> priceList = new ArrayList<>();
        JsonNode dataList = rootNode.get("price"); // JSON 구조에 따라 응답값 "data" key 사용

        if (dataList != null && dataList.isArray()) {
            for (JsonNode dataNode : dataList) {
                DailyPriceDto dto = new DailyPriceDto();
                dto.setItemName(dataNode.get("item_name").asText());
                dto.setUnit(dataNode.get("unit").asText());
                dto.setDpr1(dataNode.get("dpr1").asText());
                priceList.add(dto);
            }
        }
        return priceList;
    }
}