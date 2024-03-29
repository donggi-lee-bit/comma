package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comment.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommaId(Long commaId, Pageable pageable);

    Optional<Comment> findByIdAndCommaId(Long commentId, Long commaId);
}
