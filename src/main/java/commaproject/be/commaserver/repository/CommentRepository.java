package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommaId(Long commaId);
}
