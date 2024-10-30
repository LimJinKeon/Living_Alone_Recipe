package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Ingredient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate expiredDate;

    @OneToMany(mappedBy = "ingredient")
    private List<Refrigerator> refrigerator;
}
