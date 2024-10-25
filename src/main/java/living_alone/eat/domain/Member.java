package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String username;
    private String loginId;
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    @OneToMany(mappedBy = "member")
    private List<Recipe> recipes;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    @Builder
    public Member(String loginId, String password, String username, Role role, Refrigerator ref) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.role = role;
        this.refrigerator = ref;
    }

}
