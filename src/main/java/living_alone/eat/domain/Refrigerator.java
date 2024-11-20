package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "quantity"})
public class Refrigerator extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public void removeIngredient(int quantity) {
        int restQuantity = this.quantity - quantity;
        if (restQuantity < 0) {
//            throw new NotEnoughStockException("재고가 없습니다");
        }
        this.quantity = restQuantity;
    }
}
