package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import org.springframework.data.domain.Pageable;

public interface CommaSearchService {

    CommaPaginatedResponse searchByDateCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);

    CommaPaginatedResponse searchByUserCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);

    CommaPaginatedResponse searchByUserDateCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);
}
