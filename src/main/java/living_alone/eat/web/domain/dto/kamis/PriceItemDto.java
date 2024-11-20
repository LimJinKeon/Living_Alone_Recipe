package living_alone.eat.web.domain.dto.kamis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceItemDto {
    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("price")
    private String price;

    @JsonProperty("market")
    private String market;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}