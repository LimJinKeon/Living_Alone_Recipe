package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ShopList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String memo;
}
