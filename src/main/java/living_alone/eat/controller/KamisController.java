//package living_alone.eat.controller;
//
//import living_alone.eat.service.KamisService;
//import living_alone.eat.web.domain.dto.kamis.PriceItemDto;
//import living_alone.eat.web.domain.dto.kamis.PriceResponseDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/kamis/prices")
//public class KamisController {
//
//
//    @Autowired
//    private KamisService retailPriceService;
//
//    @GetMapping("/retail-prices")
//    public String getRetailPrices(
//            @RequestParam String startDate,
//            @RequestParam String endDate,
//            Model model) {
//
//        List<PriceItemDto> items = retailPriceService.getRetailPrices(startDate, endDate);
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
//        model.addAttribute("items", items);
//
//        return "retail-prices";
//    }
//}