package living_alone.eat.web.domain.dto.kamis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PriceResponseDto {

    @JsonProperty("data")
    private List<PriceItemDto> items;

    public List<PriceItemDto> getItems() {
        return items;
    }

    public void setItems(List<PriceItemDto> items) {
        this.items = items;
    }
}