package commaproject.be.commaserver.repository;

import static commaproject.be.commaserver.domain.comma.QComma.comma;

import com.querydsl.jpa.impl.JPAQueryFactory;
import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
public class CommaSearchRepositoryImpl implements CommaSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comma> searchByDateCondition(LocalDate start, LocalDate end, Pageable pageable) {
        List<Long> ids = jpaQueryFactory
            .select(comma.id)
            .from(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .orderBy(comma.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset() * pageable.getPageSize())
            .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.id.in(ids))
            .orderBy(comma.id.desc())
            .fetch();
    }

    @Override
    public Page<Comma> searchByUserCondition(String username, Pageable pageable) {
        List<Long> ids = jpaQueryFactory
            .select(comma.id)
            .from(comma)
            .where(comma.username.eq(username))
            .orderBy(comma.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset() * pageable.getPageSize())
            .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<Comma> commas = jpaQueryFactory
            .selectFrom(comma)
            .where(comma.id.in(ids))
            .orderBy(comma.id.desc())
            .fetch();

        return new PageImpl<>(commas, pageable, 0);
    }

    @Override
    public List<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end, Pageable pageable) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .where(comma.username.eq(username))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
