package commaproject.be.commaserver.service.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CommaPaginatedResponse {

    private int currentPage;
    private int lastPage;
    private int offsetSize;
    private List<CommaDetailResponse> commaDetailResponses;

    public CommaPaginatedResponse(int currentPage, int lastPage, int offsetSize,
        List<CommaDetailResponse> commaDetailResponses) {
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.offsetSize = offsetSize;
        this.commaDetailResponses = commaDetailResponses;
    }
}
