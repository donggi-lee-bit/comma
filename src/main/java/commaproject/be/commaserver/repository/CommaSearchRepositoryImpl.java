package commaproject.be.commaserver.repository;

import static commaproject.be.commaserver.domain.comma.QComma.comma;

import com.querydsl.jpa.impl.JPAQueryFactory;
import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommaSearchRepositoryImpl implements CommaSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Comma> searchByDateCondition(LocalDateTime start, LocalDateTime end) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.createdAt.between(start, end))
            .fetch();
    }

    @Override
    public List<Comma> searchByUserCondition(String username) {
        return jpaQueryFactory
            .selectFrom(comma)
            .where(comma.username.eq(username))
            .fetch();
    }
}
