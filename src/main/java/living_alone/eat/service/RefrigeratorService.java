package living_alone.eat.service;

import living_alone.eat.domain.Ingredient;
import living_alone.eat.domain.Member;
import living_alone.eat.domain.Refrigerator;
import living_alone.eat.repository.IngredientRepository;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.repository.RefrigeratorRepository;
import living_alone.eat.web.domain.dto.RefrigeratorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static living_alone.eat.config.UserSessionUtil.getCurrentLoginId;

@Service
@RequiredArgsConstructor
@Transactional
public class RefrigeratorService {

    private final RefrigeratorRepository refrigeratorRepository;
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    // 재료 저장
    public Refrigerator save(RefrigeratorForm form) {
        // 사용자 및 재료 찾기
        Member member = memberRepository.findByLoginId(getCurrentLoginId()).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Ingredient ingredient = ingredientRepository.findByName(form.getName()).orElse(null);

        // Dto에서 엔티티로 변환
        Refrigerator refrigerator = Refrigerator.builder()
                .name(form.getName())
                .quantity(form.getQuantity())
                .member(member)
                .ingredient(ingredient)
                .build();

        return refrigeratorRepository.save(refrigerator);
    }

    // 재료 삭제
    public void deleteIngredient(Long id) {
        refrigeratorRepository.deleteById(id);
    }

    // 재료 수량 증가
    public Refrigerator incrementQuantity(Long id) {
        Refrigerator refrigerator = refrigeratorRepository.findById(id).orElseThrow();
        return refrigerator.increaseQuantity();
    }

    // 재료 수량 감소
    public Refrigerator decrementQuantity(Long id) {
        Refrigerator refrigerator = refrigeratorRepository.findById(id).orElseThrow();
        Refrigerator decrementedQuantity = refrigerator.decrementQuantity();

        // 수량이 0이하일 경우 재료 삭제
        if (refrigerator.getQuantity() <= 0) {
            deleteIngredient(refrigerator.getId());
        }
        return decrementedQuantity;
    }

    // 내 냉장고 재료 가져오기
    @Transactional(readOnly = true)
    public List<RefrigeratorForm> findAllByMemberId(String userId) {
        // 사용자 기본키 가져오기
        Member member = memberRepository.findByLoginId(userId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        Optional<List<Refrigerator>> refrigerators = refrigeratorRepository.findAllByMemberId(member.getId());
        List<RefrigeratorForm> forms = new ArrayList<>();

        // DTO 변환
        if (refrigerators.isPresent()) {
            for (Refrigerator refrigerator : refrigerators.get()) {
                forms.add(RefrigeratorForm.builder()
                        .id(refrigerator.getId())
                        .name(refrigerator.getName())
                        .quantity(refrigerator.getQuantity())
                        .build());
            }
        }
        return forms;
    }
}
