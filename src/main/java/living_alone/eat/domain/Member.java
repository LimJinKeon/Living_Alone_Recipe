package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String loginId;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "member")
    private List<Refrigerator> refrigerators;

    @OneToOne(mappedBy = "member")
    private ShopList shopList;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @Builder
    public Member(String loginId, String password, String username, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.role = role;
    }

}
