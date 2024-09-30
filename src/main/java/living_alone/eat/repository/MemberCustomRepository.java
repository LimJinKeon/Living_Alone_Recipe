package living_alone.eat.repository;

import living_alone.eat.domain.Member;
import living_alone.eat.web.domain.dto.LoginForm;

public interface MemberCustomRepository {

    public Member login(LoginForm form);
}
