package living_alone.eat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CookController {

    @GetMapping("/cook")
    public String cook() {
        return "menu/cook";
    }

}
