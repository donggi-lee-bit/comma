package commaproject.be.commaserver.repository;

import static commaproject.be.commaserver.domain.comma.QComma.comma;

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

    @Override
    public Page<Comma> searchByDateCondition(LocalDate start, LocalDate end, Pageable pageable) {
        List<Long> ids = jpaQueryFactory
            .select(comma.id)
            .from(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .orderBy(comma.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset() * pageable.getPageSize())
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
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .orderBy(comma.id.desc())
            .fetchOne();
        log.info("totalCount: {}", totalCount);
        return new PageImpl<>(pagination, pageable, totalCount);
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

        List<Comma> pagination = jpaQueryFactory
            .selectFrom(comma)
            .where(comma.id.in(ids))
            .orderBy(comma.id.desc())
            .fetch();

        Long totalCount = jpaQueryFactory
            .select(comma.count())
            .from(comma)
            .where(comma.username.eq(username))
            .orderBy(comma.id.desc())
            .fetchOne();
        return new PageImpl<>(pagination, pageable, totalCount);
    }

    @Override
    public Page<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end, Pageable pageable) {
        List<Long> ids = jpaQueryFactory
            .select(comma.id)
            .from(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .where(comma.username.eq(username))
            .orderBy(comma.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset() * pageable.getPageSize())
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
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .where(comma.username.eq(username))
            .orderBy(comma.id.desc())
            .fetchOne();

        return new PageImpl<>(pagination, pageable, totalCount);
    }
}
