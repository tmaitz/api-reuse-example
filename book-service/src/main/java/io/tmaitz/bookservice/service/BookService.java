package io.tmaitz.bookservice.service;

import io.tmaitz.bookserviceapi.model.BookResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private long idSequence = 0;

    private final Map<Long, BookResponseDto> bookMap = new HashMap<>();

    private BookResponseDto updateOrCreateBook(long id, String title, String author) {
        return bookMap.compute(id, (it, value) -> BookResponseDto.builder()
                .id(it)
                .title(title)
                .author(author)
                .build());
    }

    public BookResponseDto createBook(String title, String author) {
        return updateOrCreateBook(++idSequence, title, author);
    }

    public BookResponseDto getBook(Long id) {
        return bookMap.get(id);
    }

    public BookResponseDto updateBook(Long id, String title, String author) {
        if (!bookMap.containsKey(id)) {
            return null;
        }
        updateOrCreateBook(id, title, author);
        return bookMap.get(id);
    }

    public void removeBook(Long id) {
        bookMap.remove(id);
    }

    public List<BookResponseDto> getAllBooks() {
        return List.copyOf(bookMap.values());
    }

}
