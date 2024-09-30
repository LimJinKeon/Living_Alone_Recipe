package living_alone.eat.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Refrigerator {

    @Id @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "refrigerator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefrigeratorIngredient> ingredients = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.setRefrigerator(this);

    }

    public void addIngredient(RefrigeratorIngredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRefrigerator(this);
    }
}
