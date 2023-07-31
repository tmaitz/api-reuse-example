package io.tmaitz.bookservice.rest;

import io.tmaitz.bookserviceapi.BookServiceApi;
import io.tmaitz.bookserviceapi.model.BookRequestDto;
import io.tmaitz.bookserviceapi.model.BookResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class BookController implements BookServiceApi {

    @Override
    public ResponseEntity<BookResponseDto> createBook(BookRequestDto bookRequestDto) {
        return ResponseEntity
                .created(URI.create(BookServiceApi.PATH + "/" + 1L))
                .body(
                        BookResponseDto.builder()
                                .id(1L)
                                .title(bookRequestDto.getTitle())
                                .author(bookRequestDto.getAuthor())
                                .build()
                );
    }

    @Override
    public ResponseEntity<BookResponseDto> getBook(Long id) {
        return ResponseEntity.ok(
                BookResponseDto.builder()
                        .id(1L)
                        .title("Crown Of Smoke")
                        .author("John Smith")
                        .build()
        );
    }

    @Override
    public ResponseEntity<BookResponseDto> updateBook(Long id, BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(
                BookResponseDto.builder()
                        .id(id)
                        .title(bookRequestDto.getTitle())
                        .author(bookRequestDto.getAuthor())
                        .build()
        );
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(
                List.of(
                        BookResponseDto.builder()
                                .id(1L)
                                .title("Crown Of Smoke")
                                .author("John Smith")
                                .build(),
                        BookResponseDto.builder()
                                .id(2L)
                                .title("The Wintering Shadow")
                                .author("Smith Johns")
                                .build()
                )
        );
    }

}
