package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "username", "loginId", "role"})
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String loginId;
    private String password;
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "member")
    private List<Refrigerator> refrigerators;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    @Builder
    public Member(String loginId, String password, String username, String email, Role role, String address) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
        this.address = address;
    }

}
