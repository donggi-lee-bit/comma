package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommaService {

    CommaDetailResponse readOne(Long commaId);

    List<CommaDetailResponse> readAll(Pageable pageable);

    CommaResponse create(Long loginUserId, CommaRequest commaRequest);

    CommaDetailResponse update(Long loginUserId, Long commaId, CommaRequest commaRequest);

    Comma remove(Long loginUserId, Long commaId);
}
