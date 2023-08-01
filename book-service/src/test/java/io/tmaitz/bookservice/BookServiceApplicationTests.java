package io.tmaitz.bookservice;

import io.tmaitz.bookservice.service.BookService;
import io.tmaitz.bookserviceapi.model.BookRequestDto;
import io.tmaitz.bookserviceapi.model.BookResponseDto;
import io.tmaitz.bookserviceclient.BookServiceClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@EnableFeignClients(clients = BookServiceClient.class)
@SpringBootTest(
        classes = BookServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.cloud.openfeign.client.config.book-service.url=http://localhost:8080"
        })
class BookServiceApplicationTests {

    @MockBean
    private BookService bookService;

    @Autowired
    private BookServiceClient bookServiceClient;

    @Test
    void test_create() {
        // given
        final var id = 1L;
        final var title = "The Picture of Dorian Gray";
        final var author = "Oscar Wilde";
        final var bookRequestDto = BookRequestDto.builder()
                .title(title)
                .author(author)
                .build();
        when(bookService.createBook(title, author)).thenReturn(BookResponseDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build());

        // when
        final var responseEntity = bookServiceClient.createBook(bookRequestDto);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(id, bookResponseDto.getId());
        assertEquals(title, bookResponseDto.getTitle());
        assertEquals(author, bookResponseDto.getAuthor());

        verify(bookService, times(1)).createBook(title, author);
    }

    @Test
    void test_read() {
        // given
        final var id = 1L;
        final var title = "The Picture of Dorian Gray";
        final var author = "Oscar Wilde";
        when(bookService.getBook(id)).thenReturn(BookResponseDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build());

        // when
        final var responseEntity = bookServiceClient.getBook(id);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(id, bookResponseDto.getId());
        assertEquals(title, bookResponseDto.getTitle());
        assertEquals(author, bookResponseDto.getAuthor());

        verify(bookService, times(1)).getBook(id);
    }

    @Test
    void test_update() {
        // given
        final var id = 1L;
        final var title = "The Old Man and the Sea";
        final var author = "Ernest Hemingway";
        when(bookService.updateBook(id, title, author)).thenReturn(BookResponseDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build());

        // when
        final var responseEntity = bookServiceClient.updateBook(id, BookRequestDto.builder()
                .title("The Old Man and the Sea")
                .author("Ernest Hemingway")
                .build());

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(id, bookResponseDto.getId());
        assertEquals(title, bookResponseDto.getTitle());
        assertEquals(author, bookResponseDto.getAuthor());

        verify(bookService, times(1)).updateBook(id, title, author);
    }

    @Test
    void test_delete() {
        // given
        final var id = 1L;
        doNothing().when(bookService).removeBook(id);

        // when
        final var responseEntity = bookServiceClient.deleteBook(1L);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(bookService, times(1)).removeBook(id);
    }

    @Test
    void test_read_all() {
        // given
        final var id1 = 1L;
        final var title1 = "The Picture of Dorian Gray";
        final var author1 = "Oscar Wilde";
        final var id2 = 1L;
        final var title2 = "The Old Man and the Sea";
        final var author2 = "Ernest Hemingway";
        when(bookService.getAllBooks()).thenReturn(List.of(
                BookResponseDto.builder()
                        .id(id1)
                        .title(title1)
                        .author(author1)
                        .build(),
                BookResponseDto.builder()
                        .id(id2)
                        .title(title2)
                        .author(author2)
                        .build()
        ));

        // when
        final var responseEntity = bookServiceClient.getAllBooks();

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(2, bookResponseDto.size());
        assertEquals(id1, bookResponseDto.get(0).getId());
        assertEquals(title1, bookResponseDto.get(0).getTitle());
        assertEquals(author1, bookResponseDto.get(0).getAuthor());
        assertEquals(id2, bookResponseDto.get(1).getId());
        assertEquals(title2, bookResponseDto.get(1).getTitle());
        assertEquals(author2, bookResponseDto.get(1).getAuthor());

        verify(bookService, times(1)).getAllBooks();
    }

}
