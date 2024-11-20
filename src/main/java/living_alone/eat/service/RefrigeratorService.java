package living_alone.eat.service;

import living_alone.eat.config.UserSessionUtil;
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

import static living_alone.eat.config.UserSessionUtil.*;

@Service
@RequiredArgsConstructor
public class RefrigeratorService {

    private final RefrigeratorRepository refrigeratorRepository;
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

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
}
