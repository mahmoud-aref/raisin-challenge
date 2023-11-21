package com.raisin.backend.infrastructure.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolExecutorConfigs {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        // those fields could be configurable through application.yml
        // but, I will leave it as it is for now
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(200);
        executor.initialize();
        return executor;
    }
}
