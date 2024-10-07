package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefrigeratorIngredient extends DateEntity {

    @Id @GeneratedValue
    private long id;

    private String name;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    protected void setRefrigerator(final Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void removeIngredient(int quantity) {
        int restQuantity = this.quantity - quantity;
        if (restQuantity < 0) {
//            throw new NotEnoughStockException("재고가 없습니다");
        }
        this.quantity = restQuantity;
    }
}
