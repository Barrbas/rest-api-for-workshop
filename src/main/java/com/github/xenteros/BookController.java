package com.github.xenteros;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
class BookController {

    private List<Book> books = new ArrayList<Book>();
    private long nextId = 1L;


    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }


    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public Book createBook(@RequestBody CreateBookDto createBookDto) {
        Book book = new Book(nextId++, createBookDto.getTitle(), createBookDto.getAuthor());
        books.add(book);
        return book;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        books.removeIf(b -> b.getId().equals(id));
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        Book old = getBookById(book.getId());
        old.setAuthor(book.getAuthor());
        old.setTitle(book.getTitle());
        return old;
    }

}
