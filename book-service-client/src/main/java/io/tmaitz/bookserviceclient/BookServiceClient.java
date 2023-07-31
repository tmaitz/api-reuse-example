package io.tmaitz.bookserviceclient;

import io.tmaitz.bookserviceapi.BookServiceApi;
import io.tmaitz.bookserviceclient.configuration.BookServiceClientDefaultConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "book-service",
        url = "${spring.cloud.openfeign.client.config.book-service.url}",
        configuration = BookServiceClientDefaultConfiguration.class
)
public interface BookServiceClient extends BookServiceApi {
}
