package commaproject.be.commaserver.repository;

import static commaproject.be.commaserver.domain.comma.QComma.comma;

import com.querydsl.jpa.impl.JPAQueryFactory;
import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommaSearchRepositoryImpl implements CommaSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comma> searchByDateCondition(LocalDate start, LocalDate end) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .fetch();
    }

    @Override
    public List<Comma> searchByUserCondition(String username) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.username.eq(username))
            .fetch();
    }

    @Override
    public List<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.createdAt.between(start.atStartOfDay(), end.atStartOfDay()))
            .where(comma.username.eq(username))
            .fetch();
    }
}
