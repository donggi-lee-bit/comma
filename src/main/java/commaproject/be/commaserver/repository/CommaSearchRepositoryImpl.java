package commaproject.be.commaserver.repository;

import static commaproject.be.commaserver.domain.comma.QComma.comma;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
public class CommaSearchRepositoryImpl implements CommaSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Comma> searchByCondition(Pageable pageable, LocalDate dateCondition, String usernameCondition) {
        log.info("date: {}, username: {}", dateCondition, usernameCondition);
        List<Long> ids = jpaQueryFactory
            .select(comma.id)
            .from(comma)
            .where(dateBetween(dateCondition), usernameEq(usernameCondition))
            .orderBy(comma.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<Comma> pagination = jpaQueryFactory
            .selectFrom(comma)
            .where(comma.id.in(ids))
            .orderBy(comma.id.desc())
            .fetch();

        Long totalCount = jpaQueryFactory
            .select(comma.count())
            .from(comma)
            .where(dateBetween(dateCondition), usernameEq(usernameCondition))
            .orderBy(comma.id.desc())
            .fetchOne();

        return new PageImpl<>(pagination, pageable, totalCount);
    }

    private BooleanExpression dateBetween(LocalDate dateCondition) {
        if (dateCondition == null) {
            return null;
        }

        return comma.createdAt.between(dateCondition.atStartOfDay(), dateCondition.plusDays(1).atStartOfDay());
    }

    private BooleanExpression usernameEq(String username) {
        if (username == null) {
            return null;
        }

        return comma.username.eq(username);
    }
}
