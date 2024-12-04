package living_alone.eat.repository;

import living_alone.eat.domain.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {

    public Optional<List<Refrigerator>> findAllByMemberId(Long userId);
}
