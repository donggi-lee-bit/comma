package commaproject.be.commaserver.service.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CommaPaginatedResponse {

    private int currentPage;
    private int pageSize;
    private long totalPages;
    private List<CommaDetailResponse> commaDetailResponses;

    public CommaPaginatedResponse(int currentPage, int pageSize, long totalPages,
        List<CommaDetailResponse> commaDetailResponses) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.commaDetailResponses = commaDetailResponses;
    }
}
