package io.tmaitz.bookservice.rest;

import io.tmaitz.bookservice.service.BookService;
import io.tmaitz.bookserviceapi.BookServiceApi;
import io.tmaitz.bookserviceapi.model.BookRequestDto;
import io.tmaitz.bookserviceapi.model.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController implements BookServiceApi {

    private final BookService bookService;

    @Override
    public ResponseEntity<BookResponseDto> createBook(BookRequestDto bookRequestDto) {
        final var bookResponseDto = bookService.createBook(bookRequestDto.getTitle(), bookRequestDto.getAuthor());
        return ResponseEntity
                .created(URI.create(BookServiceApi.PATH + "/" + bookResponseDto.getId()))
                .body(bookResponseDto);
    }

    @Override
    public ResponseEntity<BookResponseDto> getBook(Long id) {
        final var bookResponseDto = bookService.getBook(id);
        if (bookResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookResponseDto);
    }

    @Override
    public ResponseEntity<BookResponseDto> updateBook(Long id, BookRequestDto bookRequestDto) {
        final var bookResponseDto = bookService.updateBook(id, bookRequestDto.getTitle(), bookRequestDto.getAuthor());
        if (bookResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookResponseDto);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(List.copyOf(bookService.getAllBooks()));
    }

}
