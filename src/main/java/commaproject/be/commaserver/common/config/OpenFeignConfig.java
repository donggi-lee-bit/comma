package commaproject.be.commaserver.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients("commaproject.be.commaserver")
@Configuration
public class OpenFeignConfig {

}
