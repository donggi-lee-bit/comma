package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import org.springframework.data.domain.Pageable;

public interface CommaSearchService {

    CommaPaginatedResponse searchByCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable);
}
