package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
