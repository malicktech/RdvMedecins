package rdvmedecins.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import rdvmedecins.async.ExceptionHandlingAsyncTaskExecutor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Inject
    private AppProperties appProperties;

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(appProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(appProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(appProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("zocdoc-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
