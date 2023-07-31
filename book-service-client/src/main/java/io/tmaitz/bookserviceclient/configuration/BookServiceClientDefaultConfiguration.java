package io.tmaitz.bookserviceclient.configuration;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class BookServiceClientDefaultConfiguration {

    @Bean
    public Feign.Builder bookServiceDefaultFeignBuilder() {
        final var options = new Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true);
        return new Feign.Builder()
                .retryer(new Retryer.Default(500, 1000, 3))
                .options(options);
    }

}
