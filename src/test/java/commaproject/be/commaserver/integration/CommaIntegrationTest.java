package commaproject.be.commaserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@DisplayName("회고 게시글 통합 테스트")
public class CommaIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("모든 회고 게시글 조회 시 페이징을 적용해서 반환한다")
    void readAll_comma_pagination() {
        int pageSize = 2;
        int pageSize2 = 1;
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        PageRequest pageRequest2 = PageRequest.of(0, pageSize2);

        CommaPaginatedResponse commaPaginatedResponse = commaService.readAll(pageRequest);
        CommaPaginatedResponse commaPaginatedResponse2 = commaService.readAll(pageRequest2);

        assertSoftly(softly -> {
            softly.assertThat(commaPaginatedResponse.getCommaDetailResponses().size()).isEqualTo(2);
            softly.assertThat(commaPaginatedResponse2.getCommaDetailResponses().size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("100명의 사용자가 동시에 게시글 조회 시 조회수가 100 증가한다")
    void read_comma_update_view() throws InterruptedException {
        int threadCount = 100;
        Long commaId = 1L;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount); // 다른 스레드에서 작업이 완료될 때까지 대기할 수 있도록 해줌

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    commaService.readOne(commaId);
                } finally {
                    countDownLatch.countDown();
                }
            });

        }

        countDownLatch.await();
        Comma comma = commaRepository.findById(1L).orElseThrow();

        assertThat(comma.getView()).isEqualTo(100);
    }
}
