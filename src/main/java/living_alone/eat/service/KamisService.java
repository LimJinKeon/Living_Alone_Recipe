//package living_alone.eat.service;
//
//import living_alone.eat.web.domain.dto.kamis.PriceItemDto;
//import living_alone.eat.web.domain.dto.kamis.PriceResponseDto;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//
//@Service
//public class KamisService {
//    @Value("${api.url}")
//    private String apiUrl;
//
//    @Value("${api.key}")
//    private String apiKey;
//
//    public List<PriceItemDto> getRetailPrices(String startDate, String endDate) {
//        String url = String.format("%s?serviceKey=%s&startDate=%s&endDate=%s&format=json",
//                apiUrl, apiKey, startDate, endDate);
//
//        RestTemplate restTemplate = new RestTemplate();
//        PriceResponseDto response = restTemplate.getForObject(url, PriceResponseDto.class);
//
//        return response != null ? response.getItems() : null;
//    }
//}
