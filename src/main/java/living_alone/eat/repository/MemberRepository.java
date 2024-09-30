package living_alone.eat.repository;

import living_alone.eat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    public Optional<Member> findByUsername(String username);
    public Optional<Member> findByLoginId(String loginId);
}
