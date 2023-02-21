package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.util.List;

public interface CommaSearchService {

    List<CommaDetailResponse> searchByCondition(CommaSearchConditionRequest commaSearchConditionRequest);
}
