package living_alone.eat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import living_alone.eat.domain.Member;
import living_alone.eat.web.domain.dto.LoginForm;

import static living_alone.eat.domain.QMember.member;

public class MemberRepositoryImpl implements MemberCustomRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member login(LoginForm form) {
        Member result = queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(form.getLoginId())
                        .and(member.password.eq(form.getPassword())))
                .fetchOne();
        return result;
    }
}
