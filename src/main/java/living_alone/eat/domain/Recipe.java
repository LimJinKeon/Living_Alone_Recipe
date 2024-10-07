package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Recipe extends UploadFile{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String cookName;
    private String description;

    public Recipe(String uploadFileName, String storeFileName, Member member, String cookName, String description) {
        super(uploadFileName, storeFileName);
        this.member = member;
        this.cookName = cookName;
        this.description = description;
    }
}
