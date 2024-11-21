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

    // 수량 증가
    public Refrigerator increaseQuantity() {
        quantity++;
        return this;
    }

    // 수량 감소
    public Refrigerator decrementQuantity() {
        this.quantity = this.quantity - 1;
        return this;
    }
}
