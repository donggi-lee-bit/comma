package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import java.util.List;

public interface CommaService {

    CommaDetailResponse readOne(Long commaId);

    List<CommaDetailResponse> readAll();

    CommaResponse create(Long loginUserId, CommaRequest commaRequest);

    CommaDetailResponse update(Long loginUserId, Long commaId, CommaRequest commaRequest);

    CommaResponse remove(Long loginUserId, Long commaId);
}
