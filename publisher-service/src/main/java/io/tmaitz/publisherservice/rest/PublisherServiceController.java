package io.tmaitz.publisherservice.rest;

import io.tmaitz.bookserviceapi.model.BookResponseDto;
import io.tmaitz.bookserviceclient.BookServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherServiceController {

    private final BookServiceClient bookServiceClient;

    @GetMapping("/api/author")
    public String getAuthorByTitle(@RequestParam("title") String title) {
        final var responseEntity = bookServiceClient.getAllBooks();
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            return null;
        }
        return responseEntity
                .getBody().stream()
                .filter(dto -> dto.getTitle().equals(title))
                .map(BookResponseDto::getAuthor)
                .findFirst()
                .orElse(null);
    }

}
