package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Recipe extends UploadFile{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String recipeTitle;
    private String recipeContent;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Builder
    public Recipe(String uploadFileName, String storeFileName, Member member, String recipeTitle, String recipeContent) {
        super(uploadFileName, storeFileName);
        this.member = member;
        this.recipeTitle = recipeTitle;
        this.recipeContent = recipeContent;
    }

    // 레시피 수정
    public Recipe update(String recipeTitle, String recipeContent, String uploadFileName, String storeFileName) {
        this.recipeTitle = recipeTitle;
        this.recipeContent = recipeContent;
        super.updateFile(uploadFileName, storeFileName);
        return this;
    }
}
