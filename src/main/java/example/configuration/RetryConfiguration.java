package example.configuration;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import javassist.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RetryConfiguration {

    @Bean
    public Retry terminServiceRetry(){
        RetryConfig retryConfig = RetryConfig.custom()
                .intervalFunction(IntervalFunction.ofExponentialBackoff(2000, 2))
                .maxAttempts(5)
                .ignoreExceptions(NotFoundException.class)
                .build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);

        return retryRegistry.retry("terminServiceRetry");
    }

}

