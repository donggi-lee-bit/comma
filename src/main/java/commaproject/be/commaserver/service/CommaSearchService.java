package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommaSearchService {

    List<CommaDetailResponse> searchByDateCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);

    List<CommaDetailResponse> searchByUserCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);

    List<CommaDetailResponse> searchByUserDateCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);
}
