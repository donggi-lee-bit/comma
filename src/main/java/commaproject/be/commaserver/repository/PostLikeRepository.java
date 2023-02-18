package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.like.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByIdAndCommaId(Long loginUserId, Long commaId);
}
