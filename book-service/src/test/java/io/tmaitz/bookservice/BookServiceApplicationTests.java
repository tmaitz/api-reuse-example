package io.tmaitz.bookservice;

import io.tmaitz.bookserviceapi.model.BookRequestDto;
import io.tmaitz.bookserviceclient.BookServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableFeignClients(clients = BookServiceClient.class)
@SpringBootTest(
        classes = BookServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.cloud.openfeign.client.config.book-service.url=http://localhost:8080"
        })
class BookServiceApplicationTests {

    @Autowired
    private BookServiceClient bookServiceClient;

    @Test
    void test_create() {
        final var responseEntity = bookServiceClient.createBook(BookRequestDto.builder()
                .title("Test example 1")
                .author("Test example 2")
                .build());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(1L, bookResponseDto.getId());
        assertEquals("Test example 1", bookResponseDto.getTitle());
        assertEquals("Test example 2", bookResponseDto.getAuthor());
    }

    @Test
    void test_read() {
        final var responseEntity = bookServiceClient.getBook(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(1L, bookResponseDto.getId());
        assertEquals("Crown Of Smoke", bookResponseDto.getTitle());
        assertEquals("John Smith", bookResponseDto.getAuthor());
    }

    @Test
    void test_update() {
        final var responseEntity = bookServiceClient.updateBook(1L, BookRequestDto.builder()
                .title("Test example 3")
                .author("Test example 4")
                .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDto = responseEntity.getBody();
        assertNotNull(bookResponseDto);
        assertEquals(1L, bookResponseDto.getId());
        assertEquals("Test example 3", bookResponseDto.getTitle());
        assertEquals("Test example 4", bookResponseDto.getAuthor());
    }

    @Test
    void test_delete() {
        final var responseEntity = bookServiceClient.deleteBook(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_read_all() {
        final var responseEntity = bookServiceClient.getAllBooks();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var bookResponseDtoList = responseEntity.getBody();
        assertNotNull(bookResponseDtoList);
        assertEquals(2, bookResponseDtoList.size());
        assertEquals(1L, bookResponseDtoList.get(0).getId());
        assertEquals("Crown Of Smoke", bookResponseDtoList.get(0).getTitle());
        assertEquals("John Smith", bookResponseDtoList.get(0).getAuthor());
        assertEquals(2L, bookResponseDtoList.get(1).getId());
        assertEquals("The Wintering Shadow", bookResponseDtoList.get(1).getTitle());
        assertEquals("Smith Johns", bookResponseDtoList.get(1).getAuthor());
    }

}
